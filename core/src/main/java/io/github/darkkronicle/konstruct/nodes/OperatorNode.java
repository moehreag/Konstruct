package io.github.darkkronicle.konstruct.nodes;

import io.github.darkkronicle.konstruct.functions.Function;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.type.KonstructObject;

import java.util.List;
import java.util.function.BiFunction;

public class OperatorNode implements Node {

    private final Node first;
    private final Node second;
    private final BiFunction<KonstructObject<?>, KonstructObject<?>, KonstructObject<?>> evaluate;

    public OperatorNode(Node first, Node second, BiFunction<KonstructObject<?>, KonstructObject<?>, KonstructObject<?>> evaluate) {
        this.first = first;
        this.second = second;
        this.evaluate = evaluate;
    }

    @Override
    public Result parse(ParseContext context) {
        Result result1 = first.parse(context);
        if (Function.shouldExit(result1)) return result1;
        Result result2 = second.parse(context);
        if (Function.shouldExit(result1)) return result1;
        return Result.success(evaluate.apply(result1.getContent(), result2.getContent()));
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
        return "<operator " + " (<node>) (<node>)>";
    }
}
