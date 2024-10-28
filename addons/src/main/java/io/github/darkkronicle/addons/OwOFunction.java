package io.github.darkkronicle.addons;

import dev.maow.owo.api.OwO;
import dev.maow.owo.util.OwOFactory;
import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.functions.Function;
import io.github.darkkronicle.konstruct.functions.NamedFunction;
import io.github.darkkronicle.konstruct.nodes.Node;

import java.util.List;

public class OwOFunction implements NamedFunction {

    private final OwO owo;

    public OwOFunction(OwO owo) {
        this.owo = owo;
    }

    public OwOFunction() {
        this(OwOFactory.INSTANCE.create());
    }

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Result res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;
        return Result.success(owo.translate(res.getContent().getString()));
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(1);
    }

    @Override
    public String getName() {
        return "owo";
    }

}
