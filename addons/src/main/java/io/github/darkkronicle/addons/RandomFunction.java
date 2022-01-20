package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;

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
        if (Function.shouldReturn(minResult)) {
            return minResult;
        }
        Result maxResult = Function.parseArgument(context, input, 1);
        if (Function.shouldReturn(maxResult)) {
            return minResult;
        }
        int min;
        int max;
        try {
            min = Integer.parseInt(minResult.getContent().strip());
            max = Integer.parseInt(maxResult.getContent().strip());
        } catch (NumberFormatException e) {
            return Result.success("NaN");
        }
        if (min == max) {
            return Result.success(String.valueOf(min));
        }
        if (min > max) {
            int temp = max;
            max = min;
            min = temp;
        }
        return Result.success(String.valueOf(random.nextInt(max - min + 1) + min));
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
