import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.NodeProcessor;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.reader.builder.NodeBuilder;
import io.github.darkkronicle.konstruct.functions.Function;
import io.github.darkkronicle.konstruct.nodes.Node;

import java.util.List;

public class DebugExamples {

    public static void main(String[] arguments) {
        tree("This is a literal and this is a {placeholder}. Over here is a [function(argument)].");
        tree("This is a [function([func2(nestedArg,{placeholder})])].");
        tree("Here is a [function({argument},Function: [function(5 literal... {argument3},'''This is a , comma with a \\''')]{argument2})]!");
        tree("[func('')]");
        NodeProcessor handler = new NodeProcessor();
        handler.addVariable("cool", "EPIC COOL BEANS");
        handler.addFunction("lower", new Function() {
            @Override
            public Result parse(ParseContext context, List<Node> input) {
                Result res = Function.parseArgument(context, input, 0);
                if (Function.shouldReturn(res)) return res;
                return Result.success(res.getContent().getString().toLowerCase());
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
