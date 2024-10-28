package io.github.darkkronicle.konstruct.nodes;

import io.github.darkkronicle.konstruct.functions.Function;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;

import java.util.List;

public class DotNode implements Node {

    private final Node starting;
    private final String name;
    private final List<Node> arguments;
    private final int scope;

    public DotNode(Node starting, String name, List<Node> arguments, int scope) {
        this.starting = starting;
        this.name = name;
        this.arguments = arguments;
        this.scope = scope;
    }

    @Override
    public Result parse(ParseContext context) {
        Result node = starting.parse(context);
        if (Function.shouldReturn(node)) return node;
        return node.getContent().execute(scope, name, context, arguments);
    }

    @Override
    public List<Node> getChildren() {
        return arguments;
    }

    @Override
    public void addChild(Node node) {

    }

    @Override
    public String toString() {
        return "<dot " + name + ">";
    }
}
