package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.DoubleNode;
import io.github.darkkronicle.Konstruct.nodes.IntNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.reader.Token;
import io.github.darkkronicle.Konstruct.reader.Tokener;

import java.util.Optional;

public class NumberBuilder implements Builder {

    private int nextToken;

    @Override
    public Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException {
        Token token = reader.get(currentToken);
        nextToken = currentToken + 1;
        if (token.tokenType == Token.TokenType.INT) {
            return Optional.of(new IntNode(Integer.parseInt(token.content)));
        }
        if (token.tokenType == Token.TokenType.DOUBLE) {
            return Optional.of(new DoubleNode(Double.parseDouble(token.content)));
        }
        return Optional.empty();
    }

    @Override
    public int getNextToken() {
        return nextToken;
    }

}
