package io.github.darkkronicle.konstruct.reader.builder;

import io.github.darkkronicle.konstruct.NodeException;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.reader.Token;
import io.github.darkkronicle.konstruct.reader.Tokener;

import java.util.Optional;

public class ParenBuilder implements Builder {

    private int nextToken;

    @Override
    public Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException {
        int ending = getNextParen(reader, currentToken);
        Node built = new NodeBuilder(reader.split(currentToken + 1, ending), scope).build();
        nextToken = ending + 1;
        return Optional.of(built);
    }

    @Override
    public int getNextToken() {
        return nextToken;
    }

    public static int getNextParen(Tokener tokener, int index) {
        int currentBlocks = 0;
        int currentParens = 0;
        for (int i = index; i < tokener.size(); i++) {
            Token.TokenType type = tokener.get(i).tokenType;
            switch (type) {
                case PAREN_OPEN -> currentParens++;
                case PAREN_CLOSE -> currentParens--;
                case BLOCK_START -> currentBlocks++;
                case BLOCK_END -> currentBlocks--;
            }
            if (currentParens == 0 && currentBlocks == 0) {
                return i;
            }
        }
        return tokener.size();
    }
}
