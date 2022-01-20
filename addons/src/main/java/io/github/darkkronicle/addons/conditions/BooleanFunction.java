package io.github.darkkronicle.addons.conditions;

import io.github.darkkronicle.Konstruct.NodeProcessor;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;

public interface BooleanFunction extends NamedFunction {

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
