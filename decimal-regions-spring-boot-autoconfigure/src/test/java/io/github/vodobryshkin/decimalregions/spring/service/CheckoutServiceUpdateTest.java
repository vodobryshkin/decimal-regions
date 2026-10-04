package io.github.vodobryshkin.decimalregions.spring.service;

import io.github.vodobryshkin.decimalregions.checker.CheckoutManager;
import io.github.vodobryshkin.decimalregions.spring.dto.AreasFileDTO;
import io.github.vodobryshkin.decimalregions.spring.dto.FormulaAreaDTO;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.jackson.databind.json.JsonMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckoutServicesUpdateTest {

    @TempDir
    Path directory;

    private static CheckoutManager initialManager() throws IOException {
        String json =
                "{\"areas\":[{\"type\":\"formula\",\"value\":\"x<=r\"}]}";

        return new CheckoutManager(new ByteArrayInputStream(
                json.getBytes(StandardCharsets.UTF_8)
        ));
    }

    private static AreasFileDTO config(String formula) {
        FormulaAreaDTO area = new FormulaAreaDTO();
        area.setType("formula");
        area.setValue(formula);

        AreasFileDTO dto = new AreasFileDTO();
        dto.setAreas(List.of(area));
        return dto;
    }

    private ConfigFileCheckoutHitService fileService() throws IOException {
        Path file = directory.resolve("areas.json");
        JsonMapper mapper = JsonMapper.builder().build();
        Files.write(file, mapper.writeValueAsBytes(config("x<=r")));

        ConfigFileCheckoutHitService service =
                new ConfigFileCheckoutHitService(mapper, initialManager());

        service.setConfigName(file.toString());
        return service;
    }

    @Test
    void successfulFileUpdateChangesStorageAndChecks() throws Exception {
        ConfigFileCheckoutHitService service = fileService();

        service.updateResource(config("x>=r"));

        assertFalse(service.checkoutHit("0", "0", "1"));
        assertEquals(
                "x>=r",
                service.getAreasData().getAreas().get(0).getValue()
        );
    }

    @Test
    void failedFileWriteKeepsPreviousManager() throws Exception {
        ConfigFileCheckoutHitService service = fileService();

        Path blocked = directory.resolve("blocked");
        Files.createDirectory(blocked);
        Files.writeString(blocked.resolve("keep.txt"), "keep");
        service.setConfigName(blocked.toString());

        assertThrows(
                RuntimeException.class,
                () -> service.updateResource(config("x>=r"))
        );

        assertTrue(service.checkoutHit("0", "0", "1"));
        assertEquals("keep", Files.readString(blocked.resolve("keep.txt")));

        try (var files = Files.list(directory)) {
            assertEquals(2L, files.count());
        }
    }

    @Test
    void failedMinioWriteKeepsPreviousManager() throws Exception {
        MinioClient client = mock(MinioClient.class);
        when(client.putObject(any(PutObjectArgs.class)))
                .thenThrow(new IOException("Simulated upload failure"));

        MinioCheckoutHitService service = new MinioCheckoutHitService(
                initialManager(),
                JsonMapper.builder().build()
        );

        service.setMinioClient(client);
        service.setBucketName("test-bucket");
        service.setObjectName("areas.json");

        assertThrows(
                RuntimeException.class,
                () -> service.updateResource(config("x>=r"))
        );

        assertTrue(service.checkoutHit("0", "0", "1"));
    }
}