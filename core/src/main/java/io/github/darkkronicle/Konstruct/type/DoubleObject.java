package io.github.darkkronicle.Konstruct.type;

import io.github.darkkronicle.Konstruct.NodeException;
import lombok.Getter;

import java.util.Optional;

public class DoubleObject implements KonstructObject {

    public static final String TYPE_NAME = "double";

    @Getter
    private final double value;

    public DoubleObject(double value) {
        this.value = value;
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public String getString() {
        return String.valueOf(value);
    }

    @Override
    public KonstructObject add(KonstructObject other) {
        if (other instanceof DoubleObject doubleObject) {
            return new DoubleObject(doubleObject.getValue() + value);
        }
        if (other instanceof IntegerObject intObject) {
            return new DoubleObject(intObject.getValue() + value);
        }
        return new StringObject(getString() + other.getString());
    }

    @Override
    public KonstructObject subtract(KonstructObject other) {
        if (other instanceof DoubleObject doubleObject) {
            return new DoubleObject(value - doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new DoubleObject(value - intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be subtracted!");
    }

    @Override
    public KonstructObject multiply(KonstructObject other) {
        if (other instanceof DoubleObject doubleObject) {
            return new DoubleObject(value * doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new DoubleObject(value * intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be multiplied!");
    }

    @Override
    public KonstructObject divide(KonstructObject other) {
        if (other instanceof DoubleObject doubleObject) {
            return new DoubleObject(value / doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new DoubleObject(value / intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be divided!");
    }

    @Override
    public KonstructObject intDivide(KonstructObject other) {
        if (other instanceof DoubleObject doubleObject) {
            return new IntegerObject((int) (value / doubleObject.getValue()));
        }
        if (other instanceof IntegerObject intObject) {
            return new IntegerObject((int) value / intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be int divided!");
    }

    public static Optional<Double> fromObject(KonstructObject object) {
        if (object instanceof IntegerObject intObj) {
            return Optional.of((double) intObj.getValue());
        }
        if (object instanceof DoubleObject doubleObj) {
            return Optional.of(doubleObj.getValue());
        }
        String value = object.getString().strip();
        if (value.length() == 0) {
            return Optional.empty();
        }
        try {
            return Optional.of(Double.valueOf(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}
