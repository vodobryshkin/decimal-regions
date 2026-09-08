package io.github.vodobryshkin.decimalregions.geometry.areas.factory;

import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations.FormulaAreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations.RectangleAreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations.SectorAreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations.TriangleAreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.interfaces.AreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.FormulaAreaRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.RectangleAreaRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.SectorAreaRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.TriangleAreaRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.AreasRequest;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для автоматического создания и управления областями.
 */
public class AreaFactory {
    @Setter
    private AreaCreator areaCreator;

    /**
     * Метод для создания списка областей из запроса на создание областей.
     *
     * @param areasRequest запрос на создание областей.
     * @param radius радиус, по которому задаются области.
     * @return список созданных областей.
     */
    public List<Area> createAreas(AreasRequest areasRequest, BigDecimal radius) {
        List<Area> areaList = new ArrayList<>();

        for (AreaRequest areaRequest: areasRequest.getAreaRequests()) {
            if (areaRequest instanceof RectangleAreaRequest) {
                setAreaCreator(new RectangleAreaCreator());
            } else if (areaRequest instanceof TriangleAreaRequest) {
                setAreaCreator(new TriangleAreaCreator());
            } else if (areaRequest instanceof SectorAreaRequest) {
                setAreaCreator(new SectorAreaCreator());
            } else if (areaRequest instanceof FormulaAreaRequest) {
                setAreaCreator(new FormulaAreaCreator());
            }

            areaList.add(areaCreator.createArea(areaRequest, radius));
        }

        return areaList;
    }
}
