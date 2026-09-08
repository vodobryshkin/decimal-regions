package io.github.vodobryshkin.decimalregions.request.implementations.messages;

import lombok.Data;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;
import io.github.vodobryshkin.decimalregions.request.interfaces.Request;

import java.util.List;

/**
 * Класс для реализации запроса с распарсенными запросами.
 */
@Data
public class AreasRequest implements Request {
    private final List<AreaRequest> areaRequests;
}