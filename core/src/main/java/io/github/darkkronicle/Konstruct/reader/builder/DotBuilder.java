package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.DotNode;
import io.github.darkkronicle.Konstruct.nodes.FunctionNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.RootNode;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.reader.Token;
import io.github.darkkronicle.Konstruct.reader.Tokener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DotBuilder implements Builder {

    private final Node starting;
    private int lastToken;

    public DotBuilder(Node original) {
        this.starting = original;
    }

    @Override
    public Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException {
        currentToken++;
        if (reader.get(currentToken).tokenType != Token.TokenType.IDENTIFIER) {
            throw new NodeException("Dot has to be followed by function name!");
        }
        String name = reader.get(currentToken).content;
        lastToken = currentToken + 1;
        int closingIndex = FunctionBuilder.getToClosingParen(reader, lastToken);
        Tokener arguments = reader.split(lastToken + 1, closingIndex);
        List<Tokener> splitArguments = FunctionBuilder.getSplitArguments(arguments);
        List<Node> nodes = new ArrayList<>();
        for (Tokener token : splitArguments) {
            RootNode root = new NodeBuilder(token, scope).build();
            if (root.getChildren().size() > 0 || root.getPrecommands().size() > 0) {
                nodes.add(root);
            }
        }
        lastToken = closingIndex + 1;
        return Optional.of(new DotNode(starting, name, nodes, scope));
    }

    @Override
    public int getNextToken() {
        return lastToken;
    }

}
