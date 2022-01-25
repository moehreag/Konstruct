package io.github.darkkronicle.Konstruct.type;

public class NullObject extends KonstructObject<NullObject> {

    public static final String TYPE_NAME = "null";

    @Override
    public KonstructObject<?> add(KonstructObject<?> other) {
        return other;
    }

    @Override
    public String getString() {
        return "";
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }
}
