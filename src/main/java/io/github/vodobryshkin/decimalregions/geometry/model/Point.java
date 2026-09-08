package io.github.vodobryshkin.decimalregions.geometry.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Класс-абстракция над точкой на координатной плоскости.
 * Нужен для обработки данных на бэкенде.
 */
@Data
@AllArgsConstructor
public class Point {
    private final BigDecimal x;
    private final BigDecimal y;

    public <T extends Number> Point(T x, T y) {
        this(new BigDecimal(String.valueOf(x)), new BigDecimal(String.valueOf(y)));
    }
}
