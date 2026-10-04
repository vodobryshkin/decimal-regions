package io.github.vodobryshkin.decimalregions.spring.service;

import io.github.vodobryshkin.decimalregions.spring.dto.AreasFileDTO;
import tools.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import io.github.vodobryshkin.decimalregions.checker.CheckoutManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

public class ConfigFileCheckoutHitService extends ACheckoutHitService {
    @Getter @Setter
    private String configName;

    public ConfigFileCheckoutHitService(ObjectMapper objectMapper, CheckoutManager checkoutManager) {
        super(objectMapper, checkoutManager);
    }

    @Override
    public synchronized void updateResource(AreasFileDTO dto) {
        Path temporary = null;

        try {
            if (dto == null || dto.getAreas() == null) {
                throw new IllegalArgumentException("areas must not be null");
            }

            byte[] json = getObjectMapper().writeValueAsBytes(dto);
            CheckoutManager next =
                    new CheckoutManager(new ByteArrayInputStream(json));

            Path target = Path.of(configName).toAbsolutePath().normalize();
            Path parent = target.getParent();

            Files.createDirectories(parent);
            temporary = Files.createTempFile(parent, ".checkout-", ".json");
            Files.write(temporary, json);

            Files.move(
                    temporary,
                    target,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING
            );

            setCheckoutManager(next);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to update checkout config: " + configName,
                    e
            );
        } finally {
            if (temporary != null) {
                try {
                    Files.deleteIfExists(temporary);
                } catch (IOException cleanupFailure) {
                    temporary.toFile().deleteOnExit();
                }
            }
        }
    }

    @Override
    public synchronized AreasFileDTO getAreasData() {
        try {
            Path path = Path.of(configName);

            if (!Files.exists(path)) {
                throw new IllegalStateException("Config file not found: " + configName);
            }

            try (InputStream is = Files.newInputStream(path)) {
                return fromJsonInputStream(is);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AreasFileDTO fromJsonInputStream(InputStream is) throws Exception {
        return getObjectMapper().readValue(is, AreasFileDTO.class);
    }
}
