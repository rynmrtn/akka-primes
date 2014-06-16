package io.rynmrtn.puzzles.actors;

import akka.actor.UntypedActor;
import io.rynmrtn.puzzles.primes.NonPrimeNumber;
import io.rynmrtn.puzzles.primes.PrimeNumber;

public class PrimeNumberActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Integer) {
            Integer i = (Integer) message;

            if(isPrime(i)) {
                getSender().tell(new PrimeNumber(i), getSelf());
            } else {
                getSender().tell(new NonPrimeNumber(), getSelf());
            }
        } else {
            unhandled(message);
        }
    }

    // Simple method from the internet to determine if a number is prime
    boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n != 2 && n%2==0) return false;
        //if not, then just check the odds
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }
}
