package io.github.vodobryshkin.decimalregions.visitor;

import ch.obermuhlner.math.big.BigDecimalMath;
import io.github.vodobryshkin.decimalregions.parser.ConstraintsBaseVisitor;
import io.github.vodobryshkin.decimalregions.parser.ConstraintsParser;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

public class EvalExprVisitor extends ConstraintsBaseVisitor<BigDecimal> {

    private final Map<String, BigDecimal> vars;
    private final MathContext mc;

    public EvalExprVisitor(Map<String, BigDecimal> vars, MathContext mc) {
        this.vars = vars;
        this.mc = mc;
    }

    @Override
    public BigDecimal visitExpr(ConstraintsParser.ExprContext ctx) {
        return visit(ctx.sum());
    }

    @Override
    public BigDecimal visitSum(ConstraintsParser.SumContext ctx) {
        BigDecimal result = visit(ctx.prod(0));
        for (int i = 1; i < ctx.prod().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            BigDecimal rhs = visit(ctx.prod(i));
            if ("+".equals(op)) {
                result = result.add(rhs, mc);
            } else {
                result = result.subtract(rhs, mc);
            }
        }
        return result;
    }

    @Override
    public BigDecimal visitProd(ConstraintsParser.ProdContext ctx) {
        BigDecimal result = visit(ctx.pow(0));
        for (int i = 1; i < ctx.pow().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            BigDecimal rhs = visit(ctx.pow(i));
            if ("*".equals(op)) {
                result = result.multiply(rhs, mc);
            } else {
                result = result.divide(rhs, mc);
            }
        }
        return result;
    }

    @Override
    public BigDecimal visitPow(ConstraintsParser.PowContext ctx) {
        BigDecimal result = visit(ctx.unary(0));
        for (int i = 1; i < ctx.unary().size(); i++) {
            BigDecimal rhs = visit(ctx.unary(i));
            result = BigDecimalMath.pow(result, rhs, mc);
        }
        return result;
    }

    @Override
    public BigDecimal visitUnary(ConstraintsParser.UnaryContext ctx) {
        BigDecimal value = visit(ctx.atom());
        if (ctx.getChildCount() == 2) {
            String sign = ctx.getChild(0).getText();
            if ("-".equals(sign)) {
                return value.negate(mc);
            }
        }
        return value;
    }

    @Override
    public BigDecimal visitAtom(ConstraintsParser.AtomContext ctx) {
        if (ctx.X() != null) return vars.getOrDefault("x", BigDecimal.ZERO);
        if (ctx.Y() != null) return vars.getOrDefault("y", BigDecimal.ZERO);
        if (ctx.R() != null) return vars.getOrDefault("r", BigDecimal.ZERO);

        if (ctx.number() != null) return visit(ctx.number());
        if (ctx.expr() != null && ctx.func() == null) return visit(ctx.expr());

        if (ctx.func() != null) {
            String f = ctx.func().getText();
            BigDecimal arg = visit(ctx.expr());
            return applyFunc(f, arg);
        }

        throw new RuntimeException("Unknown atom: " + ctx.getText());
    }

    @Override
    public BigDecimal visitNumber(ConstraintsParser.NumberContext ctx) {
        return new BigDecimal(ctx.getText(), mc);
    }

    private BigDecimal applyFunc(String f, BigDecimal x) {
        switch (f) {
            case "sqrt":
                return BigDecimalMath.sqrt(x, mc);
            case "sin":
                return BigDecimalMath.sin(x, mc);
            case "cos":
                return BigDecimalMath.cos(x, mc);
            case "tan":
                return BigDecimalMath.tan(x, mc);
            case "ln":
                return BigDecimalMath.log(x, mc);
            case "log":
                return BigDecimalMath.log10(x, mc);
            case "exp":
                return BigDecimalMath.exp(x, mc);
            case "abs":
                return x.abs(mc);
            default:
                throw new RuntimeException("Unknown func: " + f);
        }
    }
}
