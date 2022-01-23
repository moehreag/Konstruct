import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.RootNode;
import io.github.darkkronicle.Konstruct.parser.NodeProcessor;
import io.github.darkkronicle.Konstruct.reader.Tokener;
import io.github.darkkronicle.Konstruct.reader.Token;
import io.github.darkkronicle.Konstruct.reader.builder.NodeBuilder;
import io.github.darkkronicle.addons.*;

public class TokenExamples {

    public static void main(String[] arguments) {
        NodeProcessor processor = new NodeProcessor();
        processor.addFunction(new OwOFunction());
        processor.addFunction(new CalculatorFunction());
        processor.addFunction(new RoundFunction());
        processor.addFunction(new GetFunction());
        processor.addFunction(new RandomFunction());
        processor.addFunction(new RomanNumeralFunction());
        processor.addFunction(new ReplaceFunction());
        processor.addFunction(new IsMatchFunction());
        processor.addFunction(new TimeFunction());
//        evaluate(processor, "dingus = 'Your mother'; 'Hello! ' dingus");
        evaluate(processor, """
                if (5 < 0) {
                    out = '5 is less than 0'
                } elif (5 > 0) {
                    out = '5 is greater than 0'
                } else {
                    out = 'Well this is awkward'
                };
                out = out + " ye!";
                type(out)
                """);
    }

    public static void printTokens(String argument) {
        Tokener lexer = new Tokener(argument);
        StringBuilder builder = new StringBuilder();
        for (Token t : lexer.getTokens()) {
            builder.append(t).append('\n');
        }
        System.out.println(builder);
    }

    public static void evaluate(NodeProcessor processor, String input) {
        tree(input);
        System.out.print("Input: " + input + "\nOutput: ");
        System.out.println(processor.parse(new NodeBuilder(input).build()).getResult().getContent().getString() + "\n");
    }

    public static void tree(String input) {
        NodeBuilder builder = new NodeBuilder(input);
        RootNode root = builder.build();
        for (Node child : root.getPrecommands()) {
            System.out.println("\n" + child.getTreeString());
            System.out.println("--------------------");
        }
        System.out.println("\n" + root.getTreeString() + "\n\n");
    }

}
