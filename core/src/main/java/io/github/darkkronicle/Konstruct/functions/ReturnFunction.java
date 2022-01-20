package io.github.darkkronicle.Konstruct.functions;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.Result;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;

public class ReturnFunction implements NamedFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        String content = null;
        if (input.size() == 1) {
            Result result = Function.parseArgument(context, input, 0);
            if (Function.shouldReturn(result)) {
                return result;
            }
            content = result.getContent();
        }
        return new Result(Result.ResultType.CANCEL, content);
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
