package io.github.darkkronicle.Konstruct;

import lombok.AllArgsConstructor;

import java.util.Locale;

@AllArgsConstructor
public enum Gate {
    AND("and", (bool1, bool2) -> bool1 && bool2),
    OR("or", (bool1, bool2) -> bool1 || bool2),
    NAND("nand", (bool1, bool2) -> !(bool1 && bool2)),
    NOR("nor", (bool1, bool2) -> !bool1 && !bool2),
    XOR("xor", (bool1, bool2) -> (bool1 && !bool2) || (!bool1 && bool2)),
    XNOR("xnor", (bool1, bool2) -> (bool1 && bool2) || (!bool1 && !bool2))
    ;

    public final String name;
    public final GateEvaluate evaluate;

    public boolean evaluate(boolean bool1, boolean bool2) {
        return evaluate.evaluate(bool1, bool2);
    }

    public interface GateEvaluate {
        boolean evaluate(boolean bool1, boolean bool2);
    }

    public static Gate getGate(String name) {
        name = name.toLowerCase(Locale.ROOT).strip();
        for (Gate gate : values()) {
            if (gate.name.equals(name)) {
                return gate;
            }
        }
        return AND;
    }

}