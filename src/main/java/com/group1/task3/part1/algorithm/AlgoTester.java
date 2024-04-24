package com.group1.task3.part1.algorithm;

public class AlgoTester {
    
    public static void main(String[] args) {
        // Test the HillClimbing algorithm
        // System.out.println("HillClimbing algorithm test:");
        // System.out.println("The minimum value of the function f(x) = x^2 - 4x + 1 is: " 
        // + HillClimbing.search(x -> Math.pow(x, 2) - 4 * x + 1, -100, 100, 1000));
        
        // Test the SimulatedAnnealing algorithm
        System.out.println("SimulatedAnnealing algorithm test:");
        System.out.println("The minimum value of the function f(x) = x^2 - 4x + 1 is: "
        + SimulatedAnnealing.search(x -> Math.pow(x, 2) - 4 * x + 1, -100, 100, 10000, 100, 0.001));
        // // Test the RandomSearch algorithm
        // System.out.println("RandomSearch algorithm test:");
        // System.out.println("The minimum value of the function f(x) = x^2 - 2x + 1 is: " 
        // + RandomSerach.search(x -> Math.pow(x, 2) - 2 * x + 1, -100, 100, 1000));
    }
}
