import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.NodeProcessor;
import io.github.darkkronicle.Konstruct.builder.NodeBuilder;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;

public class DebugExamples {

    public static void main(String[] arguments) {
        tree("This is a literal and this is a {placeholder}. Over here is a [function(argument)].");
        tree("This is a [function([func2(nestedArg,{placeholder})])].");
        tree("Here is a [function({argument},[function(5{argument3},This is a \\, comma)]{argument2})]!");
        NodeProcessor handler = new NodeProcessor();
        handler.addVariable("cool", "EPIC COOL BEANS");
        handler.addFunction("lower", new Function() {
            @Override
            public String parse(List<String> input) {
                return input.get(0).toLowerCase();
            }

            @Override
            public IntRange getArgumentCount() {
                return IntRange.of(1);
            }
        });

        parse(handler, "This is an {cool} [lower(MOMENT)]");
    }

    public static void tree(String input) {
        NodeBuilder builder = new NodeBuilder(input);
        System.out.println(input + "\n" + builder.build().getTreeString() + "\n\n");
    }

    public static void parse(NodeProcessor handler, String input) {
        Node node = new NodeBuilder(input).build();
        System.out.println(input + "\n" + handler.parse(node));
    }

}
