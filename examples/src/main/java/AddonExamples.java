import io.github.darkkronicle.Konstruct.parser.NodeProcessor;
import io.github.darkkronicle.Konstruct.reader.builder.NodeBuilder;
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
        processor.addFunction(new IsMatchFunction());
        processor.addFunction(new TimeFunction());

        evaluate(processor, "'''Look at all these \\chickens\\ Yeee ehaw\\'''");

        // Basic test
        evaluate(processor, "Cool calculator! [calc((5 / 3)^3 / 4 + 3)] [#This calculates some cool stuff]");

        // Integral test
        evaluate(processor, "Integral thing: [calc('2*int(sqrt(1-x^2), x, -1, 1)')]");

        // Rounding
        evaluate(processor, "Round: [round([calc(7 / 3 * 2)],1)]");

        // Get
        evaluate(processor, "I really like the [get(2, first, second, third)] option!");

        evaluate(processor, "[replace('''\\[(.+)\\]''', 'I can use [regex] now in functions', $1, regex)]");

        evaluate(processor, "I am [romannumeral(321)] years old.");

        evaluate(processor, "[owo(This is very very cool lol <3)]");

        for (int i = 0; i < 5; i++) {
            evaluate(processor, "[get([randInt(0,2)], wb, welcome, Welcome back!)]");
        }

        evaluate(processor, "This is a multi-line\nVery cool string. When I [\nowo(do some function...\nenters can stay in... Not in function names.)]");

        evaluate(processor, "Variables: {variables}\nFunctions: {functions}\nVersion: {konstructVersion}");

        evaluate(processor, "[!calc({%})] 1 + 1");

        evaluate(processor, "[!owo({%})]'''This is super duper duper cool and means that it's easier to write functions'''");

        evaluate(processor, "This can [#\n]Absorb line breaks as well! POWER!!!");

        evaluate(processor, """
                Hey whats up! This is a script for the video on Konstruct v{konstructVersion} [# This gets replaced with the version].

                Using this cool stuff I can determine that I calculate what \\'15 * 15\\' is... on the top of my head that is [
                    calc(
                        15 * 15
                    )
                ]. I knew that because I am amazing. [# This will eat up all the newlines here...


                ]'''Do you know I'm amazing? Thank you <3. I can'''[owo(' owo very cool stuff')].
                """);

        evaluate(processor, "[if([bool([isMatch(b, You are not cool)], or, [isMatch(b, You are both cool!)])], Hahahah! True, ahahahaha false!)]");

        evaluate(processor, """
                [cool = ([owo(Holy freaking ****)])];
                {cool}!!!
                """);

        evaluate(processor, """
                [bool1 = ([isMatch(e, eee)])];
                [bool2 = ([isMatch(b, bbb)])];
                [bothOn = ([bool({bool1}, and, {bool2})])];
                bool1 is {bool1} and bool2 is {bool2}
                [if({bothOn}, Both are true!, 'not both are true :(')]
                """);

        evaluate(processor, "[calc([num = (5 + 5)] {num} + {num})]");

        evaluate(processor, "Date: [time(yyyy-MM-dd)]");

        evaluate(processor, "[type([calc(5 + 5)])]");

        evaluate(processor, "[variable = ([calc(1 + 1)])];\n[type({variable})] \n- [type({variable}a)]\n[type([isType({variable}, string)])]");
    }

    public static void evaluate(NodeProcessor processor, String input) {
        System.out.print("Input: " + input + "\nOutput: ");
        System.out.println(processor.parse(new NodeBuilder(input).build()).getResult().getContent().getString() + "\n");
    }

}
