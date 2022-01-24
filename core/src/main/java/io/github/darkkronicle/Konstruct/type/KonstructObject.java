package io.github.darkkronicle.Konstruct.type;

import io.github.darkkronicle.Konstruct.Gate;
import io.github.darkkronicle.Konstruct.NodeException;

public interface KonstructObject {

    String getString();

    default KonstructObject add(KonstructObject other) {
        return new StringObject(getString() + other.getString());
    }

    default KonstructObject subtract(KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be subtracted!");
    }

    default KonstructObject multiply(KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be multiplied!");
    }

    default KonstructObject divide(KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be divided!");
    }

    default KonstructObject intDivide(KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be int divided!");
    }

    default KonstructObject modulo(KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be modulo'd!");
    }

    default KonstructObject gate(Gate gate, KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be gated!");
    }

    default boolean getBoolean() {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated as a boolean!");
    }

    default KonstructObject greaterThan(KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated greater than!");
    }

    default KonstructObject greaterThanEqual(KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated greater than equal!");
    }

    default KonstructObject lessThan(KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated less than!");
    }

    default KonstructObject lessThanEqual(KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated less than equal!");
    }

    default KonstructObject length() {
        throw new NodeException("Type " + getTypeName() + " cannot be evaluated for length!");
    }

    default KonstructObject get(KonstructObject other) {
        throw new NodeException("Type " + getTypeName() + " cannot be indexed!");
    }

    default KonstructObject equal(KonstructObject other) {
        return new BooleanObject(this.equals(other));
    }

    default KonstructObject notEqual(KonstructObject other) {
        return new BooleanObject(!this.equals(other));
    }

    String getTypeName();
}
