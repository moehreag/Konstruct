package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.Variable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class AssignmentNode implements Node {

    @Getter
    private final String name;

    @Getter
    private final Node node;

    public AssignmentNode(String name, Node node) {
        this.name = name;
        this.node = node;
    }

    @Override
    public List<Node> getChildren() {
        // No children
        return List.of(node);
    }

    @Override
    public Result parse(ParseContext context) {
        if (context.getVariables().containsKey(this.name)) {
            throw new NodeException("Variable " + this.name + "already exists!");
        }
        Result result = this.node.parse(context);
        if (Function.shouldReturn(result)) {
            return result;
        }
        context.addLocalVariable(this.name, Variable.of(result.getContent()));
        return Result.success("");
    }

    @Override
    public void addChild(Node node) {
        // No children
    }

    @Override
    public String toString() {
        return "<assignment " + name + ">";
    }

}
