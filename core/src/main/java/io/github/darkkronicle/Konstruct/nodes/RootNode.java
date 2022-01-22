package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * A node that contains only children and when evaluate will concat all the children together
 */
public class RootNode implements Node {

    private List<Node> children;
    @Getter
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
        Result result = null;
        // Check to see if it has the global modifier. If so, run everything else then work into it
        for (Node child : children) {
            Result result2 = child.parse(context);
            if (Function.shouldReturn(result2)) {
                return result;
            }
            if (result == null) {
                result = result2;
            } else {
                result = Result.success(result.getContent().add(result2.getContent()));
            }
        }
        return result;
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
