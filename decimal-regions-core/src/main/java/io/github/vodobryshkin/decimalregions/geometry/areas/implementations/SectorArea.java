package io.github.vodobryshkin.decimalregions.geometry.areas.implementations;

import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Класс, реализующий область в виде сектора.
 */
@Getter
@Deprecated
public class SectorArea implements Area {
    private final Point centerPoint;
    private final BigDecimal radius;
    private final BigDecimal angleStart;
    private final BigDecimal angleEnd;

    /**
     * Конструктор класса области в виде сектора.
     * Задаёт область в виде сектора по центральной точке, радиусу, углу начала и углу конца (оба в радианах).
     *
     * @param centerPoint центральная точка сектора.
     * @param radius радиус.
     * @param angleStart угол начала сектора.
     * @param angleEnd угол конца сектора.
     */
    public SectorArea(Point centerPoint, BigDecimal radius, BigDecimal angleStart, BigDecimal angleEnd) {
        this.centerPoint = centerPoint;
        this.radius = radius;
        this.angleStart = angleStart;
        this.angleEnd = angleEnd;
    }

    /**
     * Метод для проверки вхождения точки внутрь заданной области.
     * Точка принадлежит сектору, если асстояние до центра ≤ радиуса и её полярный угол φ из центра попадает в дугу
     * [angleStart, angleEnd].
     *
     * @param point точка для проверки.
     * @return информацию входит ли точка в область (true) или нет (false).
     */
    @Override
    public boolean checkPoint(Point point) {
        if (radius.signum() < 0) {
            return false;
        }

        BigDecimal dx = point.getX().subtract(centerPoint.getX());
        BigDecimal dy = point.getY().subtract(centerPoint.getY());
        BigDecimal r2 = dx.multiply(dx).add(dy.multiply(dy));
        BigDecimal R2 = radius.multiply(radius);

        if (r2.compareTo(R2) > 0) {
            return false;
        }

        double a0 = norm(angleStart.doubleValue());
        double a1 = norm(angleEnd.doubleValue());

        if (almostEqual(a0, a1)) return true;

        double phi = Math.atan2(dy.doubleValue(), dx.doubleValue());
        phi = norm(phi);

        final double EPS = 1e-12;

        return (a0 <= a1)
                ? (phi + EPS >= a0 && phi - EPS <= a1)
                : (phi + EPS >= a0 || phi - EPS <= a1);
    }

    private double norm(double a) {
        final double TWO_PI = 2.0 * Math.PI;
        double x = a % TWO_PI;
        if (x < 0) x += TWO_PI;
        return x;
    }

    private boolean almostEqual(double a, double b) {
        return Math.abs(a - b) <= 1e-12;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SectorArea) {
            SectorArea sectorArea = (SectorArea) o;
            return Objects.equals(centerPoint, sectorArea.centerPoint) &&
                    Objects.equals(radius, sectorArea.radius) &&
                    Objects.equals(angleStart, sectorArea.angleStart) &&
                    Objects.equals(angleEnd, sectorArea.angleEnd);
        }
        return false;
    }
}
