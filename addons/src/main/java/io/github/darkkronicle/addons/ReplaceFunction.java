package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements a function with the syntax replace(regex, input, toReplace)
 */
public class ReplaceFunction implements NamedFunction {

    @Override
    public String parse(ParseContext context, List<Node> input) {
        Pattern pattern = Pattern.compile(Function.parseArgument(context, input, 0));
        Matcher matcher = pattern.matcher(Function.parseArgument(context, input, 1));
        String replaceTo = Function.parseArgument(context, input, 2);
        return matcher.replaceAll(replaceTo);
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(3);
    }

    @Override
    public String getName() {
        return "replace";
    }

}
