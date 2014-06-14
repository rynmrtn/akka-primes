package io.rynmrtn.puzzles.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import io.rynmrtn.puzzles.primes.NumericRange;
import io.rynmrtn.puzzles.primes.PrimeNumberInput;

import java.util.HashSet;
import java.util.Set;

public class MasterActor extends UntypedActor {

    private ActorRef primeNumberActor;
    private ActorRef listeningActor;

    public MasterActor(ActorRef primeNumberActor, ActorRef listeningActor) {
        this.primeNumberActor = primeNumberActor;
        this.listeningActor = listeningActor;
    }

    /*
        When the master actor receives an initial set of work, it
        needs to distribute that work to other actors based upon
        the type of work it is. In this case, the work is
        distributing the calculation
     */
    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof PrimeNumberInput) {
            NumericRange range = new NumericRange(1, ((PrimeNumberInput) message).getInput());
            primeNumberActor.tell(range, getSelf());
        } else if (message instanceof HashSet) {
            listeningActor.tell(message, getSelf());
        } else {
            unhandled(message);
        }
    }
}
