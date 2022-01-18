package io.github.darkkronicle.addons.conditions;

import io.github.darkkronicle.Konstruct.NodeProcessor;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;

public interface BooleanFunction extends NamedFunction {

    @Override
    default String parse(ParseContext context, List<Node> input) {
        return boolToString(parseBool(context, input));
    }

    boolean parseBool(ParseContext context, List<Node> input);

    static boolean stringToBool(String string) {
        try {
            return Integer.parseInt(string) != 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static String boolToString(boolean bool) {
        return bool ? "1" : "0";
    }

    static void addAllConditionalFunctions(NodeProcessor processor) {
        processor.addFunction(new ConditionFunction());
        processor.addFunction(new NotFunction());
        processor.addFunction(new IfFunction());
    }
}
