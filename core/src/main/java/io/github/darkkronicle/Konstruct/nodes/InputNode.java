package io.github.darkkronicle.Konstruct.nodes;

import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class InputNode implements Node {

    private List<Node> roots;

    @Override
    public Result parse(ParseContext context) {
        StringBuilder builder = new StringBuilder();
        for (Node n : roots) {
            Result res = n.parse(context);
            if (res != null) {
                builder.append(res.getContent().getString());
            }
        }
        return Result.success(builder.toString());
    }

    @Override
    public List<Node> getChildren() {
        return roots;
    }

    @Override
    public void addChild(Node node) {
        roots.add(node);
    }

}
