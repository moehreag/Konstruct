package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;

import java.util.List;

/**
 * A function to return the index of list.
 * <p>Example: <code>[if(0,this is one,this is two,this is three)]</code></p> will return <code>this is one</code>
 */
public class GetFunction implements NamedFunction {

    @Override
    public String parse(List<String> input) {
        int val;
        try {
            val = Integer.parseInt(input.get(0));
        } catch (NumberFormatException e) {
            return input.get(1);
        }
        if (val < 0 || val >= input.size() - 1) {
            return input.get(1);
        }
        return input.get(val + 1);
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.greaterThanEqual(3);
    }

    @Override
    public String getName() {
        return "get";
    }

}
