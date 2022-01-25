package io.github.darkkronicle.Konstruct.functions;

import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.type.KonstructObject;

import java.util.List;

/** Represents a function that is tied to a specific object */
public interface ObjectFunction<K extends KonstructObject<?>> {

    Result parse(ParseContext context, K self, List<Node> input);

    String getName();

    IntRange getArgumentCount();

}
