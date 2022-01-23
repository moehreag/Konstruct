package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.type.NullObject;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WhileNode implements Node {

    private Node condition;
    private Node body;


    @Override
    public Result parse(ParseContext context) {
        Result result;
        while (true) {
            result = condition.parse(context);
            if (Function.shouldReturn(result)) return result;
            if (result.getContent().getBoolean()) {
                body.parse(context);
            } else {
                break;
            }
        }
        return Result.success(new NullObject());
    }

    @Override
    public List<Node> getChildren() {
        return List.of(condition, body);
    }

    @Override
    public void addChild(Node node) {

    }

    @Override
    public String toString() {
        return "<while (<condition>) {<body>}>";
    }
}
