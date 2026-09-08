package io.github.vodobryshkin.decimalregions.request.implementations.areas;

import lombok.Value;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;

/**
 * Класс для реализации запроса на создание области по формуле.
 */
@Value
public class FormulaAreaRequest implements AreaRequest {
    String formula;
}
