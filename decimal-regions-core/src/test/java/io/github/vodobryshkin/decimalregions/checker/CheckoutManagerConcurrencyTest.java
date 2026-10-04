package io.github.vodobryshkin.decimalregions.checker;

import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.CheckoutRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutManagerConcurrencyTest {

    private static InputStream config() {
        String json =
                "{\"areas\":[{\"type\":\"formula\",\"value\":\"x<=r\"}]}";
        return new ByteArrayInputStream(
                json.getBytes(StandardCharsets.UTF_8)
        );
    }

    @Test
    void parallelRequestsUseTheirOwnRadius() throws Exception {
        CheckoutManager manager = new CheckoutManager(config());
        ExecutorService pool = Executors.newFixedThreadPool(4);
        CountDownLatch start = new CountDownLatch(1);

        try {
            List<Future<?>> futures = new ArrayList<>();

            for (int worker = 0; worker < 4; worker++) {
                BigDecimal radius = worker % 2 == 0
                        ? BigDecimal.ONE
                        : new BigDecimal("2");
                boolean expected = radius.compareTo(BigDecimal.ONE) > 0;

                CheckoutRequest request = new CheckoutRequest(
                        new Point(new BigDecimal("1.5"), BigDecimal.ZERO),
                        radius
                );

                futures.add(pool.submit(() -> {
                    start.await();

                    for (int i = 0; i < 1000; i++) {
                        if (Thread.currentThread().isInterrupted()) {
                            return null;
                        }

                        assertEquals(
                                expected,
                                manager.checkRequest(request),
                                "Wrong result for r=" + radius
                        );
                    }

                    return null;
                }));
            }

            start.countDown();

            for (Future<?> future : futures) {
                future.get(20, TimeUnit.SECONDS);
            }
        } finally {
            start.countDown();
            pool.shutdownNow();
        }
    }

    @Test
    void failedConfigReadKeepsPreviousConfiguration() throws Exception {
        CheckoutManager manager = new CheckoutManager(config());

        InputStream broken = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Simulated read failure");
            }
        };

        assertThrows(
                IOException.class,
                () -> manager.updateAreasData(broken)
        );

        assertTrue(manager.checkRequest(new CheckoutRequest(
                new Point(BigDecimal.ZERO, BigDecimal.ZERO),
                BigDecimal.ONE
        )));
    }
}