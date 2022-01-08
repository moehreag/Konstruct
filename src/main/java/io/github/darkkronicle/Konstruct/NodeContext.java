package io.github.darkkronicle.Konstruct;

import io.github.darkkronicle.Konstruct.functions.Function;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

public class NodeContext {

    @Getter
    private Map<String, String> variables;

    @Getter
    private Map<String, Function> functions;

    public NodeContext(Map<String, Function> functions, Map<String, String> variables) {
        this.functions = functions;
        this.variables = variables;
    }

    public Optional<String> getVariable(String key) {
        String string = variables.get(key);
        if (string == null) {
            return Optional.empty();
        }
        return Optional.of(string);
    }

    public Optional<Function> getFunction(String key) {
        Function function = functions.get(key);
        if (function == null) {
            return Optional.empty();
        }
        return Optional.of(function);
    }

}
