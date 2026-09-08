package io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations;

import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.interfaces.AreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.SectorArea;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.SectorAreaRequest;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;

import java.math.BigDecimal;

/**
 * Класс для автоматического создания круглых областей.
 */
@Deprecated
public class SectorAreaCreator implements AreaCreator {
    /**
     * Метод для создания области по переданному запросу.
     *
     * @param areaRequest переданный запрос на создание области в виде сектора.
     * @param radius      радиус из условия лабораторной.
     * @return созданную область.
     */
    @Override
    public Area createArea(AreaRequest areaRequest, BigDecimal radius) {
        if (areaRequest instanceof SectorAreaRequest) {
            SectorAreaRequest sectorAreaRequest = (SectorAreaRequest) areaRequest;
            BigDecimal xC = sectorAreaRequest.getXC();
            BigDecimal yC = sectorAreaRequest.getYC();
            BigDecimal radiusK = sectorAreaRequest.getRadiusK().multiply(radius);
            BigDecimal startAngleK = sectorAreaRequest.getStartAngleK().multiply(new BigDecimal(Math.PI));
            BigDecimal endAngleK = sectorAreaRequest.getEndAngleK().multiply(new BigDecimal(Math.PI));

            return new SectorArea(new Point(xC, yC), radiusK, startAngleK, endAngleK);
        }
        return null;
    }
}
