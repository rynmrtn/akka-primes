package io.rynmrtn.puzzles.primes;

public class Work {

    // Declare as final for concurrency reasons
    private final int startingValue;
    private final int numElements;

    public Work(int startingValue, int numElements) {
        this.startingValue = startingValue;
        this.numElements = numElements;
    }

    public int getStartingValue() {
        return this.startingValue;
    }

    public int getNumElements() {
        return this.numElements;
    }

}
