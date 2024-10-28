package io.github.darkkronicle.konstruct.reader.builder;

import io.github.darkkronicle.konstruct.NodeException;
import io.github.darkkronicle.konstruct.nodes.Node;
import io.github.darkkronicle.konstruct.nodes.OperatorNode;
import io.github.darkkronicle.konstruct.reader.Token;
import io.github.darkkronicle.konstruct.reader.Tokener;
import io.github.darkkronicle.konstruct.type.KonstructObject;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.function.BiFunction;

public class ConditionalBuilder implements Builder {

    @AllArgsConstructor
    public enum Conditional {
        EQUAL((first, second) -> first.equal(second)),
        NOT_EQUAL((first, second) -> first.notEqual(second)),
        GREATER_THAN((first, second) -> first.greaterThan(second)),
        LESS_THAN((first, second) -> first.lessThan(second)),
        GREATER_THAN_EQUAL((first, second) -> first.greaterThanEqual(second)),
        LESS_THAN_EQUAL((first, second) -> first.lessThanEqual(second)),
        ;

        private final BiFunction<KonstructObject<?>, KonstructObject<?>, KonstructObject<?>> evaluate;

        public static Conditional fromToken(Token.TokenType type) {
            return switch (type) {
                case NOT_EQUAL -> NOT_EQUAL;
                case GREATER -> GREATER_THAN;
                case GREATER_EQUAL -> GREATER_THAN_EQUAL;
                case LESS -> LESS_THAN;
                case LESS_EQUAL -> LESS_THAN_EQUAL;
                default -> EQUAL;
            };
        }
    }

    private final Node starting;
    private int nextToken;

    public ConditionalBuilder(Node original) {
        this.starting = original;
    }

    @Override
    public Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException {
        Conditional condition = Conditional.fromToken(reader.get(currentToken).tokenType);
        nextToken = currentToken + 1;
        if (nextToken >= reader.size()) {
            throw new NodeException("Condition " + condition.name() + " is unmatched!");
        }
        int next = toNextConditional(reader, nextToken);
        Node inside = new NodeBuilder(reader.split(nextToken, next), scope).build();
        if (inside.getChildren().size() == 1) {
            inside = inside.getChildren().get(0);
        }
        nextToken = next;
        return Optional.of(new OperatorNode(starting, inside, condition.evaluate));
    }

    @Override
    public int getNextToken() {
        return nextToken;
    }

    public static int toNextConditional(Tokener tokener, int index) {
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
            if (currentParens == 0 && currentBlocks == 0 && (type == Token.TokenType.END_LINE || (
                    type == Token.TokenType.GREATER || type == Token.TokenType.GREATER_EQUAL
                    || type == Token.TokenType.LESS || type == Token.TokenType.LESS_EQUAL
                    || type == Token.TokenType.EQUAL || type == Token.TokenType.NOT_EQUAL
            ))) {
                return i;
            }
        }
        return tokener.size();
    }
}
