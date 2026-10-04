package io.github.vodobryshkin.decimalregions.request.implementations.messages;

import lombok.Value;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;

import java.math.BigDecimal;

/**
 * Класс с данными для отправки на проверки на бизнес-логику.
 * Получается в результате обработки поступивших данных валидатором.
 */
@Value
public class CheckoutRequest {
    Point point;
    BigDecimal r;
}
