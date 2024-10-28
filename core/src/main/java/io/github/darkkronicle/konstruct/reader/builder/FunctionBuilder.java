package io.github.darkkronicle.konstruct.reader.builder;

import io.github.darkkronicle.konstruct.NodeException;
import io.github.darkkronicle.konstruct.nodes.FunctionNode;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.nodes.RootNode;
import io.github.darkkronicle.konstruct.reader.Token;
import io.github.darkkronicle.konstruct.reader.Tokener;

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
    public Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException {
        int closingIndex = getToClosingParen(reader, currentToken);
        Tokener arguments = reader.split(currentToken + 1, closingIndex);
        List<Tokener> splitArguments = getSplitArguments(arguments);
        List<Node> nodes = new ArrayList<>();
        for (Tokener token : splitArguments) {
            RootNode root = new NodeBuilder(token, scope).build();
            if (root.getChildren().size() > 0 || root.getPrecommands().size() > 0) {
                nodes.add(root);
            }
        }
        lastToken = closingIndex + 1;
        return Optional.of(new FunctionNode(name, nodes, scope));
    }

    public static List<Tokener> getSplitArguments(Tokener tokener) {
        List<Tokener> tokeners = new ArrayList<>();
        int lastIndex = 0;
        int currentParenths = 0;
        for (int i = 0; i < tokener.size(); i++) {
            Token currentToken = tokener.get(i);
            if (currentToken.tokenType == Token.TokenType.PAREN_OPEN) {
                currentParenths++;
            } else if (currentToken.tokenType == Token.TokenType.PAREN_CLOSE) {
                currentParenths--;
            } else if (currentParenths == 0 && currentToken.tokenType == Token.TokenType.ARGUMENTS_DELIMINATOR) {
                tokeners.add(tokener.split(lastIndex, i));
                lastIndex = i + 1;
            }
        }
        tokeners.add(tokener.split(lastIndex, tokener.size()));
        return tokeners;
    }

    public static int getToClosingParen(Tokener tokener, int index) {
        int currentParens = 0;
        for (int i = index; i < tokener.size(); i++) {
            Token.TokenType type = tokener.get(i).tokenType;
            switch (type) {
                case PAREN_OPEN -> currentParens++;
                case PAREN_CLOSE -> currentParens--;
            }
            if (currentParens == 0) {
                return i;
            }
        }
        return tokener.size();
    }

}
