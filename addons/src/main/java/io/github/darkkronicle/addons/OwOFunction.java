package io.github.darkkronicle.addons;

import dev.maow.owo.api.OwO;
import dev.maow.owo.util.OwOFactory;
import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;

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
        return Result.success(owo.translate(res.getContent()));
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
