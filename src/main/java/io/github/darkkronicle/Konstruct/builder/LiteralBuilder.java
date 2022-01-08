package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.nodes.LiteralNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import lombok.Getter;

public class LiteralBuilder implements Builder {

    private String string;
    @Getter
    private int cursor = 0;

    public LiteralBuilder(String string) {
        this.string = string;
    }

    @Override
    public Node build() {
        cursor = 1;
        while (cursor < string.length()) {
            if (isDifferent()) {
                return new LiteralNode(string.substring(0, cursor));
            }
            cursor++;
        }
        return new LiteralNode(string);
    }

    private boolean isDifferent() {
        char c = string.charAt(cursor);
        return c == NodeBuilder.FUNCTION_START || c == NodeBuilder.VARIABLE_START;
    }

}
