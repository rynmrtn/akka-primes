package io.rynmrtn.puzzles.actors;

import akka.actor.UntypedActor;

import java.util.HashSet;

public class ListeningActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof HashSet) {
            HashSet<Integer> primeNumbers = (HashSet<Integer>) message;
            System.out.println(String.format("%d prime numbers found in range; here they are: %s", primeNumbers.size(), primeNumbers.toString()));
        } else {
            unhandled(message);
        }
    }
}
