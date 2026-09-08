package io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations;

import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.interfaces.AreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.TriangleArea;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.TriangleAreaRequest;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;

import java.math.BigDecimal;

/**
 * Класс для автоматического создания треугольных областей.
 */
@Deprecated
public class TriangleAreaCreator  implements AreaCreator {
    /**
     * Метод для создания области по переданному запросу.
     * Параметры x, y идут в точку.
     *
     * @param areaRequest переданный запрос на создание треугольной области.
     * @param radius радиус из условия лабораторной.
     * @return созданную область.
     */
    @Override
    public Area createArea(AreaRequest areaRequest, BigDecimal radius) {
        if (areaRequest instanceof TriangleAreaRequest) {
            TriangleAreaRequest triangleAreaRequest = (TriangleAreaRequest) areaRequest;
            BigDecimal xA = triangleAreaRequest.getXA();
            BigDecimal yA = triangleAreaRequest.getYA();

            BigDecimal xB = triangleAreaRequest.getXBK().multiply(radius);
            BigDecimal yB = triangleAreaRequest.getYBK().multiply(radius);

            BigDecimal xC = triangleAreaRequest.getXCK().multiply(radius);
            BigDecimal yC = triangleAreaRequest.getYCK().multiply(radius);

            return new TriangleArea(new Point(xA, yA), new Point(xB, yB), new Point(xC, yC));
        }
        return null;
    }
}
