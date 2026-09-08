package io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations;

import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.interfaces.AreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.RectangleArea;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.RectangleAreaRequest;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;

import java.math.BigDecimal;

/**
 * Класс для автоматического создания прямоугольных областей.
 */
@Deprecated
public class RectangleAreaCreator implements AreaCreator {
    /**
     * Метод для создания области по переданному запросу.
     * Параметры x, y идут в точку.
     *
     * @param areaRequest переданный запрос на создание прямоугольной области.
     * @param radius радиус из условия лабораторной.
     * @return созданную область.
     */
    @Override
    public Area createArea(AreaRequest areaRequest, BigDecimal radius) {
       if (areaRequest instanceof RectangleAreaRequest) {
           RectangleAreaRequest rectangleAreaRequest = (RectangleAreaRequest) areaRequest;
           BigDecimal x = rectangleAreaRequest.getX();
           BigDecimal y = rectangleAreaRequest.getY();
           String format = rectangleAreaRequest.getFormat();
           BigDecimal width = rectangleAreaRequest.getWidthK().multiply(radius);
           BigDecimal height = rectangleAreaRequest.getHeightK().multiply(radius);

           return new RectangleArea(new Point(x, y), format, width, height);
       }
       return null;
    }
}
