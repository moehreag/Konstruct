package io.github.darkkronicle.Konstruct.functions;

import io.github.darkkronicle.Konstruct.IntRange;
import io.github.darkkronicle.Konstruct.ParseContext;
import io.github.darkkronicle.Konstruct.Result;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.List;

/**
 * A class to evaluate a function to be used in {@link io.github.darkkronicle.Konstruct.nodes.FunctionNode}.
 *
 * The function gets an {@link IntRange} amount of arguments puts in and a string should be returned.
 */
public interface Function {

    /**
     * Contains the result of the function
     * @param context The context of the function
     * @param input A {@link List} of all the arguments
     * @return The processed function
     */
    Result parse(ParseContext context, List<Node> input);

    /**
     * An {@link IntRange} for the amount of arguments to be put in.
     *
     * The {@link Function#parse(ParseContext, List)} won't be called unless the amount of arguments is within this range.
     * @return {@link IntRange} for arguments
     */
    IntRange getArgumentCount();

    /**
     * A utility function to parse and format an argument
     * @param context {@link ParseContext} containing the context to parse the input
     * @param input A list of arguments that are {@link Node}'s
     * @throws IndexOutOfBoundsException if an index is out of bounds for the input
     * @throws io.github.darkkronicle.Konstruct.NodeException if something internally goes wrong parsing
     */
    static Result parseArgument(ParseContext context, List<Node> input, int index) {
        return input.get(index).parse(context);
    }

    /**
     * Checks if a result is blocking and should stop current execution.
     */
    static boolean shouldReturn(Result result) {
        return result.getType() == Result.ResultType.CANCEL || result.getType() == Result.ResultType.TERMINATE;
    }

}
