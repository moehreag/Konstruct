package io.github.darkkronicle.Konstruct.type;

public class BooleanObject implements KonstructObject{

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
        try {
            return Integer.parseInt(string) != 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String boolToString(boolean bool) {
        return bool ? "1" : "0";
    }

    public static boolean fromObject(KonstructObject object) {
        if (object instanceof BooleanObject bool) {
            return bool.value;
        }
        return stringToBool(object.getString());
    }

}
