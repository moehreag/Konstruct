package io.github.darkkronicle.addons.conditions;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;

public class ConditionFunction implements BooleanFunction {

    @Override
    public boolean parseBool(ParseContext context, List<Node> input) {
        boolean bool1;
        boolean bool2;
        Gate gate = Gate.getGate(Function.parseArgument(context, input, 1));
        bool1 = BooleanFunction.stringToBool(Function.parseArgument(context, input, 0).strip());
        bool2 = BooleanFunction.stringToBool(Function.parseArgument(context, input, 2).strip());
        return gate.evaluate(bool1, bool2);
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(3);
    }

    @Override
    public String getName() {
        return "bool";
    }
}
