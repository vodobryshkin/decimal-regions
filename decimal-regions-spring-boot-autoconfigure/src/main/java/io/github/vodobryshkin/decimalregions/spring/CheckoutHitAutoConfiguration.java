package io.github.vodobryshkin.decimalregions.spring;

import io.github.vodobryshkin.decimalregions.checker.CheckoutManager;
import io.github.vodobryshkin.decimalregions.spring.minio.MinioInit;
import io.github.vodobryshkin.decimalregions.spring.service.ACheckoutHitService;
import io.github.vodobryshkin.decimalregions.spring.service.ConfigFileCheckoutHitService;
import io.github.vodobryshkin.decimalregions.spring.service.MinioCheckoutHitService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@AutoConfiguration
@ConditionalOnClass({CheckoutManager.class, ObjectMapper.class})
@EnableConfigurationProperties(CheckoutProperties.class)
@ConditionalOnProperty(
        prefix = "checkout",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class CheckoutHitAutoConfiguration {

    private static String requireText(String value, String property) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(property + " must not be blank");
        }
        return value;
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(
            prefix = "checkout.minio",
            name = "mode",
            havingValue = "false",
            matchIfMissing = true
    )
    static class FileConfiguration {

        @Bean
        @ConditionalOnMissingBean(ACheckoutHitService.class)
        ACheckoutHitService checkoutHitService(
                CheckoutProperties properties,
                ResourceLoader resourceLoader
        ) {
            String location = requireText(
                    properties.getConfigPath(),
                    "checkout.config-path"
            );

            try {
                Path file;

                if (location.startsWith("classpath:")) {
                    try (InputStream input = resourceLoader
                            .getResource(location)
                            .getInputStream()) {
                        file = Files.createTempFile("checkout-config-", ".json");
                        file.toFile().deleteOnExit();
                        Files.copy(
                                input,
                                file,
                                StandardCopyOption.REPLACE_EXISTING
                        );
                    }
                } else {
                    file = location.startsWith("file:")
                            ? resourceLoader.getResource(location).getFile().toPath()
                            : Path.of(location);

                    file = file.toAbsolutePath().normalize();
                }

                if (!Files.isRegularFile(file)) {
                    throw new IllegalStateException(
                            "Config is not a regular file: " + file
                    );
                }

                try (InputStream input = Files.newInputStream(file)) {
                    CheckoutManager manager = new CheckoutManager(input);
                    ObjectMapper mapper = JsonMapper.builder().build();

                    ConfigFileCheckoutHitService service =
                            new ConfigFileCheckoutHitService(mapper, manager);

                    service.setConfigName(file.toString());
                    return service;
                }
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Failed to load checkout config: " + location,
                        e
                );
            }
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(name = "io.minio.MinioClient")
    @ConditionalOnProperty(
            prefix = "checkout.minio",
            name = "mode",
            havingValue = "true"
    )
    static class MinioConfiguration {

        @Bean
        @ConditionalOnMissingBean(ACheckoutHitService.class)
        ACheckoutHitService checkoutHitService(CheckoutProperties properties) {
            CheckoutProperties.MinIO settings = properties.getMinio();

            if (settings == null) {
                throw new IllegalArgumentException(
                        "checkout.minio settings must be configured"
                );
            }

            requireText(settings.getEndpoint(), "checkout.minio.endpoint");
            requireText(settings.getAccessKey(), "checkout.minio.access-key");
            requireText(settings.getSecretKey(), "checkout.minio.secret-key");
            requireText(settings.getBucketName(), "checkout.minio.bucket-name");
            requireText(settings.getObjectName(), "checkout.minio.object-name");

            try {
                MinioClient client = MinioInit.build(
                        settings.getEndpoint(),
                        settings.getAccessKey(),
                        settings.getSecretKey()
                );

                try (InputStream input = client.getObject(
                        GetObjectArgs.builder()
                                .bucket(settings.getBucketName())
                                .object(settings.getObjectName())
                                .build()
                )) {
                    CheckoutManager manager = new CheckoutManager(input);
                    ObjectMapper mapper = JsonMapper.builder().build();

                    MinioCheckoutHitService service =
                            new MinioCheckoutHitService(manager, mapper);

                    service.setMinioClient(client);
                    service.setBucketName(settings.getBucketName());
                    service.setObjectName(settings.getObjectName());

                    return service;
                }
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Failed to initialize MinIO checkout service",
                        e
                );
            }
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMissingClass("io.minio.MinioClient")
    @ConditionalOnProperty(
            prefix = "checkout.minio",
            name = "mode",
            havingValue = "true"
    )
    static class MissingMinioConfiguration {

        @Bean
        @ConditionalOnMissingBean(ACheckoutHitService.class)
        ACheckoutHitService checkoutHitService() {
            throw new IllegalStateException(
                    "checkout.minio.mode=true requires io.minio:minio "
                            + "on the application classpath"
            );
        }
    }
}