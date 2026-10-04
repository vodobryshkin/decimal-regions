package io.github.vodobryshkin.decimalregions.checker;

import io.github.vodobryshkin.decimalregions.geometry.areas.factory.AreaFactory;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.parser.JsonAreasConfigParser;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.AreasRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.CheckoutRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CheckoutManager {

    private volatile AreasRequest areasRequest;

    public CheckoutManager(String configName) throws IOException {
        areasRequest = new JsonAreasConfigParser().parse(configName);
    }

    public CheckoutManager(InputStream inputStream) throws IOException {
        areasRequest = new JsonAreasConfigParser().parse(inputStream);
    }

    public boolean checkRequest(CheckoutRequest request) {
        AreasRequest snapshot = areasRequest;
        List<Area> areas =
                new AreaFactory().createAreas(snapshot, request.getR());

        for (Area area : areas) {
            if (area.checkPoint(request.getPoint())) {
                return true;
            }
        }

        return false;
    }

    public void updateAreasData(InputStream inputStream) throws IOException {
        AreasRequest next = new JsonAreasConfigParser().parse(inputStream);
        areasRequest = next;
    }
}