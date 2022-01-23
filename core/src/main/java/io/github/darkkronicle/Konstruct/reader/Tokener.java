package io.github.darkkronicle.Konstruct.reader;

import io.github.darkkronicle.Konstruct.NodeException;

import java.util.*;

public class Tokener {

    private int cursor = 0;
    private final String original;
    private List<Token> tokens = new ArrayList<>();
    private char currentCharacter;
    private boolean done;
    private int line;
    private int column;

    private final static Set<Character> DIGITS = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    private final static Set<Character> WORD_CHARACTERS = Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_');

    public Tokener(String string) {
        this.original = string;
        make();
        checkIntegrity();
    }

    public Tokener(List<Token> tokens) {
        original = "";
        this.tokens = tokens;
    }

    public Token get(int index) {
        return tokens.get(index);
    }

    public int size() {
        return tokens.size();
    }

    public Tokener split(int start, int end) {
        return new Tokener(new ArrayList<>(tokens.subList(start, end)));
    }

    private void checkIntegrity() {
        Map<Token.TokenType, Integer> count = new HashMap<>();
        for (Token.TokenType type : Token.TokenType.values()) {
            count.put(type, 0);
        }
        Token.TokenType lastToken = null;
        for (Token token : tokens) {
            Integer current = count.get(token.tokenType);
            count.put(token.tokenType, current + 1);
            if (lastToken != null) {
                if (Token.OPERATOR.contains(lastToken) && Token.OPERATOR.contains(token.tokenType)) {
                    throwException("You cannot have two operators next to each other!");
                    return;
                }
                if (Token.CONDITIONAL.contains(lastToken) && Token.CONDITIONAL.contains(token.tokenType)) {
                    throwException("You cannot have two conditionals next to each other!");
                    return;
                }
                if (lastToken == token.tokenType) {
                    switch (lastToken) {
                        case DOUBLE, INT, IDENTIFIER, BANG -> {
                            throwException("You cannot have two " + lastToken.name() + " next to each other!");
                            return;
                        }
                    }
                }
            }
            lastToken = token.tokenType;
        }
        if (!count.get(Token.TokenType.BLOCK_START).equals(count.get(Token.TokenType.BLOCK_END))) {
            throwException("Unmatched block start!");
        }
        if (!count.get(Token.TokenType.PAREN_OPEN).equals(count.get(Token.TokenType.PAREN_CLOSE))) {
            throwException("Unmatched parenthesis!");
        }
    }

    private Optional<Character> getCharacter() {
        return getCharacter(cursor);
    }

    private Optional<Character> getCharacter(int cursor) {
        if (cursor >= original.length()) {
            return Optional.empty();
        }
        return Optional.of(original.charAt(cursor));
    }

    private void make() {
        while (cursor < original.length()) {
            currentCharacter = getCharacter().get();
            switch (currentCharacter) {
                case '+' -> addPlus();
                case '-' -> addMinus();
                case '*' -> addMultiply();
                case '%' -> addModulo();
                case '/' -> addDivide();
                case '(' -> addOpenParen();
                case ')' -> addCloseParen();
                case '!' -> addBang();
                case '\'' -> makeString('\'');
                case '"' -> makeString('"');
                case '>' -> addGreater();
                case '<' -> addLess();
                case ',' -> addDelim();
                case '=' -> addAssignment();
                case '{' -> addBlockStart();
                case '}' -> addBlockEnd();
                case ';' -> addEndLine();
                case '#' -> goThroughComment();
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> addNumber();
                case ' ', '\t', '\n' -> cursor++;
                default -> {
                    if (!WORD_CHARACTERS.contains(currentCharacter)) {
                        throwException("Invalid character!");
                        return;
                    }
                    addKeyword();
                }
            }
            updateLine(currentCharacter);
        }
        setDone();
    }

    private void addModulo() {
        cursor++;
        add(new Token(Token.TokenType.MODULO, " "));
    }

    private void goThroughComment() {
        cursor++;
        if (getCharacter().isPresent() && getCharacter().get() == '|') {
            cursor++;
            goThroughMultiLineComment();
            return;
        }
        while (cursor < original.length()) {
            char character = getCharacter().get();
            if (character == '\n') {
                return;
            }
            cursor++;
        }
    }

    private void goThroughMultiLineComment() {
        while (cursor < original.length() - 1) {
            if (original.startsWith("|#", cursor)) {
                cursor += 2;
                return;
            }
            cursor++;
        }
        throwException("Multiline comment never closed!");
    }

    private void addKeyword() {
        StringBuilder string = new StringBuilder();
        while (cursor < original.length()) {
            if (!WORD_CHARACTERS.contains(getCharacter().get())) {
                break;
            }
            string.append(getCharacter().get());
            cursor++;
            getCharacter().ifPresent(this::updateLine);
        }
        String built = string.toString();
        if (Token.KEYWORDS.contains(built)) {
            add(new Token(Token.TokenType.KEYWORD, built));
        } else {
            add(new Token(Token.TokenType.IDENTIFIER, built));
        }
    }

    private void makeStrongString(char starter) {
        StringBuilder builder = new StringBuilder().append(getCharacter().get());
        cursor++;
        Optional<Character> c = getCharacter();
        if (c.isEmpty()) {
            throwException("Strong string wasn't closed!");
            return;
        }
        String exit = String.valueOf(starter).repeat(3);
        updateLine(c.get());
        while (cursor < original.length()) {
            if (cursor > original.length() - 3) {
                throwException("Strong string wasn't closed!");
                return;
            }
            if (original.substring(cursor, cursor + 3).equals(exit)) {
                updateLine(getCharacter(cursor + 1).get());
                updateLine(getCharacter(cursor + 2).get());
                cursor += 3;
                add(new Token(Token.TokenType.LITERAL, builder.toString()));
                return;
            }
            builder.append(getCharacter().get());
            cursor++;
            updateLine(getCharacter().get());
        }
        throwException("Strong string wasn't closed!");
    }

    private void makeString(char starter) {
        if (getCharacter(cursor + 1).map(character -> character.equals(starter)).orElse(false)) {
            updateLine(getCharacter(cursor + 1).get());
            if (getCharacter(cursor + 2).map(character -> character.equals(starter)).orElse(false)) {
                cursor += 2;
                updateLine(getCharacter().get());
                cursor++;
                makeStrongString(starter);
                return;
            }
        }
        StringBuilder literal = new StringBuilder();
        cursor++;
        while (cursor < original.length()) {
            updateLine(getCharacter().get());
            if (getCharacter().get() == starter) {
                cursor++;
                add(new Token(Token.TokenType.LITERAL, literal.toString()));
                return;
            }
            if (getCharacter().get() == '\\') {
                cursor++;
                getCharacter().ifPresent(character -> {
                    updateLine(character);
                    literal.append(character);
                });
                continue;
            }
            literal.append(getCharacter().get());
            cursor++;
        }
        throwException("String wasn't closed!");
    }

    private void addNumber() {
        StringBuilder digits = new StringBuilder();
        boolean isDouble = false;
        digits.append(currentCharacter);
        cursor++;
        while (cursor < original.length()) {
            char character = getCharacter().get();
            updateLine(character);
            if (character == '.') {
                if (isDouble) {
                    throwException("Double can't have multiple dots!");
                    return;
                }
                isDouble = true;
            } else if (!DIGITS.contains(character)) {
                break;
            }
            digits.append(character);
            cursor++;
        }
        add(new Token(
                isDouble ? Token.TokenType.DOUBLE : Token.TokenType.INT,
                digits.toString()
        ));
    }

    private void addPlus() {
        cursor++;
        add(new Token(Token.TokenType.PLUS, " "));
    }

    private void addMinus() {
        cursor++;
        add(new Token(Token.TokenType.MINUS, " "));
    }

    private void addMultiply() {
        cursor++;
        add(new Token(Token.TokenType.MULTIPLY, " "));
    }

    private void addOpenParen() {
        cursor++;
        add(new Token(Token.TokenType.PAREN_OPEN, " "));
    }

    private void addCloseParen() {
        cursor++;
        add(new Token(Token.TokenType.PAREN_CLOSE, " "));
    }

    private void addAssignment() {
        cursor++;
        if (getCharacter().isPresent() && getCharacter().get() == '=') {
            cursor++;
            add(new Token(Token.TokenType.EQUAL, " "));
        } else {
            add(new Token(Token.TokenType.ASSIGNMENT, " "));
        }
    }

    private void addBang() {
        cursor++;
        if (getCharacter().isPresent() && getCharacter().get() == '=') {
            cursor++;
            add(new Token(Token.TokenType.NOT_EQUAL, " "));
        } else {
            add(new Token(Token.TokenType.BANG, " "));
        }
    }

    private void addDelim() {
        cursor++;
        add(new Token(Token.TokenType.ARGUMENTS_DELIMINATOR, " "));
    }

    private void addBlockStart() {
        cursor++;
        add(new Token(Token.TokenType.BLOCK_START, " "));
    }

    private void addBlockEnd() {
        cursor++;
        add(new Token(Token.TokenType.BLOCK_END, " "));
    }

    private void addEndLine() {
        cursor++;
        add(new Token(Token.TokenType.END_LINE, " "));
    }

    private void addDivide() {
        cursor++;
        if (getCharacter().isPresent() && getCharacter().get() == '/') {
            cursor++;
            add(new Token(Token.TokenType.INT_DIVIDE, " "));
        } else {
            add(new Token(Token.TokenType.DIVIDE, " "));
        }
    }

    private void addGreater() {
        cursor++;
        if (getCharacter().isPresent() && getCharacter().get() == '=') {
            updateLine(getCharacter().get());
            cursor++;
            add(new Token(Token.TokenType.GREATER_EQUAL, " "));
        } else {
            add(new Token(Token.TokenType.GREATER, " "));
        }
    }

    private void addLess() {
        cursor++;
        if (getCharacter().isPresent() && getCharacter().get() == '=') {
            cursor++;
            add(new Token(Token.TokenType.LESS_EQUAL, " "));
        } else {
            add(new Token(Token.TokenType.LESS, " "));
        }
    }

    private void add(Token token) {
        if (done) {
            throw new NodeException("Tokenizer has already stopped!");
        }
        tokens.add(token);
    }

    private void updateLine(char character) {
        if (character == '\n') {
            line++;
            column = 0;
        } else {
            column++;
        }
    }

    private void throwException(String message) {
        throw new NodeFormatException(line, column, cursor, original, message);
    }

    private void setDone() {
        this.done = true;
    }

    public Token[] getTokens() {
        return tokens.toArray(new Token[0]);
    }
}
