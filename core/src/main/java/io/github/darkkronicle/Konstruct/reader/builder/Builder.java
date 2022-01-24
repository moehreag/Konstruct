package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.reader.Tokener;

import java.util.Optional;

/**
 * A builder class to create a {@link Node}
 */
public interface Builder {

    /**
     * Build's a {@link Node}
     * @return Built {@link Node}. If empty don't add
     */
    Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException;

    /**
     * Returns the index of the next token to be processed
     * @return Next token
     */
    int getNextToken();

}
