package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.addons.conditions.BooleanFunction;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsMatchFunction implements NamedFunction {

    @Override
    public String parse(ParseContext context, List<Node> input) {
        ReplaceFunction.ReplaceType type = ReplaceFunction.ReplaceType.LITERAL;
        if (input.size() == 3) {
            String replaceTypeString = Function.parseArgument(context, input, 2).strip().toLowerCase(Locale.ROOT);
            type = ReplaceFunction.ReplaceType.getType(replaceTypeString);
        }
        Pattern pattern;
        String patternString = Function.parseArgument(context, input, 0);
        if (type == ReplaceFunction.ReplaceType.LITERAL) {
            pattern = Pattern.compile(patternString, Pattern.LITERAL);
        } else if (type == ReplaceFunction.ReplaceType.UPPER_LOWER) {
            pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(patternString);
        }
        Matcher matcher = pattern.matcher(Function.parseArgument(context, input, 1));
        return BooleanFunction.boolToString(matcher.find());
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(2, 3);
    }

    @Override
    public String getName() {
        return "isMatch";
    }
}
