package io.github.darkkronicle.konstruct.nodes;

import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.type.BooleanObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class BooleanNode implements Node {

    @Getter
    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public Result parse(ParseContext context) {
        return Result.success(new BooleanObject(value));
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<>(0);
    }

    @Override
    public String toString() {
        return "<boolean " + value + ">";
    }

    @Override
    public void addChild(Node node) {

    }
}
