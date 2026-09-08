import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.SectorArea;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SectorAreaTest {

    @Test
    @DisplayName("Точка внутри сектора")
    void pointInsideSectorTest() {
        Point center = new Point(new BigDecimal("0"), new BigDecimal("0"));
        BigDecimal R = new BigDecimal("2.5");
        BigDecimal a0 = new BigDecimal(Math.PI);
        BigDecimal a1 = new BigDecimal(3 * Math.PI / 2);

        SectorArea sector = new SectorArea(center, R, a0, a1);

        Point p = new Point(new BigDecimal("-1"), new BigDecimal("-1"));
        assertTrue(sector.checkPoint(p));
    }

    @Test
    @DisplayName("Точка на граничном луче (angleStart)")
    void pointOnStartRayTest() {
        Point center = new Point(new BigDecimal("0"), new BigDecimal("0"));
        BigDecimal R = new BigDecimal("2.5");
        BigDecimal a0 = new BigDecimal(Math.PI);
        BigDecimal a1 = new BigDecimal(3 * Math.PI / 2);

        SectorArea sector = new SectorArea(center, R, a0, a1);

        Point p = new Point(new BigDecimal("-1"), new BigDecimal("0"));
        assertTrue(sector.checkPoint(p));
    }

    @Test
    @DisplayName("Точка на граничном луче (angleEnd)")
    void pointOnEndRayTest() {
        Point center = new Point(new BigDecimal("0"), new BigDecimal("0"));
        BigDecimal R = new BigDecimal("2.5");
        BigDecimal a0 = new BigDecimal(Math.PI);
        BigDecimal a1 = new BigDecimal(3 * Math.PI / 2);

        SectorArea sector = new SectorArea(center, R, a0, a1);

        Point p = new Point(new BigDecimal("0"), new BigDecimal("-1"));
        assertTrue(sector.checkPoint(p));
    }

    @Test
    @DisplayName("Точка на дуге (r = R)")
    void pointOnArcTest() {
        Point center = new Point(new BigDecimal("0"), new BigDecimal("0"));
        BigDecimal R = new BigDecimal("2.5");
        BigDecimal a0 = new BigDecimal(Math.PI);
        BigDecimal a1 = new BigDecimal(3 * Math.PI / 2);

        SectorArea sector = new SectorArea(center, R, a0, a1);

        Point p = new Point(new BigDecimal("-1.5"), new BigDecimal("-2.0")); // ровно на окружности
        assertTrue(sector.checkPoint(p));
    }

    @Test
    @DisplayName("Снаружи по радиусу (r > R)")
    void pointOutsideByRadiusTest() {
        Point center = new Point(new BigDecimal("0"), new BigDecimal("0"));
        BigDecimal R = new BigDecimal("2.5");
        BigDecimal a0 = new BigDecimal(Math.PI);
        BigDecimal a1 = new BigDecimal(3 * Math.PI / 2);

        SectorArea sector = new SectorArea(center, R, a0, a1);

        Point p = new Point(new BigDecimal("3"), new BigDecimal("3"));
        assertFalse(sector.checkPoint(p));
    }

    @Test
    @DisplayName("Снаружи по углу (без перехода через 0°)")
    void pointOutsideByAngleNoWrap() {
        Point center = new Point(new BigDecimal("0"), new BigDecimal("0"));
        BigDecimal R = new BigDecimal("2.5");
        BigDecimal a0 = new BigDecimal(Math.PI);
        BigDecimal a1 = new BigDecimal(3 * Math.PI / 2);

        SectorArea sector = new SectorArea(center, R, a0, a1);

        Point p = new Point(new BigDecimal("0"), new BigDecimal("0.0000000001"));
        assertFalse(sector.checkPoint(p));
    }
}
