package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.reader.Tokener;

import java.util.Optional;

public class KeywordBuilder implements Builder {

    private int lastToken;

    @Override
    public Optional<Node> build(Tokener reader, int currentToken) throws NodeException {
        String keyword = reader.get(currentToken).content;
        currentToken++;
        lastToken = currentToken;
        if (keyword.equals("def")) {

        }
        return Optional.empty();
    }

    @Override
    public int getNextToken() {
        return lastToken;
    }
}
