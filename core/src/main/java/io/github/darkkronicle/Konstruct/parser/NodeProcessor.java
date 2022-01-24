package io.github.darkkronicle.Konstruct.parser;

import io.github.darkkronicle.Konstruct.Konstruct;
import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.functions.*;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.type.StringObject;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Processes {@link Node}'s that have been built. This class is meant to store functions and variables
 * to be referenced at a later time. This class automatically sends in {@link ParseContext} to the
 * parsed {@link Node}'s.
 */
public class NodeProcessor {

    public final String[] RESERVED_VARIABLES = {"konstructVersion", "functions", "variables", "%"};

    /**
     * All stored {@link Function}'s in this class
     */
    @Getter
    private Map<String, Function> functions;

    /**
     * All stored {@link Variable}'s in this class
     */
    @Getter
    private Map<String, Variable> variables;

    /**
     * Constructs an empty {@link NodeProcessor} with zero functions or variables.
     */
    public NodeProcessor() {
        this(new HashMap<>(), new HashMap<>());
    }

    /**
     * Constructs a {@link NodeProcessor} with functions and variables.
     * @param functions {@link Map} of {@link Function} with the key being the name and value being the function
     * @param variables {@link Map} of {@link Variable} with key being the name and value being the value of the variable
     */
    public NodeProcessor(Map<String, Function> functions, Map<String, Variable> variables) {
        this.functions = functions;
        this.variables = variables;
        this.addDefaults();
    }

    private void addDefaults() {
        this.variables.put("empty", Variable.of(""));
        this.variables.put("konstructVersion", Variable.of(Konstruct.INFO.getVersion()));
        this.variables.put("functions", () -> new StringObject(String.join(", ", functions.keySet().stream().toList())));
        this.variables.put("variables", () -> new StringObject(String.join(", ", variables.keySet().stream().toList())));
        addFunction(new NullFunction());
        addFunction(new ReturnFunction());
        addFunction(new ExitFunction());
        addFunction(new TypeFunction());
        addFunction(new IsTypeFunction());
        addFunction(new LenFunction());
        addFunction(new GetFunction());
        addFunction(new ListFunction());
    }

    /**
     * Adds a {@link Variable} to the class
     * @param key Key to reference this variable
     * @param value {@link Variable} to get value
     */
    public void addVariable(String key, Variable value) {
        if (Arrays.asList(RESERVED_VARIABLES).contains(key)) {
            throw new NodeException("Variable name " + key + " is reserved for Konstruct!");
        }
        variables.put(key, value);
    }

    /**
     * Adds a {@link Variable} to the class constructed out of just a string
     * @param key Key of the variable
     * @param value Value of the variable
     */
    public void addVariable(String key, String value) {
        addVariable(key, Variable.of(value));
    }

    /**
     * Adds a {@link Function} to the class
     * @param key Key to reference this function
     * @param function {@link Function} to execute on call
     */
    public void addFunction(String key, Function function) {
        functions.put(key, function);
    }

    /**
     * Adds a {@link NamedFunction} to the class that has a specified name
     * @param function {@link NamedFunction} to add
     */
    public void addFunction(NamedFunction function) {
        addFunction(function.getName(), function);
    }

    /**
     * Adds all functions and variables from a different {@link NodeProcessor}
     * @param processor {@link NodeProcessor} to copy from
     */
    public void addAll(NodeProcessor processor) {
        functions.putAll(processor.functions);
        variables.putAll(processor.variables);
    }

    /**
     * Creates a {@link ParseContext} with the current settings of this class
     * @return Constructed {@link ParseContext}
     */
    public ParseContext createContext() {
        return new ParseContext(functions, variables);
    }

    /**
     * Parses a {@link Node} with the current functions and variables
     * @param node {@link Node} to parse
     * @return The parsed string
     */
    public ParseResult parse(Node node) {
        ParseContext context = createContext();
        return new ParseResult(context, node.parse(context));
    }

    /**
     * Copies the current {@link NodeProcessor} into a new object. The maps are new, but the functions/variables retain
     * original reference.
     * @return Shallow copy of {@link NodeProcessor}
     */
    public NodeProcessor copy() {
        NodeProcessor newProcessor = new NodeProcessor();
        newProcessor.addAll(this);
        return newProcessor;
    }

}
