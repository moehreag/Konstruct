package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.type.IntegerObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class IntNode implements Node {

    @Getter
    private final int value;

    public IntNode(int value) {
        this.value = value;
    }

    @Override
    public Result parse(ParseContext context) {
        return Result.success(new IntegerObject(value));
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<>(0);
    }

    @Override
    public String toString() {
        return "<int " + value + ">";
    }

    @Override
    public void addChild(Node node) {

    }
}
