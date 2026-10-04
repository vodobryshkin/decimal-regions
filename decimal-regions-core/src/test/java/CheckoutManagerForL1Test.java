import io.github.vodobryshkin.decimalregions.checker.CheckoutManager;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.CheckoutRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutManagerForL1Test {

    private CheckoutManager createCheckoutManager() throws IOException {
        InputStream inputStream = Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream("areas1.json"),
                "areas1.json not found on classpath"
        );

        return new CheckoutManager(inputStream);
    }

    @Test
    @DisplayName("Точка попала1")
    void pointIsInTheAreasTest1() throws IOException {
        CheckoutManager checkoutManager = createCheckoutManager();

        CheckoutRequest checkoutRequest = new CheckoutRequest(
                new Point(
                        new BigDecimal("1"),
                        new BigDecimal("1")
                ),
                new BigDecimal("5")
        );

        assertTrue(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка попала2")
    void pointIsInTheAreasTest2() throws IOException {
        CheckoutManager checkoutManager = createCheckoutManager();

        CheckoutRequest checkoutRequest = new CheckoutRequest(
                new Point(
                        new BigDecimal("0"),
                        new BigDecimal("0")
                ),
                new BigDecimal("5")
        );

        assertTrue(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала1")
    void pointIsNotInTheAreasTest1() throws IOException {
        CheckoutManager checkoutManager = createCheckoutManager();

        CheckoutRequest checkoutRequest = new CheckoutRequest(
                new Point(
                        new BigDecimal("10"),
                        new BigDecimal("1")
                ),
                new BigDecimal("5")
        );

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала2")
    void pointIsNotInTheAreasTest2() throws IOException {
        CheckoutManager checkoutManager = createCheckoutManager();

        CheckoutRequest checkoutRequest = new CheckoutRequest(
                new Point(
                        new BigDecimal("4"),
                        new BigDecimal("-4")
                ),
                new BigDecimal("4")
        );

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }

    @Test
    @DisplayName("Точка не попала3")
    void pointIsNotInTheAreasTest3() throws IOException {
        CheckoutManager checkoutManager = createCheckoutManager();

        CheckoutRequest checkoutRequest = new CheckoutRequest(
                new Point(
                        new BigDecimal("-2"),
                        new BigDecimal("-2")
                ),
                new BigDecimal("4")
        );

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
    }
}