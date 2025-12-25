package ru.ifmo.se.gmt.checker;

import ru.ifmo.se.gmt.geometry.areas.factory.AreaFactory;
import ru.ifmo.se.gmt.geometry.areas.interfaces.Area;
import ru.ifmo.se.gmt.parser.JsonAreasConfigParser;
import ru.ifmo.se.gmt.request.implementations.messages.AreasRequest;
import ru.ifmo.se.gmt.request.implementations.messages.CheckoutRequest;

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