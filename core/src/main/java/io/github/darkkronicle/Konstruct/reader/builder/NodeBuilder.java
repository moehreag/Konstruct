package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.RootNode;
import io.github.darkkronicle.Konstruct.reader.Token;
import io.github.darkkronicle.Konstruct.reader.Tokener;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class to build a {@link RootNode}
 *
 * <p>This should generally be used for any amount of node processing</p>
 */
public class NodeBuilder {

    @Getter
    private int currentToken = 0;
    private Tokener reader;

    /**
     * Constructs a builder from a string and automatically {@link Tokener}'s it
     * @param string String to {@link Tokener}
     */
    public NodeBuilder(String string) {
        this(new Tokener(string));
    }

    /**
     * Constructs a builder from an already parsed {@link Tokener}
     * @param reader Reader storing {@link Token}'s
     */
    public NodeBuilder(Tokener reader) {
        this.reader = reader;
    }

    public RootNode build() throws NodeException {
        List<Node> children = new ArrayList<>();
        List<Node> allCommands = new ArrayList<>();
        currentToken = 0;
        while (currentToken < reader.size()) {
            Token token = reader.get(currentToken);
            if (token.tokenType == Token.TokenType.END_LINE) {
                allCommands.add(new RootNode(children));
                children = new ArrayList<>();
                currentToken++;
                continue;
            }
            Builder builder = switch (token.tokenType) {
                case KEYWORD -> new KeywordBuilder();
                case IDENTIFIER -> new IdentifierBuilder();
                case LITERAL -> new LiteralBuilder();
                case INT, DOUBLE -> new NumberBuilder();
                case PAREN_OPEN -> new ParenBuilder();
                default -> null;
            };
            if (builder == null) {
                // Only create a builder from the start of specific nodes
                currentToken++;
                continue;
            }
            Optional<Node> node = builder.build(reader, currentToken);
            currentToken = builder.getNextToken();
            if (node.isPresent()) {
                while (currentToken < reader.size() && Token.OPERATOR.contains(reader.get(currentToken).tokenType)) {
                    MathBuilder math = new MathBuilder(node.get());
                    Optional<Node> newNode = math.build(reader, currentToken);
                    currentToken = math.getNextToken();
                    if (newNode.isPresent()) {
                        node = newNode;
                    } else {
                        break;
                    }
                }
            }
            node.ifPresent(children::add);
        }
        return new RootNode(allCommands, children);
    }

    public static int subToEnd(Tokener tokener, int index) {
        int currentBlocks = 0;
        int currentParens = 0;
        for (int i = index; i < tokener.size(); i++) {
            Token.TokenType type = tokener.get(i).tokenType;
            switch (type) {
                case PAREN_OPEN -> currentParens++;
                case PAREN_CLOSE -> currentParens--;
                case BLOCK_START -> currentBlocks++;
                case BLOCK_END -> currentBlocks--;
            }
            if (currentParens == 0 && currentBlocks == 0 && type == Token.TokenType.END_LINE) {
                return i;
            }
        }
        return index;
    }

}
