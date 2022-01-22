package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.type.IntegerObject;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Converts the input integer into a roman numeral
 */
public class RomanNumeralFunction implements NamedFunction {

    private static final TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    /**
     * Turns a number into a Roman Numeral.
     *
     * <p>Example: 4 -> IV
     *
     * <p>https://stackoverflow.com/questions/12967896/converting-integers-to-roman-numerals-java/12968022
     *
     * @param number Example to convert to
     * @return String or Roman Numeral
     */
    public static String toRoman(int number) {
        boolean neg = false;
        if (number == 0) {
            return "O";
        }
        if (number < 0) {
            neg = true;
            number = -1 * number;
        }
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        if (neg) {
            return "-" + map.get(l) + toRoman(number - l);
        } else {
            return map.get(l) + toRoman(number - l);
        }
    }

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Result res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;
        Optional<Integer> num = IntegerObject.fromObject(res.getContent());
        if (num.isEmpty()) {
            return Result.success("NaN");
        }
        if (Math.abs(num.get()) > 1000000) {
            return Result.success("TooBig");
        }
        return Result.success(toRoman(num.get()));
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(1);
    }

    @Override
    public String getName() {
        return "romannumeral";
    }
}
