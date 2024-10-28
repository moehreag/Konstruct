package io.github.darkkronicle.konstruct.reader.builder;

import io.github.darkkronicle.konstruct.NodeException;
import io.github.darkkronicle.konstruct.nodes.BooleanNode;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.reader.Token;
import io.github.darkkronicle.konstruct.reader.Tokener;

import java.util.Optional;

public class KeywordBuilder implements Builder {

    private int lastToken;

    @Override
    public Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException {
        String keyword = reader.get(currentToken).content;
        currentToken++;
        lastToken = currentToken;
        boolean bool = keyword.equals("true");
        if (bool || keyword.equals("false")) {
            return Optional.of(new BooleanNode(bool));
        }
        if (keyword.equals("if")) {
            IfBuilder ifBuilder = new IfBuilder();
            Optional<Node> node = ifBuilder.build(scope, reader, currentToken);
            lastToken = ifBuilder.getNextToken();
            return node;
        }
        if (keyword.equals("while")) {
            WhileBuilder builder = new WhileBuilder();
            Optional<Node> node = builder.build(scope, reader, currentToken);
            lastToken = builder.getNextToken();
            return node;
        }
        if (keyword.equals("def")) {
            FunctionDefinitionBuilder function = new FunctionDefinitionBuilder();
            Optional<Node> node = function.build(scope, reader, currentToken);
            lastToken = function.getNextToken();
            return node;
        }
        Token nextToken = reader.get(lastToken);
        if (nextToken.tokenType == Token.TokenType.PAREN_OPEN) {
            FunctionBuilder function = new FunctionBuilder(keyword);
            Optional<Node> f = function.build(scope, reader, currentToken);
            lastToken = function.getNextToken();
            return f;
        }
        throw new NodeException("Unexpected keyword " + keyword);
    }

    @Override
    public int getNextToken() {
        return lastToken;
    }
}
