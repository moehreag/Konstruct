package io.github.darkkronicle.Konstruct.type;

import io.github.darkkronicle.Konstruct.NodeException;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.ObjectFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class ListObject extends KonstructObject<ListObject> {

    public final static String TYPE_NAME = "list";

    private final static List<ObjectFunction<ListObject>> FUNCTIONS = List.of(
            new ObjectFunction<>() {
                @Override
                public Result parse(ParseContext context, ListObject self, List<Node> input) {
                    Result result = Function.parseArgument(context, input, 0);
                    if (Function.shouldReturn(result)) return result;
                    return Result.success(self.get(result.getContent()));
                }

                @Override
                public String getName() {
                    return "get";
                }

                @Override
                public IntRange getArgumentCount() {
                    return IntRange.of(1);
                }
            }
    );

    @Getter
    private final List<KonstructObject<?>> list;

    public ListObject(List<KonstructObject<?>> list) {
        super(FUNCTIONS);
        this.list = list;
    }

    @Override
    public String getString() {
        return list.toString();
    }

    @Override
    public KonstructObject<?> add(KonstructObject<?> other) {
        list.add(other);
        return this;
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public KonstructObject<?> get(KonstructObject<?> other) {
        Optional<Integer> index = IntegerObject.fromObject(other);
        if (index.isEmpty()) {
            throw new NodeException("List index has to be an integer!");
        }
        return list.get(index.get());
    }

    @Override
    public IntegerObject length() {
        return new IntegerObject(size());
    }

    public int size() {
        return list.size();
    }
}
