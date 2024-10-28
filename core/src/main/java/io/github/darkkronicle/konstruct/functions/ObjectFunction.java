package io.github.darkkronicle.konstruct.functions;

import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.type.KonstructObject;

import java.util.List;

/** Represents a function that is tied to a specific object */
public interface ObjectFunction<K extends KonstructObject<?>> {

    Result parse(ParseContext context, K self, List<Node> input);

    String getName();

    IntRange getArgumentCount();

}
