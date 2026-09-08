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
    private final AreaContext areaContext;
    private AreasRequest areasRequest;

    public CheckoutManager(String configName) throws IOException {
        areaContext = new AreaContext();
        areasRequest = new JsonAreasConfigParser().parse(configName);
    }

    public CheckoutManager(InputStream inputStream) throws IOException {
        areaContext = new AreaContext();
        areasRequest = new JsonAreasConfigParser().parse(inputStream);
    }

    public boolean checkRequest(CheckoutRequest request) {
        List<Area> areaList = new AreaFactory().createAreas(areasRequest, request.getR());
        boolean status = false;

        for (Area area : areaList) {
            areaContext.setGeometryArea(area);

            if (areaContext.execute(request.getPoint())) {
                status = true;
                break;
            }
        }

        return status;
    }

    public void updateAreasData(InputStream inputStream) throws IOException {
        areasRequest = new JsonAreasConfigParser().parse(inputStream);
    }
}