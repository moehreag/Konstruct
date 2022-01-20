package io.github.darkkronicle.addons.conditions;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;

public class NotFunction implements BooleanFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Result res = Function.parseArgument(context, input, 1);
        if (Function.shouldReturn(res)) return res;
        return Result.success(BooleanFunction.boolToString(!BooleanFunction.stringToBool(res.getContent().strip())));
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
