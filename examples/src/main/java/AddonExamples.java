import io.github.darkkronicle.Konstruct.functions.GetFunction;
import io.github.darkkronicle.Konstruct.parser.NodeProcessor;
import io.github.darkkronicle.Konstruct.reader.builder.InputNodeBuilder;
import io.github.darkkronicle.addons.*;

public class AddonExamples {

    public static void main(String[] arguments) {
        NodeProcessor processor = new NodeProcessor();

        processor.addFunction(new CalculatorFunction());
        processor.addFunction(new RoundFunction());
        processor.addFunction(new RandomFunction());
        processor.addFunction(new RomanNumeralFunction());
        processor.addFunction(new OwOFunction());
        processor.addFunction(new ReplaceFunction());
        processor.addFunction(new IsMatchFunction());
        processor.addFunction(new TimeFunction());

        // Basic test
        evaluate(processor, "Cool calculator! [[calc('(5 / 3)^3 / 4 + 3') # Calculates cool stuff]]");

        // Integral test
        evaluate(processor, "Integral thing: [[integral = '2*int(sqrt(1-x^2), x, -1, 1)'; calc(integral)]]");

        // Rounding
        evaluate(processor, "Round: [[round(5 * 4 / 3)]]");

        // Get
        evaluate(processor, "I really like the [[get(2, 'first', 'second', 'third')]] option!");

        evaluate(processor, "[[replace('''\\[(.+)\\]''', 'I can use [regex] now in functions', '$1', 'regex')]]");

        evaluate(processor, "I am [[romannumeral(321)]] years old.");

        evaluate(processor, "[[owo('This is very very cool lol <3')]]");

        for (int i = 0; i < 5; i++) {
            evaluate(processor, "[[get(randInt(0,2), 'wb', 'welcome', 'Welcome back!')]]");
        }

        evaluate(processor, "This is a multi-line\nVery cool string. When I [[\nowo('do some function...\nenters can stay in... Not in function names.')]]");

        evaluate(processor, "Variables: [[variables]]\nFunctions: [[functions]]\nVersion: [[konstructVersion]]");

        evaluate(processor, """
                Hey whats up! This is a script for the video on Konstruct v[[konstructVersion # this gets replaced with the version]].

                Using this cool stuff I can determine that I calculate what \\'15 * 15\\' is... on the top of my head that is [[
                   15 * 15
                ]]. I knew that because I am amazing. [[#| This will eat up all the newlines here...


                |#]] Do you know I'm amazing? Thank you <3. I can[[owo(' owo very cool stuff')]].
                """);

        evaluate(processor, """
                [[
                cool = (owo('Holy freaking ****'));
                cool + '!!!'
                ]]
                """);

        evaluate(processor, "Date: [[time('yyyy-MM-dd')]]");

    }

    public static void evaluate(NodeProcessor processor, String input) {
        System.out.print("Input: " + input + "\nOutput: ");
        System.out.println(processor.parse(new InputNodeBuilder(input).build()).getResult().getContent().getString() + "\n");
    }

}
