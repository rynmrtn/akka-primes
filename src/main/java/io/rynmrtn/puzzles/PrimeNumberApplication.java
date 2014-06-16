package io.rynmrtn.puzzles;

import akka.actor.*;
import io.rynmrtn.puzzles.actors.ListeningActor;
import io.rynmrtn.puzzles.actors.MasterActor;
import io.rynmrtn.puzzles.primes.UserInput;

public class PrimeNumberApplication {



    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create("PrimeNumberApp");

        final ActorRef listeningActor = system.actorOf(new Props(ListeningActor.class), "listener");

        ActorRef master = system.actorOf(new Props(new UntypedActorFactory() {
            @Override
            public Actor create() throws Exception {
                return new MasterActor(listeningActor);
            }
        }), "master");

        UserInput input = parseInput(args);

        System.out.println(String.format("Now printing prime numbers from 1 to %d", input.getInput()));
        master.tell(input, listeningActor);

        system.awaitTermination();
        system.shutdown();
    }

    private static UserInput parseInput(String[] untrustedInput) {
        if(untrustedInput.length >= 1) {
            // Take the first value
            try {
                Integer primesUpTo = Integer.parseInt(untrustedInput[0]);
                return new UserInput(primesUpTo);

            } catch(NumberFormatException formatExeption) {
                System.out.println(String.format("Prime numbers cannot be calculated for %s", untrustedInput[0]));
            }
        } else {
            System.out.println("You must enter a numerical argument");
        }
        throw new RuntimeException("A numerical argument is required to calculated prime numbers");
    }
}
