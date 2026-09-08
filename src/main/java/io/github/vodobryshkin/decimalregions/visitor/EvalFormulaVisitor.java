package io.github.vodobryshkin.decimalregions.visitor;

import org.antlr.v4.runtime.tree.ParseTree;
import io.github.vodobryshkin.decimalregions.parser.ConstraintsBaseVisitor;
import io.github.vodobryshkin.decimalregions.parser.ConstraintsParser;

import java.math.BigDecimal;
import java.util.List;

public class EvalFormulaVisitor extends ConstraintsBaseVisitor<Boolean> {
    private final EvalExprVisitor expr;

    public EvalFormulaVisitor(EvalExprVisitor expr) {
        this.expr = expr;
    }

    @Override
    public Boolean visitFormula(ConstraintsParser.FormulaContext ctx) {
        List<ConstraintsParser.ConstraintContext> constraints =
                ctx.getRuleContexts(ConstraintsParser.ConstraintContext.class);

        boolean result = visit(constraints.get(0));

        for (int i = 1; i < constraints.size(); i++) {
            ParseTree opNode = ctx.getChild(2 * i - 1);
            String op = opNode.getText();

            boolean rhs = visit(constraints.get(i));

            if ("&&".equals(op)) {
                result = result && rhs;
            } else if ("||".equals(op)) {
                result = result || rhs;
            } else if ("xor".equals(op)) {
                result = result ^ rhs;
            } else {
                throw new RuntimeException("Unknown boolean op: " + op);
            }
        }

        return result;
    }

    @Override
    public Boolean visitConstraint(ConstraintsParser.ConstraintContext ctx) {
        List<ConstraintsParser.ExprContext> exprs =
                ctx.getRuleContexts(ConstraintsParser.ExprContext.class);

        BigDecimal left = expr.visit(exprs.get(0));
        BigDecimal right = expr.visit(exprs.get(1));

        String op = ctx.getChild(1).getText();
        int cmp = left.compareTo(right);

        if (">".equals(op))  return cmp > 0;
        if (">=".equals(op)) return cmp >= 0;
        if ("<".equals(op))  return cmp < 0;
        if ("<=".equals(op)) return cmp <= 0;
        if ("=".equals(op))  return cmp == 0;
        if ("!=".equals(op)) return cmp != 0;

        throw new RuntimeException("Unknown par op: " + op);
    }
}
