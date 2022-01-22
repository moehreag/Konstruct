package io.github.darkkronicle.Konstruct.reader;

import io.github.darkkronicle.Konstruct.NodeException;

public class NodeFormatException extends NodeException {

    private final int row;
    private final int column;
    private final String context;

    public NodeFormatException(int row, int column, int cursor, String total, String content) {
        super(content);
        this.row = row;
        this.column = column;
        int start = Math.max(cursor - 20, 0);
        String pre = column > 0 ? " ".repeat(cursor - start) : "";
        this.context =  pre + "V" + "\n" + total.substring(start, Math.min(cursor + 20, total.length())).replaceAll("\n", " ");
    }

    @Override
    public String getMessage() {
        return "Format Exception at " + row + ":" + column + "\n" + super.getMessage() + "\n\n" + this.context;
    }
}
