package io.github.darkkronicle.Konstruct.reader;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Represents information about what characters or strings should become tokens.
 *
 * <p>Each part can be more than one character in length, but ones that start with others should be avoided.</p>
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenSettings {

    /**
     * Represents the default token settings
     * <p>
     * Variables: {}
     * Functions: []
     * Arguments: ()
     * Escape: \
     * </p>
     */
    public final static TokenSettings DEFAULT = TokenSettings.builder().build();

    @Builder.Default
    public final String functionStart = "[";
    @Builder.Default
    public final String functionEnd = "]";

    @Builder.Default
    public final String argsStart = "(";
    @Builder.Default
    public final String argsEnd = ")";
    @Builder.Default
    public final String argsDelim = ",";

    @Builder.Default
    public final String variableStart = "{";
    @Builder.Default
    public final String variableEnd = "}";

    @Builder.Default
    public final String escape = "\\";
    @Builder.Default
    public final String assignment = "=";
    @Builder.Default
    public final String endLine = ";";

    /**
     * Functions like a quotation mark and makes text within two of these count as literal. (Unless escaped out of)
     */
    @Builder.Default
    public final String forceLiteral = "'";

    /**
     * Functions like a quotation mark, but nothing inside can be escaped
     */
    @Builder.Default
    public final String strongLiteral = "'''";

    /**
     * The maximum length of any token string
     */
    @Getter
    private int maxLength;

    private int getMax() {
        String[] strings = new String[]{functionStart, functionEnd, argsStart, argsEnd, argsDelim, variableStart, variableEnd, escape, forceLiteral, strongLiteral, assignment, endLine};
        int max = 0;
        for (String s : strings) {
            max = Math.max(max, s.length());
        }
        return max;
    }

    public static SettingsBuilder builder() {
        return new SettingsBuilder();
    }

    public static class SettingsBuilder extends TokenSettingsBuilder {

        SettingsBuilder() {
            super();
        }

        @Override
        public TokenSettings build() {
            TokenSettings settings = super.build();
            settings.maxLength = settings.getMax();
            return settings;
        }
    }
}
