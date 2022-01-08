package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.NodeContext;

import java.util.ArrayList;
import java.util.List;

public class LiteralNode implements Node {

    private final String content;

    public LiteralNode(String content) {
        this.content = content;
    }

    @Override
    public String parse(NodeContext context) {
        return content;
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<>(0);
    }

    @Override
    public void addChild(Node node) {

    }

    @Override
    public String toString() {
        return "<literal " + content + ">";
    }
}
