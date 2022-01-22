package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements a function with the syntax replace(regex, input, toReplace)
 */
public class ReplaceFunction implements NamedFunction {

    @AllArgsConstructor
    public enum ReplaceType {
        LITERAL("literal"),
        REGEX("regex"),
        UPPER_LOWER("upper_lower")
        ;
        public final String name;

        public static ReplaceType getType(String input) {
            for (ReplaceType type : values()) {
                if (type.name.equals(input)) {
                    return type;
                }
            }
            return LITERAL;
        }
    }

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        ReplaceType type = ReplaceType.LITERAL;
        Result res;
        if (input.size() == 4) {
            res = Function.parseArgument(context, input, 3);
            if (Function.shouldReturn(res)) return res;

            String replaceTypeString = res.getContent().getString().strip().toLowerCase(Locale.ROOT);
            type = ReplaceType.getType(replaceTypeString);
        }
        Pattern pattern;

        res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;

        String patternString = res.getContent().getString();
        if (type == ReplaceType.LITERAL) {
            pattern = Pattern.compile(patternString, Pattern.LITERAL);
        } else if (type == ReplaceType.UPPER_LOWER) {
            pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(patternString);
        }

        res = Function.parseArgument(context, input, 1);
        if (Function.shouldReturn(res)) return res;
        Matcher matcher = pattern.matcher(res.getContent().getString());

        res = Function.parseArgument(context, input, 2);
        if (Function.shouldReturn(res)) return res;

        String replaceTo = res.getContent().getString();
        return Result.success(matcher.replaceAll(replaceTo));
    }

    @Override
    public IntRange getArgumentCount() {
        return new IntRange(3, 4);
    }

    @Override
    public String getName() {
        return "replace";
    }

}
