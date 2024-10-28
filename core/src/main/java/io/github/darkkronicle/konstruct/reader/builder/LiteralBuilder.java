package io.github.darkkronicle.konstruct.reader.builder;

import io.github.darkkronicle.konstruct.NodeException;
import io.github.darkkronicle.konstruct.nodes.LiteralNode;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.reader.Tokener;

import java.util.Optional;

public class LiteralBuilder implements Builder {


    private int nextToken;

    @Override
    public Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException {
        // Guaranteed to be a literal
        nextToken = currentToken + 1;
        return Optional.of(new LiteralNode(reader.get(currentToken).content));
    }

    @Override
    public int getNextToken() {
        return nextToken;
    }
}
