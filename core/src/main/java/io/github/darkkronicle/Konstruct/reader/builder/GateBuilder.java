package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.Gate;
import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.GateNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.reader.Token;
import io.github.darkkronicle.Konstruct.reader.Tokener;

import java.util.Optional;

public class GateBuilder implements Builder {

    private final Node starting;
    private int nextToken;

    public GateBuilder(Node original) {
        this.starting = original;
    }

    @Override
    public Optional<Node> build(Tokener reader, int currentToken) throws NodeException {
        Gate gate = Token.GATES.get(reader.get(currentToken).content);
        nextToken = currentToken + 1;
        if (nextToken >= reader.size()) {
            throw new NodeException("Gate " + gate.name() + " is unmatched!");
        }
        int next = toNextGate(reader, nextToken);
        Node inside = new NodeBuilder(reader.split(nextToken, next)).build();
        if (inside.getChildren().size() == 1) {
            inside = inside.getChildren().get(0);
        }
        nextToken = next;
        return Optional.of(new GateNode(starting, inside, gate));
    }

    @Override
    public int getNextToken() {
        return nextToken;
    }

    public static int toNextGate(Tokener tokener, int index) {
        int currentBlocks = 0;
        int currentParens = 0;
        for (int i = index; i < tokener.size(); i++) {
            Token token = tokener.get(i);
            Token.TokenType type = token.tokenType;
            switch (type) {
                case PAREN_OPEN -> currentParens++;
                case PAREN_CLOSE -> currentParens--;
                case BLOCK_START -> currentBlocks++;
                case BLOCK_END -> currentBlocks--;
            }
            if (currentParens == 0 && currentBlocks == 0 && (type == Token.TokenType.END_LINE || (type == Token.TokenType.KEYWORD && Token.GATES.containsKey(token.content)))) {
                return i;
            }
        }
        return tokener.size();
    }
}
