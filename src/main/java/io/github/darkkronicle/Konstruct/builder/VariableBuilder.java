package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.VariableNode;
import lombok.Getter;

public class VariableBuilder implements Builder {

    @Getter
    private int cursor = 0;
    private String string;

    public VariableBuilder(String string) {
        this.string = string;
    }

    @Override
    public Node build() {
        cursor = 1;
        while (cursor < string.length()) {
            char c = string.charAt(cursor);
            if (c == NodeBuilder.VARIABLE_END) {
                cursor++;
                return new VariableNode(string.substring(1, cursor - 1));
            }
            cursor++;
        }
        return null;
    }
}
