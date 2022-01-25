import io.github.darkkronicle.Konstruct.functions.GetFunction;
import io.github.darkkronicle.Konstruct.nodes.InputNode;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.nodes.RootNode;
import io.github.darkkronicle.Konstruct.parser.NodeProcessor;
import io.github.darkkronicle.Konstruct.reader.Tokener;
import io.github.darkkronicle.Konstruct.reader.Token;
import io.github.darkkronicle.Konstruct.reader.builder.InputNodeBuilder;
import io.github.darkkronicle.Konstruct.reader.builder.NodeBuilder;
import io.github.darkkronicle.addons.*;

public class TokenExamples {

    public static void main(String[] arguments) {
        NodeProcessor processor = new NodeProcessor();
        processor.addFunction(new OwOFunction());
        processor.addFunction(new CalculatorFunction());
        processor.addFunction(new RoundFunction());
        processor.addFunction(new RandomFunction());
        processor.addFunction(new RomanNumeralFunction());
        processor.addFunction(new ReplaceFunction());
        processor.addFunction(new IsMatchFunction());
        processor.addFunction(new TimeFunction());
//        evaluate(processor, "dingus = 'Your mother'; 'Hello! ' dingus");
        evaluate(processor, """
                l = list("one", "two", true);
                l.get(0) + " - " + get(0, l)
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

    public static void evaluateInput(NodeProcessor processor, String input) {
        InputNode node = new InputNodeBuilder(input).build();
        System.out.println(node.getTreeString() + "\n\n");
        System.out.print("Input: " + input + "\nOutput: ");
        System.out.println(processor.parse(node).getResult().getContent().getString() + "\n");
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
