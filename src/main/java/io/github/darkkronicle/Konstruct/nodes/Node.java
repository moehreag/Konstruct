package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.NodeContext;

import java.util.ArrayList;
import java.util.List;

public interface Node {

    String parse(NodeContext context);

    List<Node> getChildren();

    void addChild(Node node);

    default String getTreeString() {
        return String.join("\n", getTreeString(this));
    }

    static List<String> getTreeString(Node node) {
        List<String> strings = new ArrayList<>();
        strings.add("- " +node.toString());
        for (Node child : node.getChildren()) {
            for (String string : getTreeString(child)) {
                strings.add("  " + string);
            }
        }
        return strings;
    }

}
