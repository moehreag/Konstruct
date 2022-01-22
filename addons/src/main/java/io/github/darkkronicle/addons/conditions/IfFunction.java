package io.github.darkkronicle.addons.conditions;

import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.type.BooleanObject;

import java.util.List;

public class IfFunction implements NamedFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Result res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;
        boolean val = BooleanObject.fromObject(res.getContent());
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
