package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.AssignmentNode;
import io.github.darkkronicle.Konstruct.nodes.FunctionNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.reader.Tokenizer;
import io.github.darkkronicle.Konstruct.reader.Token;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class to build a {@link FunctionNode}
 */
public class FunctionBuilder implements Builder {

    @Getter
    private int cursor = 0;
    private Tokenizer reader;

    public FunctionBuilder(Tokenizer reader) {
        this.reader = reader;
    }

    @Override
    public Optional<Node> build() throws NodeException {
        cursor = 1;
        String functionName = null;
        int funcs = 1;
        int args = 0;
        List<Node> children = new ArrayList<>();

        StringBuilder nameBuilder = new StringBuilder();
        List<Token> currentArgument = new ArrayList<>();
        List<List<Token>> arguments = new ArrayList<>();

        boolean isAssignment = false;

        while (cursor < reader.length()) {
            Token token = reader.get(cursor);
            if (token.tokenType == Token.TokenType.LITERAL) {
                if (functionName == null) {
                    nameBuilder.append(token.c);
                } else {
                    currentArgument.add(token);
                }
                cursor++;
            } else if (token.tokenType == Token.TokenType.ARGUMENTS_START) {
                if (functionName == null) {
                    functionName = nameBuilder.toString();
                } else {
                    currentArgument.add(token);
                }
                cursor++;
                args++;
            } else if (token.tokenType == Token.TokenType.ARGUMENTS_DELIMINATOR && args == 1) {
                arguments.add(currentArgument);
                currentArgument = new ArrayList<>();
                cursor++;
            } else if (token.tokenType == Token.TokenType.FUNCTION_START) {
                currentArgument.add(token);
                cursor++;
                funcs++;
            } else if (token.tokenType == Token.TokenType.ARGUMENTS_END) {
                cursor++;
                args--;
                if (args > 0) {
                    currentArgument.add(token);
                }
            } else if (token.tokenType == Token.TokenType.FUNCTION_END) {
                cursor++;
                funcs--;
                if (funcs == 0) {
                    arguments.add(currentArgument);
                    break;
                } else {
                    currentArgument.add(token);
                }
            } else if (token.tokenType == Token.TokenType.ASSIGNMENT && functionName == null) {
                isAssignment = true;
                functionName = nameBuilder.toString();
                cursor++;
            } else {
                currentArgument.add(token);
                cursor++;
            }
        }
        for (List<Token> s : arguments) {
            // Constructs all the children that are in the arguments
            children.add(new NodeBuilder(new Tokenizer(reader.getInput(), reader.getSettings(), s)).build());
        }
        if (functionName == null) {
            functionName = nameBuilder.toString();
        }
        if (functionName.startsWith("#")) {
            // A comment function
            return Optional.empty();
        }
        if (isAssignment) {
            return Optional.of(new AssignmentNode(functionName.strip(), children));
        }
        return Optional.of(new FunctionNode(functionName, children));
    }

}
