package io.github.vodobryshkin.decimalregions.spring.service;

import io.github.vodobryshkin.decimalregions.spring.dto.AreasFileDTO;
import tools.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import io.github.vodobryshkin.decimalregions.checker.CheckoutManager;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.CheckoutRequest;

import java.math.BigDecimal;

@AllArgsConstructor
public abstract class ACheckoutHitService {
    @Getter
    private final ObjectMapper objectMapper;

    @Setter
    private volatile CheckoutManager checkoutManager;

    public boolean checkoutHit(String x, String y, String r) {
        CheckoutManager manager = this.checkoutManager;
        return manager.checkRequest(
                new CheckoutRequest(
                        new Point(new BigDecimal(x), new BigDecimal(y)),
                        new BigDecimal(r)
                )
        );
    }

    public abstract void updateResource(AreasFileDTO areasFileDTO);
    public abstract AreasFileDTO getAreasData();
}
