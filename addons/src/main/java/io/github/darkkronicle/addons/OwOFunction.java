package io.github.darkkronicle.addons;

import dev.maow.owo.api.OwO;
import dev.maow.owo.util.OwOFactory;
import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;

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
    public String parse(List<String> input) {
        return owo.translate(input.get(0));
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
