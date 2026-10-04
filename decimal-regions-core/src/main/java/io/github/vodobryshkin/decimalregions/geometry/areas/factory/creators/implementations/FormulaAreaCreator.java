package io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations;

import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.interfaces.AreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.FormulaArea;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.FormulaAreaRequest;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;

import java.math.BigDecimal;

/**
 * Класс для автоматического создания областей по формулам.
 */
public class FormulaAreaCreator implements AreaCreator {
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
        if (areaRequest instanceof FormulaAreaRequest) {
            FormulaAreaRequest formulaAreaRequest = (FormulaAreaRequest) areaRequest;
            String formula = formulaAreaRequest.getFormula();

            return new FormulaArea(formula, radius);
        }
        return null;
    }
}
