package io.github.darkkronicle.Konstruct.reader;

import lombok.AllArgsConstructor;
import lombok.Value;

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
    }

    @Override
    public String toString() {
        if (tokenType == TokenType.LITERAL) {
            return String.valueOf(c);
        }
        return tokenType.toString();
    }

    /**
     * Represents information about what characters or strings should become tokens.
     *
     * <p>Each part can be more than one character in length, but ones that start with others should be avoided.</p>
     */
    @Value
    public static class TokenSettings {

        /**
         * Represents the default token settings
         * <p>
         * Variables: {}
         * Functions: []
         * Arguments: ()
         * Escape: \
         * </p>
         */
        public final static TokenSettings DEFAULT = new TokenSettings("[", "]", "(", ")", ",", "{", "}", "\\", "'", "'''");

        public String functionStart;
        public String functionEnd;

        public String argsStart;
        public String argsEnd;
        public String argsDelim;

        public String variableStart;
        public String variableEnd;

        public String escape;

        /**
         * Functions like a quotation mark and makes text within two of these count as literal. (Unless escaped out of)
         */
        public String forceLiteral;

        /**
         * Functions like a quotation mark, but nothing inside can be escaped
         */
        public String strongLiteral;

        /**
         * The maximum length of any token string
         */
        public int maxLength;

        public TokenSettings(String functionStart, String functionEnd, String argsStart, String argsEnd, String argsDelim, String variableStart, String variableEnd, String escape, String forceLiteral, String strongLiteral) {
            this.functionStart = functionStart;
            this.functionEnd = functionEnd;
            this.argsStart = argsStart;
            this.argsEnd = argsEnd;
            this.argsDelim = argsDelim;
            this.variableStart = variableStart;
            this.variableEnd = variableEnd;
            this.escape = escape;
            this.forceLiteral = forceLiteral;
            this.strongLiteral = strongLiteral;
            this.maxLength = getMax();
        }

        private int getMax() {
            String[] strings = new String[]{functionStart, functionEnd, argsStart, argsEnd, argsDelim, variableStart, variableEnd, escape, forceLiteral, strongLiteral};
            int max = 0;
            for (String s : strings) {
                max = Math.max(max, s.length());
            }
            return max;
        }
    }

}
