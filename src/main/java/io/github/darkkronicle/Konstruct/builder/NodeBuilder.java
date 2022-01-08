package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.RootNode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class NodeBuilder implements Builder {

    public final static char FUNCTION_START = '[';
    public final static char FUNCTION_END = ']';

    public final static char FUNCTION_ARG_START = '(';
    public final static char FUNCTION_ARG_END = ')';

    public final static char VARIABLE_START = '{';
    public final static char VARIABLE_END = '}';

    @Getter
    private int cursor = 0;
    private String string;

    public NodeBuilder(String string) {
        this.string = string;
    }

    @Override
    public RootNode build() {
        List<Node> children = new ArrayList<>();
        cursor = 0;
        while (cursor < string.length()) {
            char c = string.charAt(cursor);
            Builder builder = switch (c) {
                case VARIABLE_START -> new VariableBuilder(string.substring(cursor));
                case FUNCTION_START -> new FunctionBuilder(string.substring(cursor));
                default -> new LiteralBuilder(string.substring(cursor));
            };
            Node node = builder.build();
            if (node == null) {
                throw new NodeException("Invalid node!");
            }
            children.add(node);
            cursor += builder.getCursor();
        }
        return new RootNode(children);
    }

}
