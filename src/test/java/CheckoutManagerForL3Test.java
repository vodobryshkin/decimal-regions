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

public class CheckoutManagerForL3Test {
    @Test
    @DisplayName("Точка попала1")
    void pointIsInTheareas3Test1() throws IOException {
        String areas3Path;
        try {
            areas3Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas3.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas3Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("1"), new BigDecimal("1")),
                new BigDecimal("5"));

        assertTrue(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка попала2")
    void pointIsInTheareas3Test2() throws IOException {String areas3Path;
        try {
            areas3Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas3.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas3Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("0.5"), new BigDecimal("-0.5")),
                new BigDecimal("3"));

        assertTrue(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка попала3")
    void pointIsInTheareas3Test3() throws IOException {
        String areas3Path;
        try {
            areas3Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas3.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas3Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("0"), new BigDecimal("0")),
                new BigDecimal("5"));

        assertTrue(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала1")
    void pointIsNotInTheareas3Test1() throws IOException {
        String areas3Path;
        try {
            areas3Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas3.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas3Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("10"), new BigDecimal("1")),
                new BigDecimal("5"));

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала2")
    void pointIsNotInTheareas3Test2() throws IOException {
        String areas3Path;
        try {
            areas3Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas3.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas3Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("4"), new BigDecimal("-4")),
                new BigDecimal("4"));

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала3")
    void pointIsNotInTheareas3Test3() throws IOException {
        String areas3Path;
        try {
            areas3Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas3.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas3Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("-2"), new BigDecimal("-2")),
                new BigDecimal("4"));

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала4")
    void pointIsNotInTheareas3Test4() throws IOException {
        String areas3Path;
        try {
            areas3Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas3.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas3Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("1.51"), new BigDecimal("-1.5555")),
                new BigDecimal("3"));

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }
}