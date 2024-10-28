package io.github.darkkronicle.konstruct.functions;

import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.type.IntegerObject;

import java.util.List;

/**
 * A function to return the index of list.
 * <p>Example: <code>[if(0,this is one,this is two,this is three)]</code></p> will return <code>this is one</code>
 */
public class GetFunction implements NamedFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Result res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;
        int val = IntegerObject.fromObject(res.getContent()).orElse(0);
        if (input.size() == 2) {
            // A list!
            Result list = Function.parseArgument(context, input, 1);
            if (Function.shouldReturn(list)) return list;
            return Result.success(list.getContent().get(new IntegerObject(val)));
        }
        if (val < 0 || val >= input.size() - 1) {
            return Function.parseArgument(context, input, 1);
        }
        return Function.parseArgument(context, input, val + 1);
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.greaterThanEqual(2);
    }

    @Override
    public String getName() {
        return "get";
    }

}
