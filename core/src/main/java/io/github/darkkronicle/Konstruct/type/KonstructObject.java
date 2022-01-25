package io.github.darkkronicle.Konstruct.type;

import io.github.darkkronicle.Konstruct.Gate;
import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.ObjectFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class KonstructObject<K extends KonstructObject<?>> {

    @Getter
    private final Map<String, ObjectFunction<K>> functions;

    public KonstructObject() {
        this(new ArrayList<>(0));
    }

    public KonstructObject(List<ObjectFunction<K>> functions) {
        this.functions = new HashMap<>();
        for (ObjectFunction<K> function : functions) {
            this.functions.put(function.getName(), function);
        }
    }

    public KonstructObject(Map<String, ObjectFunction<K>> functions) {
        this.functions = functions;
    }

    public abstract String getString();

    public abstract String getTypeName();

    public KonstructObject<?> add(KonstructObject<?> other) {
        return new StringObject(getString() + other.getString());
    }

    public KonstructObject<?> subtract(KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be subtracted!");
    }

    public KonstructObject<?> multiply(KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be multiplied!");
    }

    public KonstructObject<?> divide(KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be divided!");
    }

    public KonstructObject<?> intDivide(KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be int divided!");
    }

    public KonstructObject<?> modulo(KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be modulo'd!");
    }

    public KonstructObject<?> gate(Gate gate, KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be gated!");
    }

    public boolean getBoolean() {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated as a boolean!");
    }

    public KonstructObject<?> greaterThan(KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated greater than!");
    }

    public KonstructObject<?> greaterThanEqual(KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated greater than equal!");
    }

    public KonstructObject<?> lessThan(KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated less than!");
    }

    public KonstructObject<?> lessThanEqual(KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated less than equal!");
    }

    public KonstructObject<?> length() {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated for length!");
    }

    public KonstructObject<?> get(KonstructObject<?> other) {
        throw new NodeException("Type " + getTypeName() + " cannot be indexed!");
    }

    public KonstructObject<?> equal(KonstructObject<?> other) {
        return new BooleanObject(this.equals(other));
    }

    public KonstructObject<?> notEqual(KonstructObject<?> other) {
        return new BooleanObject(!this.equals(other));
    }

    public Result execute(int scope, String functionName, ParseContext context, List<Node> arguments) {
        ObjectFunction<K> function = this.functions.get(functionName);
        if (function == null) {
            throw new NodeException("No function named " + functionName + " for object type " + getTypeName() + " found!");
        }
        List<Node> argumentsList = new ArrayList<>(arguments);
        if (!function.getArgumentCount().isInRange(argumentsList.size())) {
            throw new NodeException("Too many arguments! " + this);
        }
        Result result = function.parse(context, (K) this, argumentsList);
        if (Function.shouldReturn(result)) {
            return result;
        }
        return new Result(Result.ResultType.SUCCESS, result.getContent(), scope);
    }

}
