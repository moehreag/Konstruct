package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.type.DoubleObject;
import io.github.darkkronicle.Konstruct.type.IntegerObject;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class RoundFunction implements NamedFunction {


    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Result res = Function.parseArgument(context, input, 0);
        if (Function.shouldReturn(res)) return res;
        Optional<Double> doubleOptional = DoubleObject.fromObject(res.getContent());
        if (doubleOptional.isEmpty()) {
            return Result.success("NaN");
        }

        int places = 0;
        DecimalFormat format;
        if (input.size() == 2) {
            res = Function.parseArgument(context, input, 1);
            if (Function.shouldReturn(res)) return res;
            places = IntegerObject.fromObject(res.getContent()).orElse(0);
            places = Math.abs(places);
        }

        if (places == 0) {
            format = new DecimalFormat("#");
        } else {
            format = new DecimalFormat("#." + "#".repeat(places));
        }

        if (places == 0) {
            return Result.success(new IntegerObject(Integer.parseInt(format.format(doubleOptional.get()))));
        }

        return Result.success(new DoubleObject(Double.parseDouble(format.format(doubleOptional.get()))));
    }

    @Override
    public IntRange getArgumentCount() {
        return new IntRange(1, 2);
    }

    @Override
    public String getName() {
        return "round";
    }
}
