package com.group1.task3.part1.algorithm;

import java.util.Random;
import java.util.function.Function;

public class SimulatedAnnealing {
    private static final Random r = new Random();

    public static double search(Function<Double, Double> function, double lowerBound, double upperBound, int iterations, double initialTemp, double coolingRate) {
        double currentSolution = lowerBound + r.nextDouble() * (upperBound - lowerBound);
        double currentFitness = function.apply(currentSolution);

        double temperature = initialTemp;

        for (int i = 0; i < iterations; i++) {
            double newSolution = currentSolution + (r.nextDouble() - 0.5);
            double newFitness = function.apply(newSolution);

            if (shouldAccept(currentFitness, newFitness, temperature)) {
                currentSolution = newSolution;
                currentFitness = newFitness;
            }

            temperature *= 1 - coolingRate;
        }

        return currentSolution;
    }

    private static boolean shouldAccept(double currentFitness, double newFitness, double temperature) {
        if (newFitness < currentFitness) {
            return true;
        }
        double delta = newFitness - currentFitness;
        return Math.exp(-delta / temperature) > r.nextDouble();
    }
}