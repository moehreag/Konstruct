package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.IfNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.RootNode;
import io.github.darkkronicle.Konstruct.reader.Token;
import io.github.darkkronicle.Konstruct.reader.Tokener;

import java.util.Optional;

public class IfBuilder implements Builder {

    private int lastToken;

    @Override
    public Optional<Node> build(Tokener reader, int currentToken) throws NodeException {
        lastToken = currentToken;
        if (reader.get(lastToken).tokenType != Token.TokenType.PAREN_OPEN) {
            throw new NodeException("If has to be followed by an open parenthesis!");
        }
        int endingParenIndex = FunctionBuilder.getToClosingParen(reader, lastToken);
        Node conditionNode = new NodeBuilder(reader.split(lastToken + 1, endingParenIndex)).build();
        lastToken = endingParenIndex + 1;
        if (reader.get(lastToken).tokenType != Token.TokenType.BLOCK_START) {
            throw new NodeException("If statement has to be followed by a code block start!");
        }
        int endingBlockIndex = getToEndCodeBlock(reader, lastToken);
        Node inner = new NodeBuilder(reader.split(lastToken + 1, endingBlockIndex)).build();
        lastToken = endingBlockIndex + 1;
        if (lastToken >= reader.size()) {
            return Optional.of(new IfNode(conditionNode, inner, null));
        }
        Token next = reader.get(lastToken);

        if (next.tokenType != Token.TokenType.KEYWORD && (!next.content.equals("elif") && !next.content.equals("else"))) {
            return Optional.of(new IfNode(conditionNode, inner, null));
        }

        lastToken++;
        if (next.content.equals("elif")) {
            IfBuilder nested = new IfBuilder();
            Optional<Node> node = nested.build(reader, lastToken);
            lastToken = nested.getNextToken();
            return Optional.of(new IfNode(conditionNode, inner, node.get()));
        } else {
            // Else part
            if (reader.get(lastToken).tokenType != Token.TokenType.BLOCK_START) {
                throw new NodeException("Else statement has to be followed by a code block start!");
            }
            int endingElseBlockIndex = getToEndCodeBlock(reader, lastToken);
            Node elseNode = new NodeBuilder(reader.split(lastToken + 1, endingElseBlockIndex)).build();
            lastToken = endingElseBlockIndex + 1;
            return Optional.of(new IfNode(conditionNode, inner, elseNode));
        }
    }

    @Override
    public int getNextToken() {
        return lastToken;
    }

    public static int getToEndCodeBlock(Tokener tokener, int index) {
        int currentBlock = 0;
        for (int i = index; i < tokener.size(); i++) {
            Token.TokenType type = tokener.get(i).tokenType;
            switch (type) {
                case BLOCK_START -> currentBlock++;
                case BLOCK_END -> currentBlock--;
            }
            if (currentBlock == 0) {
                return i;
            }
        }
        return tokener.size();
    }

}
