package io.github.darkkronicle.Konstruct.reader;

import io.github.darkkronicle.Konstruct.Gate;
import lombok.*;

import java.util.Map;
import java.util.Set;

/**
 * A class to store information about a parsed string. This stores a character and the {@link TokenType}
 */
@AllArgsConstructor
public class Token {

    /**
     * The type of token
     */
    public final TokenType tokenType;

    /**
     * The string that is stored
     */
    public final String content;

    public enum TokenType {

        /**
         * A literal string
         */
        LITERAL,

        BLOCK_START,
        BLOCK_END,

        /**
         * The separator of arguments within a function
         */
        ARGUMENTS_DELIMINATOR,


        /**
         * Assigns a value to a variable
         */
        ASSIGNMENT,

        /**
         * Equivalent to a ; in java
         */
        END_LINE,

        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        INT_DIVIDE,
        MODULO,
        PAREN_OPEN,
        PAREN_CLOSE,
        KEYWORD,
        IDENTIFIER,
        GREATER,
        LESS,
        GREATER_EQUAL,
        LESS_EQUAL,
        BANG,
        EQUAL,
        NOT_EQUAL,
        DOUBLE,
        INT
    }

    public final static Set<String> KEYWORDS = Set.of("if", "else", "and", "or", "nand", "nor", "xor", "xnor", "true", "false");

    public final static Map<String, Gate> GATES = Map.of("and", Gate.AND, "or", Gate.OR, "nand", Gate.NAND, "nor", Gate.NOR, "xor", Gate.XOR, "xnor", Gate.XNOR);

    public static final Set<TokenType> OPERATOR = Set.of(TokenType.PLUS, TokenType.MINUS, TokenType.DIVIDE, TokenType.INT_DIVIDE, TokenType.MULTIPLY, TokenType.MODULO);

    public static final Set<TokenType> CONDITIONAL = Set.of(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL, TokenType.EQUAL, TokenType.NOT_EQUAL);

    @Override
    public String toString() {
        if (tokenType == TokenType.LITERAL || tokenType == TokenType.INT || tokenType == TokenType.DOUBLE || tokenType == TokenType.IDENTIFIER || tokenType == TokenType.KEYWORD) {
            return tokenType + " - " + content;
        }
        return tokenType.toString();
    }

}
