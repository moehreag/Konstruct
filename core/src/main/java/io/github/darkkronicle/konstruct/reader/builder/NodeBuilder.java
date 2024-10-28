package io.github.darkkronicle.konstruct.reader.builder;

import io.github.darkkronicle.konstruct.NodeException;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.nodes.RootNode;
import io.github.darkkronicle.konstruct.reader.Token;
import io.github.darkkronicle.konstruct.reader.Tokener;
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
    @Getter
    private int scope;

    /**
     * Constructs a builder from a string and automatically {@link Tokener}'s it
     * @param string String to {@link Tokener}
     */
    public NodeBuilder(String string) {
        this(new Tokener(string), 0);
    }

    public NodeBuilder(String string, int scope) {
        this(new Tokener(string), scope);
    }

    /**
     * Constructs a builder from an already parsed {@link Tokener}
     * @param reader Reader storing {@link Token}'s
     */
    public NodeBuilder(Tokener reader, int scope) {
        this.scope = scope;
        this.reader = reader;
    }

    public RootNode build() throws NodeException {
        List<Node> children = new ArrayList<>();
        List<Node> allCommands = new ArrayList<>();
        currentToken = 0;
        while (currentToken < reader.size()) {
            Token token = reader.get(currentToken);
            if (token.tokenType == Token.TokenType.END_LINE) {
                allCommands.add(new RootNode(scope, children));
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
            Optional<Node> node = builder.build(scope, reader, currentToken);
            currentToken = builder.getNextToken();
            if (node.isPresent()) {
                // Dot
                while (currentToken < reader.size() && reader.get(currentToken).tokenType == Token.TokenType.DOT) {
                    DotBuilder dot = new DotBuilder(node.get());
                    Optional<Node> newNode = dot.build(scope, reader, currentToken);
                    currentToken = dot.getNextToken();
                    if (newNode.isPresent()) {
                        node = newNode;
                    } else {
                        break;
                    }
                }
                // Do operators first
                while (currentToken < reader.size() && Token.OPERATOR.contains(reader.get(currentToken).tokenType)) {
                    MathBuilder math = new MathBuilder(node.get());
                    Optional<Node> newNode = math.build(scope, reader, currentToken);
                    currentToken = math.getNextToken();
                    if (newNode.isPresent()) {
                        node = newNode;
                    } else {
                        break;
                    }
                }
                // Conditions
                while (currentToken < reader.size() && Token.CONDITIONAL.contains(reader.get(currentToken).tokenType)) {
                    ConditionalBuilder condition = new ConditionalBuilder(node.get());
                    Optional<Node> newNode = condition.build(scope, reader, currentToken);
                    currentToken = condition.getNextToken();
                    if (newNode.isPresent()) {
                        node = newNode;
                    } else {
                        break;
                    }
                }
                // Gates!
                while (currentToken < reader.size() && Token.GATES.containsKey(reader.get(currentToken).content) && reader.get(currentToken).tokenType == Token.TokenType.KEYWORD) {
                    GateBuilder condition = new GateBuilder(node.get());
                    Optional<Node> newNode = condition.build(scope, reader, currentToken);
                    currentToken = condition.getNextToken();
                    if (newNode.isPresent()) {
                        node = newNode;
                    } else {
                        break;
                    }
                }
            }
            node.ifPresent(children::add);
        }
        return new RootNode(scope, allCommands, children);
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
        return tokener.size();
    }

}
