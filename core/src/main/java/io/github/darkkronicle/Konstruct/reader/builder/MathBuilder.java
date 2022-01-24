package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.OperatorNode;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.reader.Token;
import io.github.darkkronicle.Konstruct.reader.Tokener;
import io.github.darkkronicle.Konstruct.type.IntegerObject;
import io.github.darkkronicle.Konstruct.type.KonstructObject;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class MathBuilder implements Builder {

    private final Node starting;
    private int nextToken;

    @AllArgsConstructor
    public enum Operator {
        PLUS((first, second) -> first.add(second)),
        SUBTRACT((first, second) -> first.subtract(second)),
        MULTIPLY((first, second) -> first.multiply(second)),
        DIVIDE((first, second) -> first.divide(second)),
        INT_DIVIDE((first, second) -> first.intDivide(second)),
        MODULO((first, second) -> first.modulo(second)),
        ;

        private final BiFunction<KonstructObject, KonstructObject, KonstructObject> evaluate;

        public static Operator fromToken(Token.TokenType type) {
            return switch (type) {
                case MINUS -> Operator.SUBTRACT;
                case MULTIPLY -> Operator.MULTIPLY;
                case DIVIDE -> Operator.DIVIDE;
                case INT_DIVIDE -> Operator.INT_DIVIDE;
                case MODULO -> Operator.MODULO;
                default -> PLUS;
            };
        }
    }

    public MathBuilder(Node starting) {
        this.starting = starting;
    }

    @Override
    public Optional<Node> build(int scope, Tokener reader, int currentToken) throws NodeException {
        Operator intial = Operator.fromToken(reader.get(currentToken).tokenType);
        nextToken = currentToken + 1;
        if (nextToken >= reader.size()) {
            throw new NodeException("Operator " + intial.name() + " is unmatched!");
        }
        int next = toNextOperator(reader, nextToken);
        Node inside = new NodeBuilder(reader.split(nextToken, next), scope).build();
        if (inside.getChildren().size() == 1) {
            inside = inside.getChildren().get(0);
        }
        nextToken = next;
        if (nextToken >= reader.size() || !Token.OPERATOR.contains(reader.get(nextToken).tokenType)) {
            return Optional.of(new OperatorNode(starting, inside, intial.evaluate));
        }
        Token following = reader.get(nextToken);
        if (following.tokenType == Token.TokenType.MULTIPLY || following.tokenType == Token.TokenType.DIVIDE || following.tokenType == Token.TokenType.INT_DIVIDE) {
            Operator intial2 = Operator.fromToken(following.tokenType);
            next = toNextOperator(reader, nextToken + 1);
            Node inside2 = new NodeBuilder(reader.split(nextToken, next), scope).build();
            if (inside2.getChildren().size() == 1) {
                inside2 = inside2.getChildren().get(0);
            }
            nextToken = next;
            return Optional.of(new OperatorNode(starting, new OperatorNode(inside, inside2, intial2.evaluate), intial.evaluate));
        }
        return Optional.of(new OperatorNode(starting, inside, intial.evaluate));
    }

    @Override
    public int getNextToken() {
        return nextToken;
    }

    public static int toNextOperator(Tokener tokener, int index) {
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
            if (currentParens == 0 && currentBlocks == 0 && (type == Token.TokenType.END_LINE || Token.OPERATOR.contains(type))) {
                return i;
            }
        }
        return tokener.size();
    }
}
