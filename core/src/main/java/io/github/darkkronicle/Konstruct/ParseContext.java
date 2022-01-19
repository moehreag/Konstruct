package io.github.darkkronicle.Konstruct;

import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.Variable;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A container class that stores information about the current parse.
 *
 * Nodes on build can reference variables and functions from this class
 */
public class ParseContext {

    @Getter
    private final Map<String, Variable> variables;

    @Getter
    private final Map<String, Variable> localVariables;

    @Getter
    private final Map<String, Function> functions;

    /**
     * Constructs a basic context
     * @param functions {@link Function}'s that should be accessible
     * @param variables Variables that should be accessible
     */
    public ParseContext(Map<String, Function> functions, Map<String, Variable> variables) {
        this.functions = functions;
        this.variables = variables;
        this.localVariables = new HashMap<>();
    }

    /**
     * Returns an optional of a variable that is in this context.
     * @param key The key for the variable
     * @return The variable, if present
     */
    public Optional<Variable> getVariable(String key) {
        Variable variable = variables.get(key);
        if (variable == null) {
            variable = localVariables.get(key);
            if (variable == null) {
                return Optional.empty();
            }
        }
        return Optional.of(variable);
    }

    /**
     * Returns an optional of a {@link Function} that is in this context.
     * @param key The key for the function within the functions map
     * @return The {@link Function}, if present
     */
    public Optional<Function> getFunction(String key) {
        Function function = functions.get(key);
        if (function == null) {
            return Optional.empty();
        }
        return Optional.of(function);
    }

}
