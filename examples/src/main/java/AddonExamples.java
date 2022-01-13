import io.github.darkkronicle.Konstruct.NodeProcessor;
import io.github.darkkronicle.Konstruct.builder.NodeBuilder;
import io.github.darkkronicle.addons.*;

public class AddonExamples {

    public static void main(String[] arguments) {
        NodeProcessor processor = new NodeProcessor();

        processor.addFunction(new CalculatorFunction());
        processor.addFunction(new RoundFunction());
        processor.addFunction(new GetFunction());
        processor.addFunction(new RandomFunction());
        processor.addFunction(new RomanNumeralFunction());
        processor.addFunction(new OwOFunction());
        processor.addFunction(new ReplaceFunction());

        evaluate(processor, "'''Look at all these \\chickens\\ Yeee ehaw\\'''");

        // Basic test
        evaluate(processor, "Cool calculator! [calc((5 / 3)^3 / 4 + 3)] [#This calculates some cool stuff]");

        // Integral test
        evaluate(processor, "Integral thing: [calc('2*int(sqrt(1-x^2), x, -1, 1)')]");

        // Rounding
        evaluate(processor, "Round: [round([calc(7 / 3 * 2)],1)]");

        // Get
        evaluate(processor, "I really like the [get(2, first, second, third)] option!");

        evaluate(processor, "[replace('''\\[(.+)\\]''', 'I can use [regex] now in functions', $1)]");

        evaluate(processor, "I am [romannumeral(321)] years old.");

        evaluate(processor, "[owo(This is very very cool lol <3)]");

        for (int i = 0; i < 5; i++) {
            evaluate(processor, "[get([randint(0,2)], wb, welcome, Welcome back!)]");
        }

        evaluate(processor, "This is a multi-line\nVery cool string. When I [\nowo(do some function...\nenters can stay in... Not in function names.)]");

        evaluate(processor, "Variables: {variables}\nFunctions: {functions}\nVersion: {konstructVersion}");

        evaluate(processor, "[!calc({%})] 1 + 1");

        evaluate(processor, "[!owo({%})]'''This is super duper duper cool and means that it's easier to write functions'''");

        evaluate(processor, "This can [#\n]Absorb line breaks as well! POWER!!!");
    }

    public static void evaluate(NodeProcessor processor, String input) {
        System.out.print("Input: " + input + "\nOutput: ");
        System.out.println(processor.parse(new NodeBuilder(input).build()) + "\n");
    }

}
