package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
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
    public String parse(ParseContext context, List<Node> input) {
        ReplaceType type = ReplaceType.LITERAL;
        if (input.size() == 4) {
            String replaceTypeString = Function.parseArgument(context, input, 3).strip().toLowerCase(Locale.ROOT);
            type = ReplaceType.getType(replaceTypeString);
        }
        Pattern pattern;
        String patternString = Function.parseArgument(context, input, 0);
        if (type == ReplaceType.LITERAL) {
            pattern = Pattern.compile(patternString, Pattern.LITERAL);
        } else if (type == ReplaceType.UPPER_LOWER) {
            pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(patternString);
        }
        Matcher matcher = pattern.matcher(Function.parseArgument(context, input, 1));
        String replaceTo = Function.parseArgument(context, input, 2);
        return matcher.replaceAll(replaceTo);
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
