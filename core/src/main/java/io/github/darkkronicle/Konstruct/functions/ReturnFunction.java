package io.github.darkkronicle.Konstruct.functions;

import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.type.KonstructObject;

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
