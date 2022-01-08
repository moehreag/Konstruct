package io.github.darkkronicle.Konstruct.reader;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
public class Token {

    public final TokenType tokenType;
    public final char c;

    public enum TokenType {
        LITERAL,
        FUNCTION_START,
        FUNCTION_END,
        ARGUMENTS_START,
        ARGUMENTS_END,
        ARGUMENTS_DELIMINATOR,
        VARIABLE_START,
        VARIABLE_END
    }

    @Override
    public String toString() {
        if (tokenType == TokenType.LITERAL) {
            return String.valueOf(c);
        }
        return tokenType.toString();
    }

    @Value
    public static class TokenSettings {

        public final static TokenSettings DEFAULT = new TokenSettings("[", "]", "(", ")", ",", "{", "}", "\\");

        public String functionStart;
        public String functionEnd;

        public String argsStart;
        public String argsEnd;
        public String argsDelim;

        public String variableStart;
        public String variableEnd;

        public String escape;

        public int maxLength;

        public TokenSettings(String functionStart, String functionEnd, String argsStart, String argsEnd, String argsDelim, String variableStart, String variableEnd, String escape) {
            this.functionStart = functionStart;
            this.functionEnd = functionEnd;
            this.argsStart = argsStart;
            this.argsEnd = argsEnd;
            this.argsDelim = argsDelim;
            this.variableStart = variableStart;
            this.variableEnd = variableEnd;
            this.escape = escape;
            this.maxLength = getMax();
        }

        private int getMax() {
            String[] strings = new String[]{functionStart, functionEnd, argsStart, argsEnd, argsDelim, variableStart, variableEnd, escape};
            int max = 0;
            for (String s : strings) {
                max = Math.max(max, s.length());
            }
            return max;
        }
    }

}
