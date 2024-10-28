package io.github.darkkronicle.addons;

import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.functions.Function;
import io.github.darkkronicle.konstruct.functions.NamedFunction;
import io.github.darkkronicle.konstruct.nodes.Node;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeFunction implements NamedFunction {

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        try {
            Result res = Function.parseArgument(context, input, 0);
            if (Function.shouldReturn(res)) return res;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(res.getContent().getString());
            return Result.success(LocalDateTime.now().format(formatter));
        } catch (IllegalArgumentException e) {
            return Result.success("Invalid time");
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
