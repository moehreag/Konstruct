package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.VariableNode;
import io.github.darkkronicle.Konstruct.reader.StringReader;
import io.github.darkkronicle.Konstruct.reader.Token;
import lombok.Getter;

public class VariableBuilder implements Builder {

    @Getter
    private int cursor = 0;
    private StringReader reader;

    public VariableBuilder(StringReader string) {
        this.reader = string;
    }

    @Override
    public Node build() {
        cursor = 1;
        StringBuilder builder = new StringBuilder();
        while (cursor < reader.length()) {
            Token token = reader.get(cursor);
            if (token.tokenType == Token.TokenType.LITERAL) {
                builder.append(token.c);
                cursor++;
                continue;
            }
            if (token.tokenType == Token.TokenType.VARIABLE_END) {
                cursor++;
                return new VariableNode(builder.toString());
            }
            throw new NodeException("Invalid character within variable!");
        }
        throw new NodeException("Argument never ended!");
    }
}
