package io.github.darkkronicle.Konstruct.type;

import io.github.darkkronicle.Konstruct.functions.ObjectFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;

import java.util.List;
import java.util.Locale;

public class StringObject extends KonstructObject<StringObject> {

    public static final String TYPE_NAME = "string";

    private final String value;

    private final static List<ObjectFunction<StringObject>> FUNCTIONS = List.of(
            new ObjectFunction<>() {
                @Override
                public Result parse(ParseContext context, StringObject self, List<Node> input) {
                    return Result.success(new StringObject(self.value.toLowerCase(Locale.ROOT)));
                }

                @Override
                public String getName() {
                    return "lower";
                }

                @Override
                public IntRange getArgumentCount() {
                    return IntRange.of(0);
                }
            },
            new ObjectFunction<>() {
                @Override
                public Result parse(ParseContext context, StringObject self, List<Node> input) {
                    return Result.success(new StringObject(self.value.toUpperCase(Locale.ROOT)));
                }

                @Override
                public String getName() {
                    return "upper";
                }

                @Override
                public IntRange getArgumentCount() {
                    return IntRange.of(0);
                }
            }
    );

    public StringObject(String value) {
        super(FUNCTIONS);
        this.value = value;
    }

    @Override
    public KonstructObject<?> add(KonstructObject<?> other) {
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
    public KonstructObject<?> equal(KonstructObject<?> other) {
        return new BooleanObject(other.getString().equals(getString()));
    }

    @Override
    public KonstructObject<?> notEqual(KonstructObject<?> other) {
        return new BooleanObject(!other.getString().equals(getString()));
    }
}
