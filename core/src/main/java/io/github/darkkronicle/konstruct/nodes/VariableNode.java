package io.github.darkkronicle.konstruct.nodes;

import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.functions.Variable;
import io.github.darkkronicle.konstruct.type.NullObject;

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
        return new Result(Result.ResultType.SUCCESS, context.getVariable(key).orElse(Variable.of(new NullObject())).getValue());
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
