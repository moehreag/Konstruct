package io.github.darkkronicle.Konstruct.type;

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

    String getTypeName();
}
