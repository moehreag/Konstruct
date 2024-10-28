package io.github.darkkronicle.konstruct.type;

import io.github.darkkronicle.konstruct.Gate;

import java.util.Locale;

public class BooleanObject extends KonstructObject<BooleanObject> {

    private final boolean value;

    public static final String TYPE_NAME = "boolean";

    public BooleanObject(boolean value) {
        this.value = value;
    }

    public static BooleanObject fromString(String input) {
        return new BooleanObject(stringToBool(input.strip()));
    }

    @Override
    public String getString() {
        return boolToString(value);
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    public static boolean stringToBool(String string) {
        return Boolean.parseBoolean(string);
    }

    public static String boolToString(boolean bool) {
        return String.valueOf(bool).toLowerCase(Locale.ROOT);
    }

    @Override
    public KonstructObject<?> gate(Gate gate, KonstructObject<?> other) {
        boolean bool1 = getBoolean();
        boolean bool2 = other.getBoolean();
        return new BooleanObject(gate.evaluate(bool1, bool2));
    }

    public static boolean fromObject(KonstructObject<?> object) {
        if (object instanceof BooleanObject bool) {
            return bool.value;
        }
        return stringToBool(object.getString());
    }

    @Override
    public KonstructObject<?> equal(KonstructObject<?> other) {
        if (other instanceof BooleanObject booleanObject) {
            return new BooleanObject(value == booleanObject.value);
        }
        return super.equal(other);
    }

    @Override
    public KonstructObject<?> notEqual(KonstructObject<?> other) {
        if (other instanceof BooleanObject booleanObject) {
            return new BooleanObject(value != booleanObject.value);
        }
        return super.notEqual(other);
    }

    @Override
    public boolean getBoolean() {
        return value;
    }
}
