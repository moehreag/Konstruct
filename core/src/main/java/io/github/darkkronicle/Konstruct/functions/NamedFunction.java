package io.github.darkkronicle.Konstruct.functions;

/**
 * An extension to {@link Function} that includes a string to get the name of the function
 */
public interface NamedFunction extends Function {

    /**
     * Returns the name of the function that should be used for key values in {@link io.github.darkkronicle.Konstruct.NodeProcessor}
     * @return Function name
     */
    String getName();

}
