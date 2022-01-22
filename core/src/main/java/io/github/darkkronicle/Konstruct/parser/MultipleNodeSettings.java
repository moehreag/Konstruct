package io.github.darkkronicle.Konstruct.parser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MultipleNodeSettings {

    public final static MultipleNodeSettings DEFAULT = MultipleNodeSettings.builder().build();

    /**
     * Allows lines to be comments if they start with -
     */
    @Builder.Default
    public final boolean commentLines = true;

    /**
     * How different nodes are separated
     */
    @Builder.Default
    public final String separatorRegex = "-{5,}";

}
