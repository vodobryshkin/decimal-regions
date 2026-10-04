package io.github.vodobryshkin.decimalregions.geometry.areas.implementations;

import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Класс, реализующий прямоугольную область.
 */
@Deprecated
public class RectangleArea implements Area {
    private final Point leftLowerPoint;
    private final BigDecimal height;
    private final BigDecimal width;
    private final String format;

    /**
     * Конструктор класса прямоугольной области.
     * Задаёт прямоугольную область по переданным параметрам.
     *
     * @param point точка, соответсвующая левому нижнему углу прямоугольника.
     * @param height высота прямоугольника (величина по оси y).
     * @param width ширина прямоугольника (величина по оси x).
     */
    public RectangleArea(Point point, String format, BigDecimal width, BigDecimal height) {
        this.leftLowerPoint = point;
        this.height = height;
        this.width = width;
        this.format = format;
    }

    /**
     * Метод для проверки вхождения точки внутрь заданной прямоугольной области.
     * Чтобы точка лежала внутри прямоугольной области, нужно чтобы она находилась между всеми её границами.
     *
     * @param point точка для проверки.
     * @return информацию входит ли точка в область (true) или нет (false).
     */
    @Override
    public boolean checkPoint(Point point) {
        BigDecimal x0 = leftLowerPoint.getX();
        BigDecimal y0 = leftLowerPoint.getY();

        BigDecimal x = point.getX();
        BigDecimal y = point.getY();

        switch (format) {
            case "lower-left":
                    return (x.compareTo(x0) >= 0 && x.compareTo(x0.add(width)) <= 0 &&
                            y.compareTo(y0) >= 0 && y.compareTo(y0.add(height)) <= 0);
            case "lower-right":
                    return (x.compareTo(x0.subtract(width)) >= 0 && x.compareTo(x0) <= 0 &&
                            y.compareTo(y0) >= 0 && y.compareTo(y0.add(height)) <= 0);
            case "upper-left":
                    return (x.compareTo(x0) >= 0 && x.compareTo(x0.add(width)) <= 0 &&
                            y.compareTo(y0.subtract(height)) >= 0 && y.compareTo(y0) <= 0);
            case "upper-right":
                    return (x.compareTo(x0.subtract(width)) >= 0 && x.compareTo(x0) <= 0 &&
                            y.compareTo(y0.subtract(height)) >= 0 && y.compareTo(y0) <= 0);
            default:
                return false;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof RectangleArea) {
            RectangleArea rectangleArea = (RectangleArea) o;
            return Objects.equals(leftLowerPoint, rectangleArea.leftLowerPoint) &&
                    Objects.equals(height, rectangleArea.height) &&
                    Objects.equals(width, rectangleArea.width);
        }
        return false;
    }
}
