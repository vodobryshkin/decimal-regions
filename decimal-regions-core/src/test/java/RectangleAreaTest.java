import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.RectangleArea;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RectangleAreaTest {

    @Test
    @DisplayName("Точка внутри области (lower-left)")
    void insideLowerLeft() {
        RectangleArea area = new RectangleArea(new Point(0, 0), "lower-left", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(2, 2)));
    }

    @Test
    @DisplayName("Точка на левом нижнем углу (lower-left)")
    void cornerLowerLeft() {
        RectangleArea area = new RectangleArea(new Point(0, 0), "lower-left", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(0, 0)));
    }

    @Test
    @DisplayName("Точка на правом верхнем углу (lower-left)")
    void oppositeCornerLowerLeft() {
        RectangleArea area = new RectangleArea(new Point(0, 0), "lower-left", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(4, 4)));
    }

    @Test
    @DisplayName("Точка за областью (lower-left)")
    void outsideLowerLeft() {
        RectangleArea area = new RectangleArea(new Point(0, 0), "lower-left", new BigDecimal("4"), new BigDecimal("4"));
        assertFalse(area.checkPoint(new Point(-1, -1)));
        assertFalse(area.checkPoint(new Point(5, 2)));
    }

    @Test
    @DisplayName("Точка внутри области (lower-right)")
    void insideLowerRight() {
        RectangleArea area = new RectangleArea(new Point(4, 0), "lower-right", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(2, 2)));
    }

    @Test
    @DisplayName("Точка на правом нижнем углу (lower-right)")
    void cornerLowerRight() {
        RectangleArea area = new RectangleArea(new Point(4, 0), "lower-right", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(4, 0)));
    }

    @Test
    @DisplayName("Точка на левом верхнем углу (lower-right)")
    void oppositeCornerLowerRight() {
        RectangleArea area = new RectangleArea(new Point(4, 0), "lower-right", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(0, 4)));
    }

    @Test
    @DisplayName("Точка за областью (lower-right)")
    void outsideLowerRight() {
        RectangleArea area = new RectangleArea(new Point(4, 0), "lower-right", new BigDecimal("4"), new BigDecimal("4"));
        assertFalse(area.checkPoint(new Point(5, -1)));
        assertFalse(area.checkPoint(new Point(-2, 2)));
    }

    @Test
    @DisplayName("Точка внутри области (upper-left)")
    void insideUpperLeft() {
        RectangleArea area = new RectangleArea(new Point(0, 4), "upper-left", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(2, 2)));
    }

    @Test
    @DisplayName("Точка на левом верхнем углу (upper-left)")
    void cornerUpperLeft() {
        RectangleArea area = new RectangleArea(new Point(0, 4), "upper-left", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(0, 4)));
    }

    @Test
    @DisplayName("Точка на правом нижнем углу (upper-left)")
    void oppositeCornerUpperLeft() {
        RectangleArea area = new RectangleArea(new Point(0, 4), "upper-left", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(4, 0)));
    }

    @Test
    @DisplayName("Точка за областью (upper-left)")
    void outsideUpperLeft() {
        RectangleArea area = new RectangleArea(new Point(0, 4), "upper-left", new BigDecimal("4"), new BigDecimal("4"));
        assertFalse(area.checkPoint(new Point(-1, 5)));
        assertFalse(area.checkPoint(new Point(2, -1)));
    }

    @Test
    @DisplayName("Точка внутри области (upper-right)")
    void insideUpperRight() {
        RectangleArea area = new RectangleArea(new Point(4, 4), "upper-right", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(2, 2)));
    }

    @Test
    @DisplayName("Точка на правом верхнем углу (upper-right)")
    void cornerUpperRight() {
        RectangleArea area = new RectangleArea(new Point(4, 4), "upper-right", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(4, 4)));
    }

    @Test
    @DisplayName("Точка на левом нижнем углу (upper-right)")
    void oppositeCornerUpperRight() {
        RectangleArea area = new RectangleArea(new Point(4, 4), "upper-right", new BigDecimal("4"), new BigDecimal("4"));
        assertTrue(area.checkPoint(new Point(0, 0)));
    }

    @Test
    @DisplayName("Точка за областью (upper-right)")
    void outsideUpperRight() {
        RectangleArea area = new RectangleArea(new Point(4, 4), "upper-right", new BigDecimal("4"), new BigDecimal("4"));
        assertFalse(area.checkPoint(new Point(5, 2)));
        assertFalse(area.checkPoint(new Point(3, -1)));
    }
}
