package io.rynmrtn.puzzles.primes;

import java.util.Set;

public class PrimeNumber {
    private final Integer number;

    public PrimeNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
