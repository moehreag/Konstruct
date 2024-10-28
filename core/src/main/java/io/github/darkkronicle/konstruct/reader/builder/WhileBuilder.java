package io.github.darkkronicle.konstruct.reader.builder;

import io.github.darkkronicle.konstruct.NodeException;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.nodes.WhileNode;
import io.github.darkkronicle.konstruct.reader.Token;
import io.github.darkkronicle.konstruct.reader.Tokener;

import java.util.Optional;

public class WhileBuilder implements Builder {

    private int nextToken;

    @Override
    public Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException {
        nextToken = currentToken;
        if (reader.get(nextToken).tokenType != Token.TokenType.PAREN_OPEN) {
            throw new NodeException("Open parenthesis is required after a while loop!");
        }
        int closingParen = FunctionBuilder.getToClosingParen(reader, currentToken);
        Node condition = new NodeBuilder(reader.split(nextToken + 1, closingParen), scope).build();
        nextToken = closingParen + 1;
        if (reader.get(nextToken).tokenType != Token.TokenType.BLOCK_START) {
            throw new NodeException("After a while loop is declared the body has to be within {}'s!");
        }

        int closingBlock = IfBuilder.getToEndCodeBlock(reader, nextToken);
        Node body = new NodeBuilder(reader.split(nextToken + 1, closingBlock), scope).build();
        nextToken = closingBlock + 1;
        return Optional.of(new WhileNode(condition, body));
    }

    @Override
    public int getNextToken() {
        return nextToken;
    }
}
