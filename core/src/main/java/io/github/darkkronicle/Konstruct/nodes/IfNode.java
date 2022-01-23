package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.type.NullObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class IfNode implements Node {

    @Getter
    private Node condition;

    @Getter
    private Node ifBody;

    @Getter
    private Node elseBody;

    @Override
    public Result parse(ParseContext context) {
        Result cond = condition.parse(context);
        if (Function.shouldReturn(cond)) return cond;
        if (cond.getContent().getBoolean()) {
            return ifBody.parse(context);
        } else if (elseBody != null) {
            return elseBody.parse(context);
        }
        return Result.success(new NullObject());
    }

    @Override
    public List<Node> getChildren() {
        if (elseBody == null) {
            return List.of(condition, ifBody);
        }
        return List.of(condition, ifBody, elseBody);
    }

    @Override
    public void addChild(Node node) {

    }

    @Override
    public String toString() {
        if (elseBody != null) {
            return "<if (<condition>) {<true>} {<else>}>";
        }
        return "<if (<condition>) {<true>}>";
    }
}
