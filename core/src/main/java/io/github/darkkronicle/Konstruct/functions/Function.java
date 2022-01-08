package io.github.darkkronicle.Konstruct.functions;

import io.github.darkkronicle.Konstruct.IntRange;

import java.util.List;

public interface Function {

    String parse(List<String> input);

    IntRange getArgumentCount();

}
