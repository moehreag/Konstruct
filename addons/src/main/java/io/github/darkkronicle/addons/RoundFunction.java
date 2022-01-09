package io.github.darkkronicle.addons;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;

import java.text.DecimalFormat;
import java.util.List;

public class RoundFunction implements NamedFunction {


    @Override
    public String parse(List<String> input) {
        Double dub;
        try {
            dub = Double.valueOf(input.get(0));
        } catch (NumberFormatException e) {
            return "NaN";
        }
        DecimalFormat format = getFormat(input);
        return format.format(dub);
    }

    private DecimalFormat getFormat(List<String> input) {
        if (input.size() == 2) {
            try {
                int places = Integer.parseInt(input.get(1));
                places = Math.abs(places);
                if (places == 0) {
                    return new DecimalFormat("#");
                }
                return new DecimalFormat("#." + "#".repeat(places));
            } catch (NumberFormatException e) {
                // Pass it and use default
            }
        }
        return new DecimalFormat("#");
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
