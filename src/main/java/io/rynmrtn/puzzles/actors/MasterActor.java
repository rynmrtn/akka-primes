package io.rynmrtn.puzzles.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import akka.event.Logging;
import akka.routing.RoundRobinPool;
import akka.routing.RoundRobinRouter;
import io.rynmrtn.puzzles.primes.NonPrimeNumber;
import io.rynmrtn.puzzles.primes.PrimeNumber;
import io.rynmrtn.puzzles.primes.UserInput;

import java.util.HashSet;

public class MasterActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorRef primeNumberActor;
    private ActorRef listeningActor;
    private Integer originalInput;
    private Integer messageCount;

    private HashSet<PrimeNumber> primeNumbers;

    public MasterActor(ActorRef listeningActor) {
        this.messageCount = 1;
        this.primeNumbers = new HashSet<PrimeNumber>();
        this.listeningActor = listeningActor;
//        this.primeNumberActor = getContext().actorOf(Props.create(PrimeNumberActor.class).withRouter(new RoundRobinRouter(5)),"primeActor");
        this.primeNumberActor = getContext().actorOf(Props.create(PrimeNumberActor.class), "primeActor");
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof PrimeNumber) {
            messageCount += 1;

            // add it to the collection of prime numbers
            primeNumbers.add((PrimeNumber) message);

            // if we have processed all primes, notify the listener based on some condition
            if(originalInput.equals(messageCount))
                listeningActor.tell(this.primeNumbers, getSelf());
        } else if (message instanceof NonPrimeNumber) {
            messageCount += 1;

            if(originalInput.equals(messageCount))
                listeningActor.tell(this.primeNumbers, getSelf());
        } else if (message instanceof UserInput) {

            this.originalInput = ((UserInput) message).getInput();

            // This queues up and sends/queues all of the messages and informs that the prime calculations are complete when they may
            // or may not have been performed yet.

            // How do i encapsulate the fact that "this is the last prime number to be calculated?"
            for(Integer i = 1; i < this.originalInput; i++) {
                primeNumberActor.tell(i, getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}
