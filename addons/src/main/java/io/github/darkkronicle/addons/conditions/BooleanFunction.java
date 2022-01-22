package io.github.darkkronicle.addons.conditions;

import io.github.darkkronicle.Konstruct.parser.NodeProcessor;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;

public interface BooleanFunction extends NamedFunction {

    static void addAllConditionalFunctions(NodeProcessor processor) {
        processor.addFunction(new ConditionFunction());
        processor.addFunction(new NotFunction());
        processor.addFunction(new IfFunction());
    }
}
