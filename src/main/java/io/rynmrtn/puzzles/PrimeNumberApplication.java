package io.rynmrtn.puzzles;

import akka.actor.*;
import io.rynmrtn.puzzles.actors.ListeningActor;
import io.rynmrtn.puzzles.actors.MasterActor;
import io.rynmrtn.puzzles.actors.PrimeNumberActor;
import io.rynmrtn.puzzles.primes.PrimeNumberInput;

import java.text.NumberFormat;

public class PrimeNumberApplication {



    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create("PrimeNumberApp");

        final ActorRef primeNumberActor = system.actorOf(Props.create(PrimeNumberActor.class), "primeNumber");
        final ActorRef listeningActor = system.actorOf(Props.create(ListeningActor.class), "listener");

        ActorRef master = system.actorOf(Props.create(new UntypedActorFactory() {
            @Override
            public Actor create() throws Exception {
                return new MasterActor(primeNumberActor, listeningActor);
            }
        }), "master");

        PrimeNumberInput input = parseInput(args);

        System.out.println(String.format("Now printing prime numbers from 1 to %d", input.getInput()));
        master.tell(input, listeningActor);

        system.shutdown();
    }

    private static PrimeNumberInput parseInput(String[] untrustedInput) {
        if(untrustedInput.length >= 1) {
            // Take the first value
            try {
                Integer primesUpTo = Integer.parseInt(untrustedInput[0]);
                return new PrimeNumberInput(primesUpTo);

            } catch(NumberFormatException formatExeption) {
                System.out.println(String.format("Prime numbers cannot be calculated for %s", untrustedInput[0]));
            }
        } else {
            System.out.println("You must enter a numerical argument");
        }
        throw new RuntimeException("A numerical argument is required to calculated prime numbers");
    }
}
