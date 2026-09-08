package io.github.vodobryshkin.decimalregions.checker;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import lombok.Setter;

/**
 * Класс для упрощения процесса проверки на вхождение точки в область.
 */
public class AreaContext {
    @Setter
    private Area geometryArea;

    /**
     * Метод для запуска проверок на вхождение внутрь области.
     *
     * @param point точка для проверки.
     * @return информацию входит ли точка в область (true) или нет (false).
     */
    public boolean execute(Point point) {
        return geometryArea.checkPoint(point);
    }
}
