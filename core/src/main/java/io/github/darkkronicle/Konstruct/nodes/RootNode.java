package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.NodeContext;

import java.util.List;

public class RootNode implements Node {

    private List<Node> children;

    public RootNode(List<Node> children) {
        this.children = children;
    }

    @Override
    public String parse(NodeContext context) {
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
