package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeFunction implements NamedFunction {

    @Override
    public String parse(ParseContext context, List<Node> input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Function.parseArgument(context, input, 0));
            return LocalDateTime.now().format(formatter);
        } catch (IllegalArgumentException e) {
            return "Invalid time";
        }
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(1);
    }

    @Override
    public String getName() {
        return "time";
    }

}
