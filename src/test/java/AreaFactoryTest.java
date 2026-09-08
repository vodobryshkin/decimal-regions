import io.github.vodobryshkin.decimalregions.geometry.areas.factory.AreaFactory;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.github.vodobryshkin.decimalregions.parser.JsonAreasConfigParser;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.AreasRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AreaFactoryTest {
    @Test
    @DisplayName("Создание областей")
    public void createAreasTest() throws IOException {
        AreasRequest areasRequest = new JsonAreasConfigParser().parse("/home/vodobryshkin/progs/proj/" +
                "IdeaProjects/big-decimal-geometry-web-labs/src/main/resources/areas1.json");

        List<Area> areaListFromFactory = new AreaFactory().createAreas(areasRequest, new BigDecimal("5"));
        assertTrue(true);
    }
}
