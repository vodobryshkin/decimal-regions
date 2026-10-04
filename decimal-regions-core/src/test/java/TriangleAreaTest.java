import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.TriangleArea;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TriangleAreaTest {
    @Test
    @DisplayName("Точка внутри треугольника на точке основания")
    void pointInsideTriangleTestOnBorderPoint() {
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal y0 = new BigDecimal("0");
        Point p0 = new Point(x0, y0);

        Point a = new Point(new BigDecimal("5"), new BigDecimal("0"));
        Point b = new Point(new BigDecimal("0"), new BigDecimal("-5"));
        Point c = new Point(new BigDecimal("0"), new BigDecimal("0"));

        TriangleArea triangleArea = new TriangleArea(a, b, c);

        assertTrue(triangleArea.checkPoint(p0));
    }

    @Test
    @DisplayName("Точка внутри треугольника")
    void pointInsideTriangleTestInside() {
        BigDecimal x0 = new BigDecimal("1");
        BigDecimal y0 = new BigDecimal("-1");
        Point p0 = new Point(x0, y0);

        Point a = new Point(new BigDecimal("5"), new BigDecimal("0"));
        Point b = new Point(new BigDecimal("0"), new BigDecimal("-5"));
        Point c = new Point(new BigDecimal("0"), new BigDecimal("0"));

        TriangleArea triangleArea = new TriangleArea(a, b, c);

        assertTrue(triangleArea.checkPoint(p0));
    }

    @Test
    @DisplayName("Точка внутри треугольника (на гипотенузе)")
    void pointInsideTriangleTestHip() {
        BigDecimal x0 = new BigDecimal("2.5");
        BigDecimal y0 = new BigDecimal("-2.5");
        Point p0 = new Point(x0, y0);

        Point a = new Point(new BigDecimal("5"), new BigDecimal("0"));
        Point b = new Point(new BigDecimal("0"), new BigDecimal("-5"));
        Point c = new Point(new BigDecimal("0"), new BigDecimal("0"));

        TriangleArea triangleArea = new TriangleArea(a, b, c);

        assertTrue(triangleArea.checkPoint(p0));
    }

    @Test
    @DisplayName("Точка снаружи треугольника")
    void pointOutsideTriangle() {
        BigDecimal x0 = new BigDecimal("10");
        BigDecimal y0 = new BigDecimal("-10");
        Point p0 = new Point(x0, y0);

        Point a = new Point(new BigDecimal("5"), new BigDecimal("0"));
        Point b = new Point(new BigDecimal("0"), new BigDecimal("-5"));
        Point c = new Point(new BigDecimal("0"), new BigDecimal("0"));

        TriangleArea triangleArea = new TriangleArea(a, b, c);

        assertFalse(triangleArea.checkPoint(p0));
    }

    @Test
    @DisplayName("Точка снаружи треугольника (почти внутри по Y)")
    void pointOutsideTriangleTestAlmostY() {
        BigDecimal x0 = new BigDecimal("5");
        BigDecimal y0 = new BigDecimal("0.000000000001");
        Point p0 = new Point(x0, y0);

        Point a = new Point(new BigDecimal("5"), new BigDecimal("0"));
        Point b = new Point(new BigDecimal("0"), new BigDecimal("-5"));
        Point c = new Point(new BigDecimal("0"), new BigDecimal("0"));

        TriangleArea triangleArea = new TriangleArea(a, b, c);

        assertFalse(triangleArea.checkPoint(p0));
    }

    @Test
    @DisplayName("Точка снаружи треугольника (почти внутри по X)")
    void pointOutsideTriangleTestAlmostX() {
        BigDecimal x0 = new BigDecimal("5.000000000001");
        BigDecimal y0 = new BigDecimal("0");
        Point p0 = new Point(x0, y0);

        Point a = new Point(new BigDecimal("5"), new BigDecimal("0"));
        Point b = new Point(new BigDecimal("0"), new BigDecimal("-5"));
        Point c = new Point(new BigDecimal("0"), new BigDecimal("0"));

        TriangleArea triangleArea = new TriangleArea(a, b, c);

        assertFalse(triangleArea.checkPoint(p0));
    }

}
