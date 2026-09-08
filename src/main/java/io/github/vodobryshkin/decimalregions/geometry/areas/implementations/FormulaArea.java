package io.github.vodobryshkin.decimalregions.geometry.areas.implementations;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import io.github.vodobryshkin.decimalregions.geometry.areas.interfaces.Area;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.parser.ConstraintsLexer;
import io.github.vodobryshkin.decimalregions.parser.ConstraintsParser;
import io.github.vodobryshkin.decimalregions.visitor.EvalExprVisitor;
import io.github.vodobryshkin.decimalregions.visitor.EvalFormulaVisitor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

/**
 * Класс, реализующий область, заданную по функции.
 */
public class FormulaArea implements Area {
    private final ParseTree tree;
    private final BigDecimal radius;

    private final MathContext mc = new MathContext(50);

    public FormulaArea(String formula, BigDecimal radius) {
        CharStream cs = CharStreams.fromString(formula);
        tree = new ConstraintsParser(new CommonTokenStream(new ConstraintsLexer(cs))).formula();
        this.radius = radius;
    }

    /**
     * Метод для проверки вхождения точки внутрь заданной формулой области.
     * Чтобы точка лежала внутри заданной формулой области, нужно чтобы она соответствовала уравнению.
     *
     * @param point точка для проверки.
     * @return информацию входит ли точка в область (true) или нет (false).
     */
    @Override
    public boolean checkPoint(Point point) {
        BigDecimal x = point.getX();
        BigDecimal y = point.getY();

        Map<String, BigDecimal> vars = Map.of("x", x, "y", y, "r", radius);
        EvalExprVisitor exprVisitor = new EvalExprVisitor(vars, mc);
        EvalFormulaVisitor formulaVisitor = new EvalFormulaVisitor(exprVisitor);

        return formulaVisitor.visit(tree);
    }
}
