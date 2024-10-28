package io.github.darkkronicle.addons;

import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.functions.Function;
import io.github.darkkronicle.konstruct.functions.NamedFunction;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.type.IntegerObject;

import java.util.List;
import java.util.Random;

/**
 * A function that will generate an integer from min-max (inclusive)
 */
public class RandomFunction implements NamedFunction {

    private final Random random;

    public RandomFunction() {
        this(new Random());
    }

    public RandomFunction(Random random) {
        this.random = random;
    }

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Result minResult = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(minResult)) return minResult;
        Result maxResult = Function.parseArgument(context, input, 1);
        if (Function.shouldReturn(maxResult)) return minResult;
        int min = IntegerObject.fromObject(minResult.getContent()).orElse(0);
        int max = IntegerObject.fromObject(maxResult.getContent()).orElse(1);
        if (min == max) {
            return Result.success(String.valueOf(min));
        }
        if (min > max) {
            int temp = max;
            max = min;
            min = temp;
        }
        return Result.success(new IntegerObject(random.nextInt(max - min + 1) + min));
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(2);
    }

    @Override
    public String getName() {
        return "randInt";
    }

}
