package io.github.darkkronicle.Konstruct.type;

import io.github.darkkronicle.Konstruct.NodeException;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class ListObject implements KonstructObject {

    public final static String TYPE_NAME = "list";

    @Getter
    private final List<KonstructObject> list;

    public ListObject(List<KonstructObject> list) {
        this.list = list;
    }

    @Override
    public String getString() {
        return list.toString();
    }

    @Override
    public KonstructObject add(KonstructObject other) {
        list.add(other);
        return this;
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public KonstructObject get(KonstructObject other) {
        Optional<Integer> index = IntegerObject.fromObject(other);
        if (index.isEmpty()) {
            throw new NodeException("List index has to be an integer!");
        }
        return list.get(index.get());
    }

    @Override
    public KonstructObject length() {
        return new IntegerObject(size());
    }

    public int size() {
        return list.size();
    }
}
