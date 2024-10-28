package io.github.darkkronicle.konstruct.nodes;

import io.github.darkkronicle.konstruct.functions.Function;
import io.github.darkkronicle.konstruct.functions.Variable;
import io.github.darkkronicle.konstruct.parser.IntRange;
import io.github.darkkronicle.konstruct.parser.ParseContext;
import io.github.darkkronicle.konstruct.parser.Result;
import io.github.darkkronicle.konstruct.type.NullObject;

import java.util.List;

public class FunctionDefinitionNode implements Node  {

    private Node body;
    private List<String> variableNames;
    private Function built;
    private String name;

    public FunctionDefinitionNode(String name, Node body, List<String> variableNames) {
        this.name = name;
        this.body = body;
        this.variableNames = variableNames;
        this.built = new Function() {
            @Override
            public Result parse(ParseContext context, List<Node> input) {
                context = context.copy();
                for (int i = 0; i < variableNames.size(); i++) {
                    Result varResult = Function.parseArgument(context, input, i);
                    if (Function.shouldReturn(varResult)) return varResult;
                    context.addLocalVariable(variableNames.get(i), Variable.of(varResult.getContent()));
                }
                return body.parse(context);
            }

            @Override
            public IntRange getArgumentCount() {
                return IntRange.of(variableNames.size());
            }
        };
    }

    @Override
    public Result parse(ParseContext context) {
        context.addFunction(name, built);
        return Result.success(new NullObject());
    }

    @Override
    public List<Node> getChildren() {
        return List.of(body);
    }

    @Override
    public void addChild(Node node) {

    }

    @Override
    public String toString() {
        return "<def function " + name + "(" + String.join(",", variableNames) + ")>";
    }
}
