package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.Gate;
import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.type.BooleanObject;

import java.util.List;

public class GateNode implements Node {

    private final Node first;
    private final Node second;
    private final Gate gate;

    public GateNode(Node first, Node second, Gate gate) {
        this.first = first;
        this.second = second;
        this.gate = gate;
    }

    @Override
    public Result parse(ParseContext context) {
        Result result1 = first.parse(context);
        if (Function.shouldReturn(result1)) return result1;
        Result result2 = second.parse(context);
        if (Function.shouldReturn(result2)) return result2;

        return Result.success(result1.getContent().gate(gate, result2.getContent()));
    }

    @Override
    public List<Node> getChildren() {
        return List.of(first, second);
    }

    @Override
    public void addChild(Node node) {

    }

    @Override
    public String toString() {
        return "<gate (<node> " + gate.name + " <node>)>";
    }
}
