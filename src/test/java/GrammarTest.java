import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.junit.jupiter.api.Test;
import io.github.vodobryshkin.decimalregions.parser.ConstraintsLexer;
import io.github.vodobryshkin.decimalregions.parser.ConstraintsParser;
import io.github.vodobryshkin.decimalregions.visitor.EvalExprVisitor;
import io.github.vodobryshkin.decimalregions.visitor.EvalFormulaVisitor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

public class GrammarTest {
    @Test
    public void test1() {
        String input = "x^2=1";

        CharStream cs = CharStreams.fromString(input);
        ConstraintsLexer lexer = new ConstraintsLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ConstraintsParser parser = new ConstraintsParser(tokens);

        ParseTree tree = parser.formula();
        System.out.println(tree.toStringTree(parser));
    }

    @Test
    public void test2() {
        String input = "x^2 + y^2 <= r^2 && sin(x) = 0";

        CharStream cs = CharStreams.fromString(input);
        ConstraintsLexer lexer = new ConstraintsLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ConstraintsParser parser = new ConstraintsParser(tokens);

        ParseTree tree = parser.formula();

        BigDecimal x = new BigDecimal("0");
        BigDecimal y = new BigDecimal("6");
        BigDecimal r = new BigDecimal("5");

        var vars = Map.of("x", x, "y", y, "r", r);
        EvalExprVisitor exprVisitor = new EvalExprVisitor(vars, new MathContext(50));
        EvalFormulaVisitor formulaVisitor = new EvalFormulaVisitor(exprVisitor);

        boolean ok = formulaVisitor.visit(tree);
        System.out.println("Result: " + ok);
    }

    @Test
    public void test3() {
        String input = "sqrt(x^2 - 5*r) <= sin(y)";

        CharStream cs = CharStreams.fromString(input);
        ConstraintsLexer lexer = new ConstraintsLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ConstraintsParser parser = new ConstraintsParser(tokens);

        ParseTree tree = parser.formula();

        BigDecimal x = new BigDecimal("3.9");
        BigDecimal y = new BigDecimal("0.47605");
        BigDecimal r = new BigDecimal("3.0");

        var vars = Map.of("x", x, "y", y, "r", r);
        EvalExprVisitor exprVisitor = new EvalExprVisitor(vars, new MathContext(50));
        EvalFormulaVisitor formulaVisitor = new EvalFormulaVisitor(exprVisitor);

        boolean ok = formulaVisitor.visit(tree);
        System.out.println("Result: " + ok);
    }

    @Test
    public void test4() {
        String input = "sqrt(x^2 - 5*r) <= sin(y)";

        CharStream cs = CharStreams.fromString(input);
        ConstraintsLexer lexer = new ConstraintsLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ConstraintsParser parser = new ConstraintsParser(tokens);

        ParseTree tree = parser.formula();
        System.out.println(tree.toStringTree(parser));
    }
}
