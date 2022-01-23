package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to take a {@link ParseContext} and evaluate to return a processed string
 */
public interface Node {

    /**
     * Parses a {@link Node} to get an evaluated string
     * @param context {@link ParseContext} containing {@link io.github.darkkronicle.Konstruct.functions.Variable}'s and {@link io.github.darkkronicle.Konstruct.functions.Function}'s
     * @return Evaluated {@link Result}
     */
    Result parse(ParseContext context);

    /**
     * Returns children node of this node. Can be empty, but shouldn't be null.
     * @return Children
     */
    List<Node> getChildren();

    /**
     * Adds a {@link Node} to this node. By itself it may not automatically parse.
     * @param node {@link Node} to add
     */
    void addChild(Node node);

    /**
     * A debugging method to convert this node into a string with indenting to show all children
     * @return String containing all children
     */
    default String getTreeString() {
        return String.join("\n", getTreeString(this));
    }

    /**
     * A debugging method to convert a {@link Node} into a {@link List} of strings with indenting
     * @param node {@link Node} to construct a tree
     * @return {@link List} of all the children nodes
     */
    static List<String> getTreeString(Node node) {
        List<String> strings = new ArrayList<>();
        strings.add("- " +node.toString());
        for (Node child : node.getChildren()) {
            for (String string : getTreeString(child)) {
                strings.add("| " + string);
            }
        }
        return strings;
    }

}
