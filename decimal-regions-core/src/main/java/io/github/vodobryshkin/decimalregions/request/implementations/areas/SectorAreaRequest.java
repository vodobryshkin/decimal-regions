package io.github.vodobryshkin.decimalregions.request.implementations.areas;

import lombok.Data;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;

import java.math.BigDecimal;

/**
 * Класс для реализации запроса на создание области в виде сектора.
 */
@Data
@Deprecated
public class SectorAreaRequest implements AreaRequest {
    private final BigDecimal xC;
    private final BigDecimal yC;
    private final BigDecimal radiusK;
    private final BigDecimal startAngleK;
    private final BigDecimal endAngleK;
}
