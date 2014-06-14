package io.rynmrtn.puzzles.actors;

import akka.actor.UntypedActor;
import io.rynmrtn.puzzles.primes.NumericRange;

import java.util.HashSet;
import java.util.Set;

/*
    get simple and start writing some code that can be executed. stop planning

    have the master call the prime number actor to do some stuff and build from there.
 */
public class PrimeNumberActor extends UntypedActor {

    private Set<Integer> primesFound;

    public PrimeNumberActor() {
        this.primesFound = new HashSet();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof NumericRange) {
            NumericRange range = (NumericRange) message;
            for(int i = range.getStartValue(); i < range.getCount(); i++) {
                if(isPrime(i)) {
                    primesFound.add(i);
                }
            }
            getSender().tell(primesFound, getSender());
        } else {
            unhandled(message);
        }
    }

    // Simple method to determine if a number is prime
    boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n%2==0) return false;
        //if not, then just check the odds
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }
}
