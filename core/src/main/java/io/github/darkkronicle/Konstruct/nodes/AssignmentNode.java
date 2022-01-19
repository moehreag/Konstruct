package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.functions.Variable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class AssignmentNode implements Node {

    @Getter
    private final String name;

    @Getter
    private final Node node;

    public AssignmentNode(String name, List<Node> children) {
        this.name = name;
        this.node = new RootNode(children);
    }

    @Override
    public List<Node> getChildren() {
        // No children
        return new ArrayList<>();
    }

    @Override
    public String parse(ParseContext context) {
        if (context.getVariables().containsKey(this.name)) {
            throw new NodeException("Variable " + this.name + "already exists!");
        }
        context.getLocalVariables().put(this.name, Variable.of(this.node.parse(context)));
        return "";
    }

    @Override
    public void addChild(Node node) {
        // No children
    }

}
