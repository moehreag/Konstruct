package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.Result;
import io.github.darkkronicle.Konstruct.functions.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * A node that will evaluate {@link Variable}'s
 */
public class VariableNode implements Node {

    private final String key;

    public VariableNode(String key) {
        this.key = key;
    }

    @Override
    public Result parse(ParseContext context) {
        return Result.success(context.getVariable(key).orElse(Variable.of("")).getValue());
    }

    @Override
    public List<Node> getChildren() {
        // No children
        return new ArrayList<>();
    }

    @Override
    public void addChild(Node node) {}

    @Override
    public String toString() {
        return "<variable " + key + ">";
    }
}
