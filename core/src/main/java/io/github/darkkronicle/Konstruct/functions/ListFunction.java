package io.github.darkkronicle.Konstruct.functions;

import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.type.KonstructObject;
import io.github.darkkronicle.Konstruct.type.ListObject;

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
