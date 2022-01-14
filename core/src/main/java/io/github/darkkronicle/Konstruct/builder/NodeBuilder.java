package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.RootNode;
import io.github.darkkronicle.Konstruct.reader.Tokenizer;
import io.github.darkkronicle.Konstruct.reader.Token;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class to build a {@link RootNode}
 *
 * <p>This should generally be used for any amount of node processing</p>
 */
public class NodeBuilder {

    @Getter
    private int cursor = 0;
    private Tokenizer reader;

    /**
     * Constructs a builder from a string and automatically {@link Tokenizer}'s it
     * @param string String to {@link Tokenizer}
     */
    public NodeBuilder(String string) {
        this(Tokenizer.parse(string));
    }

    /**
     * Constructs a builder from a string and automatically {@link Tokenizer}'s it with specified {@link io.github.darkkronicle.Konstruct.reader.Token.TokenSettings}
     * @param string String to {@link Tokenizer}
     */
    public NodeBuilder(String string, Token.TokenSettings settings) {
        this(Tokenizer.parse(string, settings));
    }

    /**
     * Constructs a builder from an already parsed {@link Tokenizer}
     * @param reader Reader storing {@link Token}'s
     */
    public NodeBuilder(Tokenizer reader) {
        this.reader = reader;
    }

    public RootNode build() throws NodeException {
        List<Node> children = new ArrayList<>();
        cursor = 0;
        while (cursor < reader.getTokens().size()) {
            Token token = reader.getTokens().get(cursor);
            Builder builder = switch (token.tokenType) {
                case VARIABLE_START -> new VariableBuilder(reader.substring(cursor));
                case FUNCTION_START -> new FunctionBuilder(reader.substring(cursor));
                case LITERAL -> new LiteralBuilder(reader.substring(cursor));
                default -> null;
            };
            if (builder == null) {
                // Only create a builder from the start of specific nodes
                cursor++;
                continue;
            }
            Optional<Node> node = builder.build();
            node.ifPresent(children::add);
            cursor += builder.getCursor();
        }
        return new RootNode(children);
    }

}
