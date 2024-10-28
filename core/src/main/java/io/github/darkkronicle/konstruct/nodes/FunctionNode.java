package io.github.darkkronicle.konstruct.nodes;

import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.NodeException;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.functions.Function;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A node to evaluate a {@link Function}
 *
 * <p>This node's arguments are processed as {@link Node} so there can be nested functions or arguments</p>
 */
public class FunctionNode implements Node {

    private final String name;
    @Getter
    private final List<String> modifiers;
    private final List<Node> arguments;
    private final int scope;

    public FunctionNode(String rawName, List<Node> arguments, int scope) {
        this.scope = scope;
        Matcher matcher = Pattern.compile("\\b(\\w+)$").matcher(rawName);
        if (matcher.find()) {
            // Name should just be the last bit
            this.name = matcher.group(1);
            String mods = rawName.substring(0, matcher.start(1)).strip();
            // Modifiers are anything that isn't the name
            this.modifiers = Stream.of(mods.toCharArray()).map(String::valueOf).collect(Collectors.toList());
        } else {
            throw new NodeException(rawName + " is an invalid function name!");
        }
        this.arguments = arguments;
    }

    @Override
    public Result parse(ParseContext context) {
        Optional<Function> function = context.getFunction(name);
        if (function.isEmpty()) {
            throw new NodeException("No function named " + name + " defined!");
        }
        List<Node> argumentsList = new ArrayList<>(arguments);
        if (!function.get().getArgumentCount().isInRange(argumentsList.size())) {
            throw new NodeException("Too many arguments! " + this);
        }
        Result result = function.get().parse(context, argumentsList);
        if (Function.shouldReturn(result)) {
            return result;
        }
        return new Result(Result.ResultType.SUCCESS, result.getContent(), scope);
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
