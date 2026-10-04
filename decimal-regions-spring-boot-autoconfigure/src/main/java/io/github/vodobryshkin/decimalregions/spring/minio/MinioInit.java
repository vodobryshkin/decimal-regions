package io.github.vodobryshkin.decimalregions.spring.minio;

import io.minio.MinioClient;

public class MinioInit {
    public static MinioClient build(String endpoint, String accessKey, String secretKey) {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}

