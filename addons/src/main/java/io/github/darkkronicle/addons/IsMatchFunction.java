package io.github.darkkronicle.addons;

import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.functions.Function;
import io.github.darkkronicle.konstruct.functions.NamedFunction;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.type.BooleanObject;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsMatchFunction implements NamedFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        ReplaceFunction.ReplaceType type = ReplaceFunction.ReplaceType.LITERAL;
        Result res;
        if (input.size() == 3) {
            res = Function.parseArgument(context, input, 2);
            if (Function.shouldReturn(res)) return res;
            String replaceTypeString = res.getContent().getString().strip().toLowerCase(Locale.ROOT);
            type = ReplaceFunction.ReplaceType.getType(replaceTypeString);
        }
        Pattern pattern;
        res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;
        String patternString = res.getContent().getString();
        if (type == ReplaceFunction.ReplaceType.LITERAL) {
            pattern = Pattern.compile(patternString, Pattern.LITERAL);
        } else if (type == ReplaceFunction.ReplaceType.UPPER_LOWER) {
            pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(patternString);
        }
        res = Function.parseArgument(context, input, 1);
        if (Function.shouldReturn(res)) return res;
        Matcher matcher = pattern.matcher(res.getContent().getString());
        return Result.success(new BooleanObject(matcher.find()));
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
