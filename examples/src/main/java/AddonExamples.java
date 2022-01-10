import io.github.darkkronicle.Konstruct.NodeProcessor;
import io.github.darkkronicle.Konstruct.builder.NodeBuilder;
import io.github.darkkronicle.addons.CalculatorFunction;
import io.github.darkkronicle.addons.GetFunction;
import io.github.darkkronicle.addons.OwOFunction;
import io.github.darkkronicle.addons.RandomFunction;
import io.github.darkkronicle.addons.RomanNumeralFunction;
import io.github.darkkronicle.addons.RoundFunction;

public class AddonExamples {

    public static void main(String[] arguments) {
        NodeProcessor processor = new NodeProcessor();
        CalculatorFunction calculator = new CalculatorFunction();
        RoundFunction round = new RoundFunction();
        GetFunction get = new GetFunction();
        processor.addFunction(calculator.getName(), calculator);
        processor.addFunction(round.getName(), round);
        processor.addFunction(get.getName(), get);
        processor.addFunction(new RandomFunction());
        processor.addFunction(new RomanNumeralFunction());
        processor.addFunction(new OwOFunction());

        // Basic test
        evaluate(processor, "Cool calculator! [calc((5 / 3)^3 / 4 + 3)]");

        processor.addVariable("integral", "2*int(sqrt(1-x^2), x, -1, 1)");
        // Integral test
        evaluate(processor, "Integral thing: [calc({integral})]");

        // Rounding
        evaluate(processor, "Round: [round([calc(7 / 3 * 2)],1)]");

        // Get
        evaluate(processor, "I really like the [get(2, first, second, third)] option!");

        evaluate(processor, "I am [romannumeral(50321)] years old.");

        evaluate(processor, "[owo(This is very very cool lol <3)]");

        for (int i = 0; i < 5; i++) {
            evaluate(processor, "[get([randint(0,2)], wb, welcome, Welcome back!)]");
        }
    }

    public static void evaluate(NodeProcessor processor, String input) {
        System.out.print("Input: " + input + "\nOutput: ");
        System.out.println(processor.parse(new NodeBuilder(input).build()) + "\n");
    }

}
