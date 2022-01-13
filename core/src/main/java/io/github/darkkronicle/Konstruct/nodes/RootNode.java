package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.functions.Variable;

import java.util.List;

/**
 * A node that contains only children and when evaluate will concat all the children together
 */
public class RootNode implements Node {

    private List<Node> children;

    public RootNode(List<Node> children) {
        this.children = children;
    }

    @Override
    public String parse(ParseContext context) {
        StringBuilder builder = new StringBuilder();
        FunctionNode baseNode = null;
        // Check to see if it has the global modifier. If so, run everything else then work into it
        if (children.get(0) instanceof FunctionNode func) {
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
            builder.append(child.parse(context));
        }
        if (baseNode == null) {
            return builder.toString();
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
