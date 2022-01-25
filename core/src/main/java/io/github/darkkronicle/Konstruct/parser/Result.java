package io.github.darkkronicle.Konstruct.parser;

import io.github.darkkronicle.Konstruct.type.KonstructObject;
import io.github.darkkronicle.Konstruct.type.StringObject;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * A class to store data from a function/node parse
 */
@Value
public class Result {

    public enum ResultType {
        /**
         * Everything that was parsed went successfully
         */
        SUCCESS,

        /**
         * Send this back
         */
        RETURN,

        /**
         * Stop everything
         */
        TERMINATE
    }

    /**
     * The type of the result
     */
    ResultType type;

    /**
     * The content of the result. Can be null which indicates blank.
     */
    KonstructObject<?> content;

    /**
     * Scope of the result
     */
    int scope;

    public Result(ResultType type, KonstructObject<?> object) {
        this(type, object, -1);
    }

    public Result(ResultType type, KonstructObject<?> object, int scope) {
        this.type = type;
        this.content = object;
        this.scope = scope;
    }

    public static Result success(String content) {
        return new Result(ResultType.SUCCESS, new StringObject(content));
    }

    public static Result success(KonstructObject<?> content) {
        return new Result(ResultType.SUCCESS, content);
    }

    public static Result of(ResultType type, String content) {
        return new Result(type, new StringObject(content));
    }

}
