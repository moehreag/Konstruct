package io.github.darkkronicle.Konstruct.reader;

import io.github.darkkronicle.Konstruct.NodeException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StringReader {

    @Getter
    private final String input;
    @Getter
    private final Token.TokenSettings settings;
    @Getter
    private final List<Token> tokens;

    public StringReader(String input, Token.TokenSettings settings, List<Token> tokens) {
        this.input = input;
        this.settings = settings;
        this.tokens = tokens;
    }

    public StringReader substring(int begin) {
        return substring(begin, tokens.size());
    }

    public StringReader substring(int begin, int end) {
        return new StringReader(input, settings, tokens.subList(begin, end));
    }

    public int length() {
        return tokens.size();
    }

    public Token get(int index) {
        return tokens.get(index);
    }

    public static StringReader parse(String input) {
        return parse(input, Token.TokenSettings.DEFAULT);
    }

    public static StringReader parse(String input, Token.TokenSettings settings) {
        List<Token> tokens = new ArrayList<>();
        int cursor = 0;
        int currentFunction = 0;
        int currentArguments = 0;
        int currentVariable = 0;
        while (cursor < input.length()) {
            String c = input.substring(cursor, Math.min(input.length(), cursor + settings.getMaxLength()));
            if (c.startsWith(settings.escape)) {
                cursor += settings.escape.length();
                if (cursor >= input.length()) {
                    throw new NodeException("Invalid escape sequence at position " + cursor + "! " + input);
                }
                tokens.add(new Token(Token.TokenType.LITERAL, input.charAt(cursor)));
                cursor++;
                continue;
            }

            if (currentVariable > 0) {
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
                if (currentFunction == currentArguments) {
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
                } else if (currentFunction > currentArguments) {
                    if (c.startsWith(settings.argsStart)) {
                        tokens.add(new Token(Token.TokenType.ARGUMENTS_START, '0'));
                        cursor += settings.argsStart.length();
                        currentArguments++;
                    } else if (c.startsWith(settings.functionEnd)) {
                        tokens.add(new Token(Token.TokenType.FUNCTION_END, '0'));
                        cursor += settings.functionEnd.length();
                        currentFunction--;
                    }
                    else {
                        tokens.add(new Token(Token.TokenType.LITERAL, c.charAt(0)));
                        cursor++;
                    }
                    continue;

                } else {
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
        if (currentArguments > 0) {
            throw new NodeException("Not all arguments were complete!");
        }
        if (currentFunction > 0) {
            throw new NodeException("Not all functions were complete!");
        }
        if (currentVariable > 0) {
            throw new NodeException("Not all variables were complete!");
        }
        return new StringReader(input, settings, tokens);
    }

}
