package io.github.darkkronicle.Konstruct.functions;

import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;

public class NullFunction implements NamedFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        for (int i = 0; i < input.size(); i++) {
            Function.parseArgument(context, input, i);
        }
        return Result.success("");
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.any();
    }

    @Override
    public String getName() {
        return "null";
    }
}
