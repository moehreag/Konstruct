package io.github.darkkronicle.Konstruct;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class IntRange {

    int min;
    int max;

    public boolean isInRange(int x) {
        return min <= x && x <= max;
    }

    public static IntRange of(int value) {
        return new IntRange(value, value);
    }

    public static IntRange none() {
        return new IntRange(0, 0);
    }

    public static IntRange lessThanEqual(int max) {
        return new IntRange(0, max);
    }

    public static IntRange greaterThanEqual(int min) {
        return new IntRange(min, Integer.MAX_VALUE);
    }

}
