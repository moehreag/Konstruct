package io.github.darkkronicle.Konstruct;

import io.github.darkkronicle.Konstruct.builder.NodeBuilder;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.reader.TokenSettings;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Handles multiple nodes and returns one output. This is more of a scripting functionality.
 *
 * To add variables and functions get the processor and add it to that.
 */
public class MultipleNodeProcessor {

    @Setter
    @Getter
    private String inputVariable;

    @Getter
    private List<Node> nodes;

    @Getter
    private NodeProcessor processor;

    /**
     * Predicate that will be used to determine if the node value should be used to set the value of the input.
     */
    @Getter
    @Setter
    private Predicate<ParseResult> useResult = (node) -> true;


    public MultipleNodeProcessor(NodeProcessor processor, List<Node> nodes) {
        this(processor, nodes, "input");
    }

    public MultipleNodeProcessor(NodeProcessor processor, List<Node> nodes, String inputVariable) {
        this.processor = processor;
        this.inputVariable = inputVariable;
        this.nodes = nodes;
    }

    public Optional<Result> parse(String input) {
        for (Node node : nodes) {
            processor.addVariable(inputVariable, input);
            ParseResult result = processor.parse(node);
            if (result.getResult().getType() == Result.ResultType.TERMINATE) {
                return Optional.empty();
            }
            if (result.getResult().getType() == Result.ResultType.CANCEL) {
                return Optional.of(Result.success(input));
            }
            if (useResult.test(result)) {
                input = result.getResult().getContent();
            }
            processor.getVariables().remove(inputVariable);
        }
        return Optional.of(Result.success(input));
    }

    /**
     * Creates a multiple node processor
     * @param processor Processor with variables and functions
     * @param settings Settings for the multiple nodes
     * @param tokenSettings Settings for the token
     * @param string String containing all node data
     * @return A newly constructed class
     */
    public static MultipleNodeProcessor fromString(NodeProcessor processor, MultipleNodeSettings settings, TokenSettings tokenSettings, String string) {
        if (settings.commentLines) {
            string = removeComments(string);
        }
        String[] nodeStrings = string.split(settings.separatorRegex);
        List<Node> nodes = new ArrayList<>();
        for (String s : nodeStrings) {
            nodes.add(new NodeBuilder(s, tokenSettings).build());
        }
        return new MultipleNodeProcessor(processor, nodes);
    }

    private static String removeComments(String input) {
        StringBuilder builder = new StringBuilder();
        for (String string : input.split("\n")) {
            if (!string.startsWith("#")) {
                builder.append(string);
            }
        }
        return builder.toString();
    }


}
