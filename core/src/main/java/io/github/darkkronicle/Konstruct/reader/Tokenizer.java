package io.github.darkkronicle.Konstruct.reader;

import io.github.darkkronicle.Konstruct.NodeException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * A tokenizer to convert a string into {@link Token}'s
 */
public class Tokenizer {

    /**
     * The input string that was used to create this {@link Tokenizer}
     */
    @Getter
    private final String input;

    /**
     * The {@link io.github.darkkronicle.Konstruct.reader.Token.TokenSettings} that contain the information
     * to create the tokens
     */
    @Getter
    private final Token.TokenSettings settings;

    /**
     * The {@link List} of {@link Token}'s created
     */
    @Getter
    private final List<Token> tokens;

    /**
     * Creates a {@link Tokenizer} from an already parsed input. To parse an input check {@link Tokenizer#parse(String)}
     * @param input Input used to create the tokens
     * @param settings Settings used to create the tokens
     * @param tokens {@link List} of all the {@link Token}'s
     */
    public Tokenizer(String input, Token.TokenSettings settings, List<Token> tokens) {
        this.input = input;
        this.settings = settings;
        this.tokens = tokens;
    }

    /**
     * Substrings this {@link Tokenizer} and constructs a new one that includes all tokens after begin.
     * @param begin Begin index (inclusive)
     * @return New {@link Tokenizer}
     */
    public Tokenizer substring(int begin) {
        return substring(begin, tokens.size());
    }

    /**
     * Substrings this {@link Tokenizer} and constructs a new one in the given range.
     * @param begin Begin index (inclusive)
     * @param end End index (exclusive)
     * @return New {@link Tokenizer}
     */
    public Tokenizer substring(int begin, int end) {
        return new Tokenizer(input, settings, tokens.subList(begin, end));
    }

    /**
     * Returns the length of the token list
     * @return Length of the list
     */
    public int length() {
        return tokens.size();
    }

    /**
     * Returns a {@link Token} at a given index
     * @param index Index of the {@link Token}
     * @return Found {@link Token}
     * @throws IndexOutOfBoundsException if out of range
     */
    public Token get(int index) {
        return tokens.get(index);
    }

    /**
     * Parses a string and returns a fully processed {@link Tokenizer} with default token settings
     * @param input Input to tokenize
     * @return Constructed {@link Tokenizer}
     */
    public static Tokenizer parse(String input) {
        return parse(input, Token.TokenSettings.DEFAULT);
    }

    /**
     * Parses a string and returns a fully processed {@link Tokenizer} with the defined {@link io.github.darkkronicle.Konstruct.reader.Token.TokenSettings}
     * @param input Input to tokenize
     * @param settings Settings to define tokens
     * @return Constructed {@link Tokenizer}
     */
    public static Tokenizer parse(String input, Token.TokenSettings settings) {
        List<Token> tokens = new ArrayList<>();

        // Setup information about current position within the input
        int cursor = 0;
        int currentFunction = 0;
        int currentArguments = 0;
        int currentVariable = 0;

        while (cursor < input.length()) {
            String c = input.substring(cursor, Math.min(input.length(), cursor + settings.getMaxLength()));

            if (c.startsWith(settings.escape)) {
                // To use an escape sequence we just put in the next character exactly as is
                cursor += settings.escape.length();
                if (cursor >= input.length()) {
                    throw new NodeException("Invalid escape sequence at position " + cursor + "! " + input);
                }
                tokens.add(new Token(Token.TokenType.LITERAL, input.charAt(cursor)));
                cursor++;
                continue;
            }

            if (currentVariable > 0) {
                // If we are within a variable we keep on adding until it ends
                if (c.startsWith(settings.variableEnd)) {
                    currentVariable--;
                    tokens.add(new Token(Token.TokenType.VARIABLE_END, '0'));
                    cursor += settings.variableEnd.length();
                } else {
                    tokens.add(new Token(Token.TokenType.LITERAL, c.charAt(0)));
                    cursor++;
                }
                continue;
            }

            if (currentFunction > 0) {
                // If we are in a function we need to mark arguments
                if (currentFunction == currentArguments) {
                    // We are currently within the arguments for the function
                    // Should only be looking for a deliminator or end
                    if (c.startsWith(settings.argsEnd)) {
                        tokens.add(new Token(Token.TokenType.ARGUMENTS_END, '0'));
                        cursor += settings.argsEnd.length();
                        currentArguments--;
                        continue;
                    } else if (c.startsWith(settings.argsDelim)) {
                        tokens.add(new Token(Token.TokenType.ARGUMENTS_DELIMINATOR, '0'));
                        cursor += settings.argsDelim.length();
                        continue;
                    }
                    // We allow it to go on in case it now starts a new function
                } else if (currentFunction > currentArguments) {
                    // Currently, within the function name, but not arguments (or after)
                    if (c.startsWith(settings.argsStart)) {
                        tokens.add(new Token(Token.TokenType.ARGUMENTS_START, '0'));
                        cursor += settings.argsStart.length();
                        currentArguments++;
                    } else if (c.startsWith(settings.functionEnd)) {
                        tokens.add(new Token(Token.TokenType.FUNCTION_END, '0'));
                        cursor += settings.functionEnd.length();
                        currentFunction--;
                    } else {
                        // Isn't anything special and should just be a literal
                        tokens.add(new Token(Token.TokenType.LITERAL, c.charAt(0)));
                        cursor++;
                    }
                    continue;
                } else {
                    // There should never be more argument starts than there are functions
                    throw new NodeException("More variable segments than functions at position " + cursor + "! " + input);
                }
            }

            if (c.startsWith(settings.functionStart)) {
                tokens.add(new Token(Token.TokenType.FUNCTION_START, '0'));
                cursor += settings.functionStart.length();
                currentFunction++;
                continue;
            } else if (c.startsWith(settings.variableStart)) {
                currentVariable++;
                tokens.add(new Token(Token.TokenType.VARIABLE_START, '0'));
                cursor += settings.variableStart.length();
                continue;
            }
            tokens.add(new Token(Token.TokenType.LITERAL, c.charAt(0)));
            cursor++;
        }

        // Checks to make sure everything is balanced
        if (currentArguments > 0) {
            throw new NodeException("Not all arguments were complete!");
        }
        if (currentFunction > 0) {
            throw new NodeException("Not all functions were complete!");
        }
        if (currentVariable > 0) {
            throw new NodeException("Not all variables were complete!");
        }
        return new Tokenizer(input, settings, tokens);
    }

}
