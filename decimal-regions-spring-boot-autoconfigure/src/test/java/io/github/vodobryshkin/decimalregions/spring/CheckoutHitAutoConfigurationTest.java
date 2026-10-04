package io.github.vodobryshkin.decimalregions.spring;

import io.github.vodobryshkin.decimalregions.spring.service.ACheckoutHitService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CheckoutHitAutoConfigurationTest {

    private final ApplicationContextRunner runner =
            new ApplicationContextRunner()
                    .withConfiguration(AutoConfigurations.of(
                            CheckoutHitAutoConfiguration.class
                    ));

    @Test
    void fileModeWorksWithoutMinio() {
        runner.withClassLoader(new FilteredClassLoader("io.minio"))
                .withPropertyValues(
                        "checkout.config-path=classpath:areas-test.json"
                )
                .run(context -> {
                    assertThat(context)
                            .hasNotFailed()
                            .hasSingleBean(ACheckoutHitService.class);

                    ACheckoutHitService service =
                            context.getBean(ACheckoutHitService.class);

                    assertThat(service.checkoutHit("0", "0", "1")).isTrue();
                    assertThat(service.checkoutHit("2", "0", "1")).isFalse();
                });
    }

    @Test
    void disabledCheckoutDoesNotLoadResources() {
        runner.withClassLoader(new FilteredClassLoader("io.minio"))
                .withPropertyValues(
                        "checkout.enabled=false",
                        "checkout.minio.mode=true",
                        "checkout.config-path=classpath:missing.json"
                )
                .run(context -> assertThat(context)
                        .hasNotFailed()
                        .doesNotHaveBean(ACheckoutHitService.class));
    }

    @Test
    void minioModeWithoutSdkFailsClearly() {
        runner.withClassLoader(new FilteredClassLoader("io.minio"))
                .withPropertyValues("checkout.minio.mode=true")
                .run(context -> {
                    assertThat(context).hasFailed();
                    assertThat(context.getStartupFailure())
                            .hasRootCauseMessage(
                                    "checkout.minio.mode=true requires io.minio:minio "
                                            + "on the application classpath"
                            );
                });
    }

    @Test
    void userServiceOverridesAutoConfiguration() {
        ACheckoutHitService customService = mock(ACheckoutHitService.class);

        runner.withClassLoader(new FilteredClassLoader("io.minio"))
                .withPropertyValues("checkout.minio.mode=true")
                .withBean(ACheckoutHitService.class, () -> customService)
                .run(context -> {
                    assertThat(context)
                            .hasNotFailed()
                            .hasSingleBean(ACheckoutHitService.class);

                    assertThat(context.getBean(ACheckoutHitService.class))
                            .isSameAs(customService);
                });
    }
}