package io.github.darkkronicle.konstruct.reader;

import io.github.darkkronicle.konstruct.Gate;
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

        /** A literal string */
        LITERAL,

        /** Start of a code block */
        BLOCK_START,

        /** End of a code block */
        BLOCK_END,

        /** The separator of arguments within a function */
        ARGUMENTS_DELIMINATOR,

        /** Assigns a value to a variable */
        ASSIGNMENT,

        /** Equivalent to a ; in java */
        END_LINE,

        /** Plus operator */
        PLUS,

        /** Minus operator */
        MINUS,

        /** Multiply operator */
        MULTIPLY,

        /** Divide operator */
        DIVIDE,

        /** Divide with floor */
        INT_DIVIDE,

        /** Module operator */
        MODULO,

        /** Open parenthesis */
        PAREN_OPEN,

        /** Close parenthesis */
        PAREN_CLOSE,

        /** Keyword. Reserved words like and, or, if, else, def... */
        KEYWORD,

        /** Any free word in the code that isn't a keyword. Variable names and function names */
        IDENTIFIER,

        /** Greater condition */
        GREATER,

        /** Less condition */
        LESS,

        /** Greater than or equal condition */
        GREATER_EQUAL,

        /** Less than or equal condition */
        LESS_EQUAL,

        /** Equal condition */
        EQUAL,

        /** Not equal condition */
        NOT_EQUAL,

        /** Bang operator to invert booleans */
        BANG,

        /** Double type */
        DOUBLE,

        /** Int type */
        INT,

        /** Dot operator */
        DOT
    }

    /** All keywords */
    public final static Set<String> KEYWORDS = Set.of("if", "else", "elif", "and", "or", "nand", "nor", "xor", "xnor", "true", "false", "def", "while");

    /** ALl logical gates */
    public final static Map<String, Gate> GATES = Map.of("and", Gate.AND, "or", Gate.OR, "nand", Gate.NAND, "nor", Gate.NOR, "xor", Gate.XOR, "xnor", Gate.XNOR);

    /** All mathmatical operators */
    public static final Set<TokenType> OPERATOR = Set.of(TokenType.PLUS, TokenType.MINUS, TokenType.DIVIDE, TokenType.INT_DIVIDE, TokenType.MULTIPLY, TokenType.MODULO);

    /** All conditions */
    public static final Set<TokenType> CONDITIONAL = Set.of(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL, TokenType.EQUAL, TokenType.NOT_EQUAL);

    @Override
    public String toString() {
        if (tokenType == TokenType.LITERAL || tokenType == TokenType.INT || tokenType == TokenType.DOUBLE || tokenType == TokenType.IDENTIFIER || tokenType == TokenType.KEYWORD) {
            return tokenType + " - " + content;
        }
        return tokenType.toString();
    }

}
