package io.github.darkkronicle.konstruct.functions;

import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.type.KonstructObject;

import java.util.List;

public class ReturnFunction implements NamedFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        KonstructObject content = null;
        if (input.size() == 1) {
            Result result = Function.parseArgument(context, input, 0);
            if (Function.shouldReturn(result)) {
                return result;
            }
            if (result != null) {
                content = result.getContent();
            }
        }
        return new Result(Result.ResultType.RETURN, content);
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(0, 1);
    }

    @Override
    public String getName() {
        return "return";
    }

}
