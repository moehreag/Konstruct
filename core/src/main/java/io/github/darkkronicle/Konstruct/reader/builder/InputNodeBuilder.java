package io.github.darkkronicle.Konstruct.reader.builder;

import io.github.darkkronicle.Konstruct.nodes.InputNode;
import io.github.darkkronicle.Konstruct.nodes.LiteralNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.RootNode;
import io.github.darkkronicle.Konstruct.reader.Tokener;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputNodeBuilder {

    @Value
    @AllArgsConstructor
    private static class Input {

        String string;
        boolean isKonstruct;

    }

    private List<Input> inputs = new ArrayList<>();

    /**
     * Constructs a builder from a string and automatically {@link Tokener}'s it
     * @param string String to {@link Tokener}
     */
    public InputNodeBuilder(String string) {
        Pattern pattern = Pattern.compile("\\[\\[(.*?)\\]\\]");
        Matcher m = pattern.matcher(string);
        int lastIndex = 0;
        while(m.find()) {
            if (m.start(0) - lastIndex > 0) {
                inputs.add(new Input(string.substring(lastIndex, m.start(0)), false));
            }
            inputs.add(new Input(m.group(1), true));
            lastIndex = m.end(0);
        }
        if (string.length() - lastIndex > 0) {
            inputs.add(new Input(string.substring(lastIndex), false));
        }
    }

    public InputNode build() {
        List<Node> nodes = new ArrayList<>();
        for (Input i : inputs) {
            if (!i.isKonstruct) {
                nodes.add(new LiteralNode(i.string));
            } else {
                nodes.add(new NodeBuilder(i.string).build());
            }
        }
        return new InputNode(nodes);
    }

}
