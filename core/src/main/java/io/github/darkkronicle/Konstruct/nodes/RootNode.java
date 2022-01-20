package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * A node that contains only children and when evaluate will concat all the children together
 */
public class RootNode implements Node {

    private List<Node> children;
    private List<Node> precommands;

    public RootNode(List<Node> precommands, List<Node> children) {
        this.children = children;
        this.precommands = precommands;
    }

    public RootNode(List<Node> children) {
        this(new ArrayList<>(), children);
    }

    @Override
    public Result parse(ParseContext context) {
        for (Node pre : precommands) {
            pre.parse(context);
        }
        StringBuilder builder = new StringBuilder();
        FunctionNode baseNode = null;
        // Check to see if it has the global modifier. If so, run everything else then work into it
        if (children.size() != 0 && children.get(0) instanceof FunctionNode func) {
            List<String> modifiers = func.getModifiers();
            if (modifiers.contains("!")) {
                baseNode = func;
            }
        }
        boolean first = false;
        for (Node child : children) {
            if (!first && baseNode != null) {
                first = true;
                continue;
            }
            Result result = child.parse(context);
            if (Function.shouldReturn(result)) {
                return result;
            }
            builder.append(result.getContent());
        }
        if (baseNode == null) {
            return Result.success(builder.toString());
        }
        context.getVariables().put("%", Variable.of(builder.toString()));
        return baseNode.parse(context);
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public void addChild(Node node) {
        this.children.add(node);
    }

    @Override
    public String toString() {
        return "<root>";
    }

}
