package io.github.darkkronicle.Konstruct.functions;

import io.github.darkkronicle.Konstruct.IntRange;

import java.util.List;

/**
 * A class to evaluate a function to be used in {@link io.github.darkkronicle.Konstruct.nodes.FunctionNode}.
 *
 * The function gets an {@link IntRange} amount of arguments puts in and a string should be returned.
 */
public interface Function {

    /**
     * Parses a function with a {@link Function#getArgumentCount()} amount of arguments.
     * @param input A {@link List} of all the variables
     * @return The processed function
     */
    String parse(List<String> input);

    /**
     * An {@link IntRange} for the amount of arguments to be put in.
     *
     * The {@link Function#parse(List)} won't be called unless the amount of arguments is within this range.
     * @return {@link IntRange} for arguments
     */
    IntRange getArgumentCount();

}
