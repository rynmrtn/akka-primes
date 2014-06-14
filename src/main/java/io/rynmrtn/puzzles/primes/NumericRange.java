package io.rynmrtn.puzzles.primes;

public class NumericRange {

    private final int startValue;
    private final int count;

    public NumericRange(int startValue, int count) {
        this.startValue = startValue;
        this.count = count;
    }

    public int getStartValue() {
        return startValue;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.format("%d - %d", getStartValue(), getCount());
    }
}
