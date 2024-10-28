package io.github.darkkronicle.konstruct.reader.builder;

import io.github.darkkronicle.konstruct.NodeException;
import io.github.darkkronicle.konstruct.nodes.AssignmentNode;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.nodes.VariableNode;
import io.github.darkkronicle.konstruct.reader.Token;
import io.github.darkkronicle.konstruct.reader.Tokener;

import java.util.Optional;

public class IdentifierBuilder implements Builder {

    private int lastToken = 0;

    @Override
    public Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException {
        lastToken = currentToken;
        String name = reader.get(lastToken).content;
        lastToken++;
        if (reader.size() - 1 <= lastToken) {
            return Optional.of(new VariableNode(name));
        }
        Token.TokenType next = reader.get(lastToken).tokenType;
        if (next == Token.TokenType.PAREN_OPEN) {
            Builder function = new FunctionBuilder(name);
            Optional<Node> func = function.build(scope, reader, lastToken);
            lastToken = function.getNextToken();
            return func;
        }
        if (next == Token.TokenType.ASSIGNMENT) {
            lastToken++;
            int previous = lastToken;
            lastToken = NodeBuilder.subToEnd(reader, lastToken);
            Optional<Node> node = Optional.of(new AssignmentNode(name, new NodeBuilder(reader.split(previous, lastToken), scope).build()));
            return node;
        }
        return Optional.of(new VariableNode(name));
    }

    @Override
    public int getNextToken() {
        return lastToken;
    }

}
