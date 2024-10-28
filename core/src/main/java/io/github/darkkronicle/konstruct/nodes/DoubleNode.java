package io.github.darkkronicle.konstruct.nodes;

import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.type.DoubleObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DoubleNode implements Node {

    @Getter
    private final double value;

    public DoubleNode(double value) {
        this.value = value;
    }

    @Override
    public Result parse(ParseContext context) {
        return Result.success(new DoubleObject(value));
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<>(0);
    }

    @Override
    public void addChild(Node node) {

    }

    @Override
    public String toString() {
        return "<double " + value + ">";
    }

}