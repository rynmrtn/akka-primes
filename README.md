# Task Distribution with Primes

## Problem Statement

Create a Java or Scala program which calculates prime numbers. The program will accept a command line argument N and print all prime numbers from 1 to N. The primes need not be printed in any particular order, but each prime must be printed exactly once.

To distribute this task, research message passing systems in Java or Scala and write a simple system which uses message passing from a dealer to two or more workers in order to calculate the primes. In order to facilitate parallel distribution, use a simple prime number checking algorithm which inspects each integer separately and prints the integer if it is prime.

## Solution

### Some Decisions

[Akka](http://akka.io) is a powerful message passing framework that abstracts the logistics of concurrent programming and allows you to focus on the particulars of the solution that needs to be provided. 

I decided to implement the project in Java so that I could focus on solving the problem rather than re-familiarizing myself with Scala.

### How it works

This `PrimeNumberApplication` accepts an input value `N` (not trusted) and uses the input as the upper-bound for the determination of all primes between 1 and `N`. The `MasterActor` then orchestrates the work by routing processing requests to `PrimeNumberActor`.

The `PrimeNumberActor` will then determine if a number is prime and pass a message back to the sender that the value is a `PrimeNumber` or `NonPrimeNumber`. The sender, `MasterActor`, then processes this and stores the prime numbers in a `Set` so that no numbers are duplicated.

When processing is complete (all numbers between 1 and `N` are processed), the `MasterActor` will send a message to the `ListeningActor` with a `Set` of `PrimeNumber` objects. 

The `PrimeNumberApplication` is currently hard-coded to route messages to 100 `PrimeNumberActor`s.