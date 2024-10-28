package io.github.darkkronicle.konstruct.parser;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * A range of positive {@link Integer}'s. Any {@link Integer} can be checked to see if it is within this range.
 *
 * <p>Note: This range is inclusive on both minimum and maximum</p>
 */
@Value
@AllArgsConstructor
public class IntRange {

    /**
     * Minimum value of range. Inclusive.
     */
    int min;

    /**
     * Maximum value of range. Inclusive.
     */
    int max;

    /**
     * Checks to see if an integer is within the range of min and max.
     * @param x Number to check
     * @return If the number is within this range (inclusive).
     */
    public boolean isInRange(int x) {
        return min <= x && x <= max;
    }

    /**
     * Constructs an {@link IntRange} where a number will only be in range if it is the exact same number.
     *
     * x == value
     * @param value Number to be accepted.
     * @return Constructed {@link IntRange}
     */
    public static IntRange of(int value) {
        return new IntRange(value, value);
    }

    /** Constructs an {@link IntRange} where a number will only be in range if it is within max and min.
     *
     * min <= x <= max
     * @param min Minimum number
     * @param max Maximum number
     * @return Constructed {@link IntRange}
     */
    public static IntRange of(int min, int max) {
        return new IntRange(min, max);
    }

    /**
     * Constructs an {@link IntRange} where it will only be in range if it is 0;
     *
     * x == 0
     * @return Constructed {@link IntRange}
     */
    public static IntRange none() {
        return new IntRange(0, 0);
    }

    /**
     * Constructs an {@link IntRange} where it will be in range if the variable is less than or equal to this number.
     *
     * 0 <= x <= max
     * @param max Maximum number of the range
     * @return Constructed {@link IntRange}
     */
    public static IntRange lessThanEqual(int max) {
        return new IntRange(0, max);
    }

    /**
     * Constructs an {@link IntRange} where it will be in range if the variable is greater than or equal to this number.
     *
     * min <= x
     * @param min Minimum number of this range.
     * @return Constructed {@link IntRange}
     */
    public static IntRange greaterThanEqual(int min) {
        return new IntRange(min, Integer.MAX_VALUE);
    }

    /**
     * Constructs an {@link IntRange} where it will be in range if the variable is any positive integer, including 0.
     *
     * x >= 0
     * @return Constructed {@link IntRange}
     */
    public static IntRange any() {
        return greaterThanEqual(0);
    }

}
