package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.Result;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.text.DecimalFormat;
import java.util.List;

public class RoundFunction implements NamedFunction {


    @Override
    public Result parse(ParseContext context, List<Node> input) {
        Double dub;
        try {
            Result res = Function.parseArgument(context, input, 0);
            if (Function.shouldReturn(res)) return res;
            dub = Double.valueOf(res.getContent().strip());
        } catch (NumberFormatException e) {
            return Result.success("NaN");
        }

        DecimalFormat format;
        if (input.size() == 2) {
            try {
                Result res = Function.parseArgument(context, input, 1);
                if (Function.shouldReturn(res)) return res;
                int places = Integer.parseInt(res.getContent().strip());
                places = Math.abs(places);
                if (places == 0) {
                    format = new DecimalFormat("#");
                } else {
                    format = new DecimalFormat("#." + "#".repeat(places));
                }
            } catch (NumberFormatException e) {
                format = new DecimalFormat("#");
            }
        } else {
            format = new DecimalFormat("#");
        }

        return Result.success(format.format(dub));
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
