package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.ParseContext;

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
        for (Node child : children) {
            builder.append(child.parse(context));
        }
        return builder.toString();
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
