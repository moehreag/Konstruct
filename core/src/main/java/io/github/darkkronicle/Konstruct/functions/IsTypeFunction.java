package io.github.darkkronicle.Konstruct.functions;

import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.type.BooleanObject;

import java.util.List;

public class IsTypeFunction implements NamedFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Result obj = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(obj)) return obj;
        Result type = Function.parseArgument(context, input, 1);
        if (Function.shouldReturn(type)) return type;
        return Result.success(new BooleanObject(obj.getContent().getTypeName().equals(type.getContent().getString())));
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(2);
    }

    @Override
    public String getName() {
        return "isType";
    }
}
