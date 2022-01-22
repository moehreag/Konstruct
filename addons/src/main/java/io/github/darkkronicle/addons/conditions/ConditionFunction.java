package io.github.darkkronicle.addons.conditions;

import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.type.BooleanObject;

import java.util.List;

public class ConditionFunction implements BooleanFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        boolean bool1;
        boolean bool2;
        Result res = Function.parseArgument(context, input, 1);
        if (Function.shouldReturn(res)) return res;
        Gate gate = Gate.getGate(res.getContent().getString());

        res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;
        bool1 = BooleanObject.fromObject(res.getContent());

        res = Function.parseArgument(context, input, 2);
        if (Function.shouldReturn(res)) return res;
        bool2 = BooleanObject.fromObject(res.getContent());

        return Result.success(new BooleanObject(gate.evaluate(bool1, bool2)));
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
