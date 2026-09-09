import io.github.vodobryshkin.decimalregions.geometry.areas.factory.AreaFactory;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.parser.JsonAreasConfigParser;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.AreasRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AreaFactoryTest {

    @Test
    @DisplayName("Создание областей")
    public void createAreasTest() throws IOException {
        try (InputStream inputStream = Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream("areas1.json"),
                "areas1.json not found on classpath"
        )) {
            AreasRequest areasRequest =
                    new JsonAreasConfigParser().parse(inputStream);

            List<Area> areaListFromFactory =
                    new AreaFactory().createAreas(
                            areasRequest,
                            new BigDecimal("5")
                    );

            assertEquals(3, areaListFromFactory.size());
        }
    }
}