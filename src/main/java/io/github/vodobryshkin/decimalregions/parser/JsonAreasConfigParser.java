package io.github.vodobryshkin.decimalregions.parser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.FormulaAreaRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.RectangleAreaRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.SectorAreaRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.TriangleAreaRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.AreasRequest;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonAreasConfigParser {
    public AreasRequest parse(String configName) throws IOException {
        String json = Files.readString(Path.of(configName));
        return parseJson(json);
    }

    public AreasRequest parse(InputStream inputStream) throws IOException {
        String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        return parseJson(json);
    }

    private AreasRequest parseJson(String json) {
        List<AreaRequest> areaRequests = new ArrayList<>();

        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(json, JsonObject.class);

        List<JsonElement> listOfAreas = obj.get("areas").getAsJsonArray().asList();

        for (JsonElement jsonElement : listOfAreas) {
            JsonObject areaInfo = jsonElement.getAsJsonObject();

            String type = areaInfo.get("type").getAsString();

            switch (type) {
                case "rectangle":
                    java.math.BigDecimal x = areaInfo.get("x").getAsBigDecimal();
                    java.math.BigDecimal y = areaInfo.get("y").getAsBigDecimal();
                    java.math.BigDecimal widthK = areaInfo.get("widthK").getAsBigDecimal();
                    java.math.BigDecimal heightK = areaInfo.get("heightK").getAsBigDecimal();
                    String format = areaInfo.get("format").getAsString();

                    areaRequests.add(new RectangleAreaRequest(x, y, format, widthK, heightK));
                    break;

                case "triangle":
                    java.math.BigDecimal xA = areaInfo.get("xA").getAsBigDecimal();
                    java.math.BigDecimal yA = areaInfo.get("yA").getAsBigDecimal();
                    java.math.BigDecimal xBK = areaInfo.get("xBK").getAsBigDecimal();
                    java.math.BigDecimal yBK = areaInfo.get("yBK").getAsBigDecimal();
                    java.math.BigDecimal xCK = areaInfo.get("xCK").getAsBigDecimal();
                    java.math.BigDecimal yCK = areaInfo.get("yCK").getAsBigDecimal();

                    areaRequests.add(new TriangleAreaRequest(xA, yA, xBK, yBK, xCK, yCK));
                    break;

                case "circle":
                    java.math.BigDecimal xC = areaInfo.get("xC").getAsBigDecimal();
                    java.math.BigDecimal yC = areaInfo.get("yC").getAsBigDecimal();
                    java.math.BigDecimal radiusK = areaInfo.get("radiusK").getAsBigDecimal();
                    java.math.BigDecimal startAngleK = areaInfo.get("startAngleK").getAsBigDecimal();
                    java.math.BigDecimal endAngleK = areaInfo.get("endAngleK").getAsBigDecimal();

                    areaRequests.add(new SectorAreaRequest(xC, yC, radiusK, startAngleK, endAngleK));
                    break;
                case "formula":
                    String value = areaInfo.get("value").getAsString();
                    areaRequests.add(new FormulaAreaRequest(value));
            }
        }

        return new AreasRequest(areaRequests);
    }
}
