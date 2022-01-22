package io.github.darkkronicle.Konstruct.parser;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ParseResult {

    /**
     * The context of the parse
     */
    ParseContext context;

    /**
     * The result of the parse
     */
    Result result;

}
