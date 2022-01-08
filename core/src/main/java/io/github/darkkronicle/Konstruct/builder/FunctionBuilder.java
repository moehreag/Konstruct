package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.nodes.FunctionNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.reader.StringReader;
import io.github.darkkronicle.Konstruct.reader.Token;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FunctionBuilder implements Builder {

    @Getter
    private int cursor = 0;
    private StringReader reader;

    public FunctionBuilder(StringReader reader) {
        this.reader = reader;
    }

    @Override
    public Node build() {
        cursor = 1;
        String functionName = null;
        int funcs = 1;
        int args = 0;
        List<Node> children = new ArrayList<>();

        StringBuilder literalBuilder = new StringBuilder();
        List<Token> argumentBuilder = new ArrayList<>();
        List<List<Token>> arguments = new ArrayList<>();

        while (cursor < reader.length()) {
            Token token = reader.get(cursor);
            if (token.tokenType == Token.TokenType.LITERAL) {
                if (functionName == null) {
                    literalBuilder.append(token.c);
                    cursor++;
                } else {
                    argumentBuilder.add(token);
                    cursor++;
                }
                continue;
            }
            if (token.tokenType == Token.TokenType.ARGUMENTS_START) {
                if (functionName == null) {
                    functionName = literalBuilder.toString();
                    literalBuilder = new StringBuilder();
                } else {
                    argumentBuilder.add(token);
                }
                cursor++;
                args++;
                continue;
            }
            if (token.tokenType == Token.TokenType.ARGUMENTS_DELIMINATOR && args == 1) {
                arguments.add(argumentBuilder);
                argumentBuilder = new ArrayList<>();
                cursor++;
                continue;
            }
            if (token.tokenType == Token.TokenType.FUNCTION_START) {
                argumentBuilder.add(token);
                cursor++;
                funcs++;
                continue;
            }
            if (token.tokenType == Token.TokenType.ARGUMENTS_END) {
                cursor++;
                args--;
                if (args > 0) {
                    argumentBuilder.add(token);
                }
                continue;
            }
            if (token.tokenType == Token.TokenType.FUNCTION_END) {
                cursor++;
                funcs--;
                if (funcs == 0) {
                    arguments.add(argumentBuilder);
                    break;
                } else {
                    argumentBuilder.add(token);
                }
                continue;
            }
            argumentBuilder.add(token);
            cursor++;
        }
        for (List<Token> s : arguments) {
            children.add(new NodeBuilder(new StringReader(reader.getInput(), reader.getSettings(), s)).build());
        }
        return new FunctionNode(functionName, children);
    }

}
