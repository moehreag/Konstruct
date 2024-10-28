package io.github.darkkronicle.konstruct.functions;

import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.type.KonstructObject;
import io.github.darkkronicle.konstruct.type.ListObject;

import java.util.ArrayList;
import java.util.List;

public class ListFunction implements NamedFunction {
    @Override
    public Result parse(ParseContext context, List<Node> input) {
        List<KonstructObject<?>> arguments = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            Result res = Function.parseArgument(context, input, i);
            if (Function.shouldReturn(res)) return res;
            arguments.add(res.getContent());
        }
        return Result.success(new ListObject(arguments));
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.any();
    }

    @Override
    public String getName() {
        return "list";
    }
}
