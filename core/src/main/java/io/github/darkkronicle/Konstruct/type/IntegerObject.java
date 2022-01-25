package io.github.darkkronicle.Konstruct.type;

import io.github.darkkronicle.Konstruct.NodeException;
import lombok.Getter;

import java.util.Optional;

public class IntegerObject extends KonstructObject<IntegerObject> {

    public static final String TYPE_NAME = "int";

    @Getter
    private final int value;

    public IntegerObject(int value) {
        this.value = value;
    }

    @Override
    public String getString() {
        return String.valueOf(value);
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public KonstructObject<?> add(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new DoubleObject(doubleObject.getValue() + value);
        }
        if (other instanceof IntegerObject intObject) {
            return new IntegerObject(intObject.getValue() + value);
        }
        return new StringObject(getString() + other.getString());
    }

    @Override
    public KonstructObject<?> subtract(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new DoubleObject(value - doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new IntegerObject(value - intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be subtracted!");
    }

    @Override
    public KonstructObject<?> greaterThan(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new BooleanObject(value > doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new BooleanObject(value > intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be tested greater than!");
    }

    @Override
    public KonstructObject<?> greaterThanEqual(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new BooleanObject(value >= doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new BooleanObject(value >= intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be tested greater than equal!");
    }

    @Override
    public KonstructObject<?> lessThan(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new BooleanObject(value < doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new BooleanObject(value < intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be tested less than!");
    }

    @Override
    public KonstructObject<?> lessThanEqual(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new BooleanObject(value <= doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new BooleanObject(value <= intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be tested greater than equal!");
    }

    @Override
    public KonstructObject<?> equal(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new BooleanObject(Double.valueOf(value).equals(doubleObject.getValue()));
        }
        if (other instanceof IntegerObject intObject) {
            return new BooleanObject(value == intObject.getValue());
        }
        return super.equal(other);

    }

    @Override
    public KonstructObject<?> notEqual(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new BooleanObject(!Double.valueOf(value).equals(doubleObject.getValue()));
        }
        if (other instanceof IntegerObject intObject) {
            return new BooleanObject(value != intObject.getValue());
        }
        return super.notEqual(other);
    }

    @Override
    public KonstructObject<?> multiply(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new DoubleObject(value * doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new IntegerObject(value * intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be multiplied!");
    }

    @Override
    public KonstructObject<?> modulo(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new DoubleObject(value % doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new IntegerObject(value % intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be modulo'd!");
    }

    @Override
    public KonstructObject<?> divide(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new DoubleObject((double) value / doubleObject.getValue());
        }
        if (other instanceof IntegerObject intObject) {
            return new DoubleObject((double) value / intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be divided!");
    }

    @Override
    public KonstructObject<?> intDivide(KonstructObject<?> other) {
        if (other instanceof DoubleObject doubleObject) {
            return new IntegerObject((int) (value / doubleObject.getValue()));
        }
        if (other instanceof IntegerObject intObject) {
            return new IntegerObject(value / intObject.getValue());
        }
        throw new NodeException(other.getTypeName() + " and " + getTypeName() + " cannot be int divided!");
    }

    public static Optional<Integer> fromObject(KonstructObject<?> object) {
        if (object instanceof IntegerObject intObj) {
            return Optional.of(intObj.value);
        }
        if (object instanceof DoubleObject doubleObj) {
            return Optional.of((int) doubleObj.getValue());
        }
        String value = object.getString().strip();
        if (value.length() == 0) {
            return Optional.empty();
        }
        try {
            return Optional.of(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
