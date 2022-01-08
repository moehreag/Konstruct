package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.NodeContext;

import java.util.ArrayList;
import java.util.List;

public class VariableNode implements Node {

    private final String key;

    public VariableNode(String key) {
        this.key = key;
    }

    @Override
    public String parse(NodeContext context) {
        return context.getVariable(key).orElse("");
    }

    @Override
    public List<Node> getChildren() {
        // No children
        return new ArrayList<>();
    }

    @Override
    public void addChild(Node node) {

    }

    @Override
    public String toString() {
        return "<variable " + key + ">";
    }
}
