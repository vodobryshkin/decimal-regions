package io.github.vodobryshkin.decimalregions.request.implementations.areas;

import lombok.Data;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;

import java.math.BigDecimal;

/**
 * Класс для реализации запроса на создание треугольной области.
 */
@Data
@Deprecated
public class TriangleAreaRequest implements AreaRequest {
    private final BigDecimal xA;
    private final BigDecimal yA;
    private final BigDecimal xBK;
    private final BigDecimal yBK;
    private final BigDecimal xCK;
    private final BigDecimal yCK;
}

