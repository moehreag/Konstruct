package io.github.darkkronicle.addons.conditions;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;

public class IfFunction implements NamedFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Result res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;
        boolean val = BooleanFunction.stringToBool(res.getContent().strip());
        if (val) {
            return Function.parseArgument(context, input, 1);
        }
        if (input.size() == 3) {
            // Else
            return Function.parseArgument(context, input, 2);
        }
        return Result.success("");
    }

    @Override
    public IntRange getArgumentCount() {
        return new IntRange(2, 3);
    }

    @Override
    public String getName() {
        return "if";
    }

}
