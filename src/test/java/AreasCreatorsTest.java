import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations.RectangleAreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations.SectorAreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.factory.creators.implementations.TriangleAreaCreator;
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.RectangleArea;
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.SectorArea;
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.TriangleArea;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.RectangleAreaRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.SectorAreaRequest;
import io.github.vodobryshkin.decimalregions.request.implementations.areas.TriangleAreaRequest;
import io.github.vodobryshkin.decimalregions.request.interfaces.AreaRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AreasCreatorsTest {
    @Test
    @DisplayName("Создание прямоугольной области")
    void TriangleAreaCreatorTestRectangleAreaCreatorTest()  {
        AreaRequest r = new RectangleAreaRequest(new BigDecimal("0"), new BigDecimal("0"), "lower-left",
                new BigDecimal("0.5"), new BigDecimal("1"));
        RectangleAreaCreator rectangleAreaCreator = new RectangleAreaCreator();

        Area newArea = rectangleAreaCreator.createArea(r, new BigDecimal("5"));

        RectangleArea rectangleArea = new RectangleArea(new Point(new BigDecimal("0"), new BigDecimal("0")),
                "lower-left", new BigDecimal("2.5"), new BigDecimal("5"));

        assertEquals(newArea, rectangleArea);
    }

    @Test
    @DisplayName("Создание треугольной области")
    void TriangleAreaCreatorTest()  {
        AreaRequest t = new TriangleAreaRequest(new BigDecimal("0"), new BigDecimal("0"),
                new BigDecimal("1"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("-1")
        );
        TriangleAreaCreator triangleAreaCreator = new TriangleAreaCreator();
        Area newArea = triangleAreaCreator.createArea(t, new BigDecimal("5"));

        TriangleArea triangleArea = new TriangleArea(
                new Point(new BigDecimal("0"), new BigDecimal("0")),
                new Point(new BigDecimal("5"), new BigDecimal("0")),
                new Point(new BigDecimal("0"), new BigDecimal("-5"))
        );

        assertEquals(newArea, triangleArea);
    }

    @Test
    @DisplayName("Создание круглой области")
    void SectorAreaCreatorTest()  {
        AreaRequest s = new SectorAreaRequest(new BigDecimal("0"), new BigDecimal("0"),
                new BigDecimal("0.5"), new BigDecimal("1"), new BigDecimal("1.5"));

        SectorAreaCreator sectorAreaCreator = new SectorAreaCreator();
        SectorArea newArea = (SectorArea) sectorAreaCreator.createArea(s, new BigDecimal("5"));

        SectorArea sectorArea = new SectorArea(
                new Point(new BigDecimal("0"), new BigDecimal("0")),
                new BigDecimal("2.5"),
                new BigDecimal(Math.PI),
                new BigDecimal(Math.PI * 1.5)
        );


        assertEquals(newArea, sectorArea);
    }
}
