package io.github.darkkronicle.addons.conditions;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;

public class ConditionFunction implements BooleanFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        boolean bool1;
        boolean bool2;
        Result res = Function.parseArgument(context, input, 1);
        if (Function.shouldReturn(res)) return res;
        Gate gate = Gate.getGate(res.getContent());

        res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;
        bool1 = BooleanFunction.stringToBool(res.getContent().strip());

        res = Function.parseArgument(context, input, 2);
        if (Function.shouldReturn(res)) return res;
        bool2 = BooleanFunction.stringToBool(res.getContent().strip());

        return Result.success(BooleanFunction.boolToString(gate.evaluate(bool1, bool2)));
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
