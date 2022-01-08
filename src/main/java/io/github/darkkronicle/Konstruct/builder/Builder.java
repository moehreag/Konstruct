package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.nodes.Node;

public interface Builder {

    Node build();

    int getCursor();

}
