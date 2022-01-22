package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.AssignmentNode;
import io.github.darkkronicle.Konstruct.nodes.FunctionNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.reader.Token;
import io.github.darkkronicle.Konstruct.reader.Tokener;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class to build a {@link FunctionNode}
 */
public class FunctionBuilder implements Builder {

    private int lastToken;
    private final String name;

    public FunctionBuilder(String name) {
        this.name = name;
    }

    @Override
    public int getNextToken() {
        return lastToken;
    }

    @Override
    public Optional<Node> build(Tokener reader, int currentToken) throws NodeException {
        lastToken++;
        return Optional.empty();
    }

}
