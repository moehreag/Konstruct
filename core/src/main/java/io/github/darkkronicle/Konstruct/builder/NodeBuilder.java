package io.github.darkkronicle.Konstruct.builder;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.RootNode;
import io.github.darkkronicle.Konstruct.reader.StringReader;
import io.github.darkkronicle.Konstruct.reader.Token;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class NodeBuilder implements Builder {

    @Getter
    private int cursor = 0;
    private StringReader reader;

    public NodeBuilder(String string) {
        this(StringReader.parse(string));
    }

    public NodeBuilder(StringReader reader) {
        this.reader = reader;
    }

    @Override
    public RootNode build() {
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
                cursor++;
                continue;
            }
            Node node = builder.build();
            if (node == null) {
                throw new NodeException("Invalid node!");
            }
            children.add(node);
            cursor += builder.getCursor();
        }
        return new RootNode(children);
    }

}
