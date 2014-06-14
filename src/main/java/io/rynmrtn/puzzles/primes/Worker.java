package io.rynmrtn.puzzles.primes;

import akka.actor.UntypedActor;

public class Worker extends UntypedActor {

    // Handles the a message that gets passed
    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Work) {


            // Inform the sender of the values
            getSender().tell(null, getSelf());
        } else {
            // Out of scope for now
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
