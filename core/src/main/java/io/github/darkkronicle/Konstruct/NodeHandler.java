package io.github.darkkronicle.Konstruct;

import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.nodes.Node;

import java.util.HashMap;
import java.util.Map;

public class NodeHandler {

    private Map<String, Function> functions;
    private Map<String, String> variables;

    public NodeHandler() {
        this(new HashMap<>(), new HashMap<>());
    }

    public NodeHandler(Map<String, Function> functions, Map<String, String> variables) {
        this.functions = functions;
        this.variables = variables;
    }

    public void addVariable(String key, String value) {
        variables.put(key, value);
    }

    public void addFunction(String key, Function function) {
        functions.put(key, function);
    }

    public NodeContext createContext() {
        return new NodeContext(functions, variables);
    }

    public String parse(Node node) {
        return node.parse(createContext());
    }

}
