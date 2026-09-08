package io.github.vodobryshkin.decimalregions.request.implementations.areas;

import lombok.Data;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;

import java.math.BigDecimal;

/**
 * Класс для реализации запроса на создание прямоугольной области.
 */
@Data
@Deprecated
public class RectangleAreaRequest implements AreaRequest {
    private final BigDecimal x;
    private final BigDecimal y;
    private final String format;
    private final BigDecimal widthK;
    private final BigDecimal heightK;
}
