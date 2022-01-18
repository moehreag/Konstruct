package io.github.darkkronicle.addons.conditions;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;

public class NotFunction implements BooleanFunction {

    @Override
    public boolean parseBool(ParseContext context, List<Node> input) {
        return !(BooleanFunction.stringToBool(Function.parseArgument(context, input, 0)));
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(1);
    }

    @Override
    public String getName() {
        return "not";
    }

}
