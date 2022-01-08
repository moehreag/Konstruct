package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.nodes.FunctionNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FunctionBuilder implements Builder {

    @Getter
    private int cursor = 0;
    private String string;

    public FunctionBuilder(String string) {
        this.string = string;
    }

    @Override
    public Node build() {
        cursor = 1;
        String argumentString = "";
        String functionName = "";
        int argumentStart = -1;
        int funcs = 0;
        List<Node> children = new ArrayList<>();
        while (cursor < string.length()) {
            if (argumentStart < 0 && isArgumentStart()) {
                functionName = string.substring(1, cursor);
                cursor++;
                argumentStart = cursor;
                continue;
            }
            if (argumentStart >= 0) {
                if (isArgumentStart()) {
                    funcs++;
                } else if (isArgumentEnd()) {
                    if (funcs <= 0) {
                        argumentString = string.substring(argumentStart, cursor);
                        cursor += 2;
                        break;
                    }
                    funcs--;
                }
            }
            cursor++;
        }
        for (String s : argumentString.split(",")) {
            children.add(new NodeBuilder(s).build());
        }
        return new FunctionNode(functionName, children);
    }

    private boolean isArgumentStart() {
        char c = string.charAt(cursor);
        return c == NodeBuilder.FUNCTION_ARG_START;
    }

    private boolean isArgumentEnd() {
        char c = string.charAt(cursor);
        return c == NodeBuilder.FUNCTION_ARG_END;
    }

}
