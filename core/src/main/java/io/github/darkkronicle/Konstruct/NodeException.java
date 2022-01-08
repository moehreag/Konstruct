package io.github.darkkronicle.Konstruct;

public class NodeException extends IllegalArgumentException {
    public NodeException() {
        super();
    }

    public NodeException(String s) {
        super(s);
    }

    public NodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeException(Throwable cause) {
        super(cause);
    }
}
