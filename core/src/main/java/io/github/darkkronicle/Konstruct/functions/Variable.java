package io.github.darkkronicle.Konstruct.functions;

import io.github.darkkronicle.Konstruct.type.KonstructObject;
import io.github.darkkronicle.Konstruct.type.StringObject;

import java.util.function.Supplier;

/**
 * A class to return a string that is meant to be parsed in a {@link io.github.darkkronicle.Konstruct.nodes.VariableNode}
 *
 * This is similar to a {@link Function}, but no arguments are allowed. These can be static or be changing.
 */
public interface Variable {

    /**
     * Returns the value of the argument. Does not have to stay the same.
     * @return Value of the argument.
     */
    KonstructObject getValue();

    /**
     * Constructs a variable with a non-changing result.
     * @param string String for variable to contain
     * @return Constructed {@link Variable}
     */
    static Variable of(String string) {
        return () -> new StringObject(string);
    }

    /**
     * Constructs a variable with a non-changing result.
     * @param obj {@link KonstructObject} for variable to contain
     * @return Constructed {@link Variable}
     */
    static Variable of(KonstructObject obj) {
        return () -> obj;
    }

    /**
     * Constructs a variable with a {@link Supplier} to get the value
     * @param supplier {@link Supplier} to get a string
     * @return Constructed {@link Variable}
     */
    static Variable of(Supplier<KonstructObject> supplier) {
        return supplier::get;
    }

}
