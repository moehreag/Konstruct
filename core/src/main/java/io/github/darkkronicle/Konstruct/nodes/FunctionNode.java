package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.functions.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A node to evaluate a {@link Function}
 *
 * <p>This node's arguments are processed as {@link Node} so there can be nested functions or arguments</p>
 */
public class FunctionNode implements Node {

    private final String name;
    private final List<Node> arguments;

    public FunctionNode(String name, List<Node> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public String parse(ParseContext context) {
        Optional<Function> function = context.getFunction(name);
        if (function.isEmpty()) {
            throw new NodeException("No function named " + name + " defined!");
        }
        List<String> parsedArguments = new ArrayList<>();
        for (Node child : getChildren()) {
            parsedArguments.add(child.parse(context).strip());
        }
        if (!function.get().getArgumentCount().isInRange(parsedArguments.size())) {
            throw new NodeException("Too many arguments! " + this);
        }
        return function.get().parse(parsedArguments);
    }

    @Override
    public List<Node> getChildren() {
        return arguments;
    }

    @Override
    public void addChild(Node node) {
        this.arguments.add(node);
    }

    @Override
    public String toString() {
        return "<function " + name + "(" + String.join(",", arguments.stream().map(Object::toString).toList()) + ")>";
    }
}
