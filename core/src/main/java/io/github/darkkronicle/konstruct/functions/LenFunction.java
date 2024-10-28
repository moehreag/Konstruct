package io.github.darkkronicle.konstruct.functions;

import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;

import java.util.List;

public class LenFunction implements NamedFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Result res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;
        return Result.success(res.getContent().length());
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(1);
    }

    @Override
    public String getName() {
        return "len";
    }
}
