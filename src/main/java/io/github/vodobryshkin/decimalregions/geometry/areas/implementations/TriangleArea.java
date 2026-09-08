package io.github.vodobryshkin.decimalregions.geometry.areas.implementations;

import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Класс, реализующий треугольную область.
 */
@Deprecated
public class TriangleArea implements Area {
    private final Point pointA;
    private final Point pointB;
    private final Point pointC;

    /**
     * Конструктор класса треугольной области.
     * Задаёт треугольную область по переданным точкам.
     *
     * @param pointA первая точка треугольника.
     * @param pointB вторая точка треугольника.
     * @param pointC третья точка треугольника.
     */
    public TriangleArea(Point pointA, Point pointB, Point pointC) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
    }

    /**
     * Метод для проверки вхождения точки внутрь заданной треугольной области.
     * Каждое ориентированное ребро задаёт полуплоскость слева от ребра. Пересечение трёх таких полуплоскостей и есть
     * треугольник. Если точка слева от всех трёх рёбер, она в треугольнике.
     *
     * @param point точка для проверки.
     * @return информацию входит ли точка в область (true) или нет (false).
     */
    @Override
    public boolean checkPoint(Point point) {
        int s1 = sign(point, pointA, pointB);
        int s2 = sign(point, pointB, pointC);
        int s3 = sign(point, pointC, pointA);

        boolean hasNeg = (s1 < 0) || (s2 < 0) || (s3 < 0);
        boolean hasPos = (s1 > 0) || (s2 > 0) || (s3 > 0);

        return !(hasNeg && hasPos);
    }

    private static int sign(Point p1, Point p2, Point p3) {
        BigDecimal x1 = p1.getX(), y1 = p1.getY();
        BigDecimal x2 = p2.getX(), y2 = p2.getY();
        BigDecimal x3 = p3.getX(), y3 = p3.getY();

        BigDecimal term1 = x1.subtract(x3).multiply(y2.subtract(y3));
        BigDecimal term2 = x2.subtract(x3).multiply(y1.subtract(y3));
        return term1.subtract(term2).signum();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TriangleArea) {
            TriangleArea triangleArea = (TriangleArea) o;
            return Objects.equals(pointA, triangleArea.pointA) &&
                    Objects.equals(pointB, triangleArea.pointB) &&
                    Objects.equals(pointC, triangleArea.pointC);
        }
        return false;
    }
}
