package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.Optional;

/**
 * A builder class to create a {@link Node}
 */
public interface Builder {

    /**
     * Build's a {@link Node}
     * @return Built {@link Node}. If empty don't add
     */
    Optional<Node> build() throws NodeException;

    /**
     * The current cursor position of the string. This should be at the end of the created {@link Node}
     * @return Position of cursor
     */
    int getCursor();

}
