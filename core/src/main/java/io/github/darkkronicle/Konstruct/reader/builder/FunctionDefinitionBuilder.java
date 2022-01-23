package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.FunctionDefinitionNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.reader.Token;
import io.github.darkkronicle.Konstruct.reader.Tokener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FunctionDefinitionBuilder implements Builder {

    private int lastToken;

    @Override
    public Optional<Node> build(Tokener reader, int currentToken) throws NodeException {
        lastToken = currentToken;
        if (reader.get(lastToken).tokenType != Token.TokenType.IDENTIFIER) {
            throw new NodeException("Function name has to be after def!");
        }
        String name = reader.get(lastToken).content;
        lastToken++;
        if (reader.get(lastToken).tokenType != Token.TokenType.PAREN_OPEN) {
            throw new NodeException("Function declaration has to be followed by (");
        }
        int endingParenthesis = FunctionBuilder.getToClosingParen(reader, lastToken);
        Tokener args = reader.split(lastToken + 1, endingParenthesis);
        lastToken = endingParenthesis + 1;
        List<String> arguments = getSplitArguments(FunctionBuilder.getSplitArguments(args));

        if (reader.get(lastToken).tokenType != Token.TokenType.BLOCK_START) {
            throw new NodeException("After a function declaration a { is required!");
        }

        int endOfBody = IfBuilder.getToEndCodeBlock(reader, lastToken);
        Node body = new NodeBuilder(reader.split(lastToken + 1, endOfBody)).build();
        lastToken = endOfBody + 1;
        return Optional.of(new FunctionDefinitionNode(name, body, arguments));
    }

    public static List<String> getSplitArguments(List<Tokener> tokeners) {
        List<String> argNames = new ArrayList<>();
        for (Tokener t : tokeners) {
            if (t.size() != 1) {
                throw new NodeException("Argument definitions have to be one node!");
            }
            Token token = t.get(0);
            if (token.tokenType != Token.TokenType.IDENTIFIER) {
                throw new NodeException("Argument names have to be an identifier!");
            }
            argNames.add(token.content);
        }
        return argNames;
    }

    @Override
    public int getNextToken() {
        return lastToken;
    }

}
