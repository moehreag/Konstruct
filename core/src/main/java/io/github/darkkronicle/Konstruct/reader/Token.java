package io.github.darkkronicle.Konstruct.reader;

import lombok.*;

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
     * The character that is stored
     */
    public final char c;

    public enum TokenType {
        /**
         * A literal string
         */
        LITERAL,

        /**
         * The start of a function
         */
        FUNCTION_START,

        /**
         * The end of a function
         */
        FUNCTION_END,

        /**
         * The start of arguments within a function
         */
        ARGUMENTS_START,

        /**
         * The end of arguments within a function
         */
        ARGUMENTS_END,

        /**
         * The separator of arguments within a function
         */
        ARGUMENTS_DELIMINATOR,

        /**
         * The start of a variable
         */
        VARIABLE_START,

        /**
         * The end of a variable
         */
        VARIABLE_END,

        /**
         * Assigns a value to a variable
         */
        ASSIGNMENT,

        /**
         * Equivalent to a ; in java
         */
        END_LINE
    }

    @Override
    public String toString() {
        if (tokenType == TokenType.LITERAL) {
            return String.valueOf(c);
        }
        return tokenType.toString();
    }

}
