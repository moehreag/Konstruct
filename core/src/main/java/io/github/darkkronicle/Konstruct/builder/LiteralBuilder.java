package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.nodes.LiteralNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.reader.StringReader;
import io.github.darkkronicle.Konstruct.reader.Token;
import lombok.Getter;

public class LiteralBuilder implements Builder {

    private StringReader reader;
    @Getter
    private int cursor = 0;

    public LiteralBuilder(StringReader string) {
        this.reader = string;
    }

    @Override
    public Node build() {
        cursor = 0;
        StringBuilder builder = new StringBuilder();
        while (cursor < reader.length()) {
            Token token = reader.get(cursor);
            if (token.tokenType != Token.TokenType.LITERAL) {
                break;
            }
            builder.append(token.c);
            cursor++;
        }
        if (cursor == 0) {
            cursor = 1;
        }
        return new LiteralNode(builder.toString());
    }

}
