package io.github.darkkronicle.Konstruct.type;

public class StringObject implements KonstructObject {

    public static final String TYPE_NAME = "string";

    private final String value;

    public StringObject(String value) {
        this.value = value;
    }

    @Override
    public KonstructObject add(KonstructObject other) {
        return new StringObject(value + other.getString());
    }

    @Override
    public String getString() {
        return value;
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public KonstructObject equal(KonstructObject other) {
        return new BooleanObject(other.getString().equals(getString()));
    }

    @Override
    public KonstructObject notEqual(KonstructObject other) {
        return new BooleanObject(!other.getString().equals(getString()));
    }
}
