import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.github.vodobryshkin.decimalregions.checker.CheckoutManager;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.CheckoutRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutManagerForL4Test {
    @Test
    @DisplayName("Точка попала1")
    void pointIsInTheareas4Test1() throws IOException {
        String areas4Path;
        try {
            areas4Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas4.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas4Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("-0.5"), new BigDecimal("1.5")),
                new BigDecimal("4"));

        assertTrue(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка попала2")
    void pointIsInTheareas4Test2() throws IOException {String areas4Path;
        try {
            areas4Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas4.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas4Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("1.5"), new BigDecimal("1.3")),
                new BigDecimal("4"));

        assertTrue(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка попала3")
    void pointIsInTheareas4Test3() throws IOException {
        String areas4Path;
        try {
            areas4Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas4.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas4Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("-1.2"), new BigDecimal("-0.6")),
                new BigDecimal("4"));

        assertTrue(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала1")
    void pointIsNotInTheareas4Test1() throws IOException {
        String areas4Path;
        try {
            areas4Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas4.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas4Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("10"), new BigDecimal("1")),
                new BigDecimal("5"));

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала2")
    void pointIsNotInTheareas4Test2() throws IOException {
        String areas4Path;
        try {
            areas4Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas4.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas4Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("1.195"), new BigDecimal("1.605")),
                new BigDecimal("4"));

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала3")
    void pointIsNotInTheareas4Test3() throws IOException {
        String areas4Path;
        try {
            areas4Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas4.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas4Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("-1"), new BigDecimal("-1.05")),
                new BigDecimal("4"));

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала4")
    void pointIsNotInTheareas4Test4() throws IOException {
        String areas4Path;
        try {
            areas4Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas4.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas4Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("-2.05"), new BigDecimal("2")),
                new BigDecimal("4"));

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }
}