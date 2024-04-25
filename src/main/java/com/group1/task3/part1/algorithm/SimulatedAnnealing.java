package com.group1.task3.part1.algorithm;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

import com.group1.task3.part1.guioptimiser.CalculatorLauncher;
import com.group1.task3.part1.guioptimiser.SimpleAppLauncher;

public class SimulatedAnnealing {
    private static final Random r = new Random();
    private static SimpleAppLauncher sa = new SimpleAppLauncher();
    private static CalculatorLauncher ca = new CalculatorLauncher();
    // private static ArrayList<ArrayList<Integer>> currentSolution = new ArrayList<ArrayList<Integer>>();
    private static BufferedImage currentBestImage;

    public static ArrayList<ArrayList<Integer>> simpleAppSearch(Function<ArrayList<ArrayList<Integer>>, Double> function, 
                                                int iterations, double initialTemperature, double coolingRate) {
        ArrayList<ArrayList<Integer>> currentSolution = new ArrayList<ArrayList<Integer>>();
        double temperature = initialTemperature;
        // currentSolution contains RGB values for each simpleApp GUI component. 
        // Only 6 components needed
        for (int i = 0; i < 6; i++) {
            currentSolution.add(new ArrayList<Integer>(Arrays.asList(new Integer[]{r.nextInt(256), r.nextInt(256), r.nextInt(256)})));
            // 2. jTextField1 (start from 0)
            // 3. jTextField1TextColor (start from 0)
        }

        double currentFitness = function.apply(currentSolution);
        System.out.println("Initial solution: " + currentSolution + "\nInitial fitness: " + currentFitness);
        sa.saveScreenShot(null, "initial.png");

        for (int i = 0; i < iterations; i++) {
            System.out.println("--------------------------------------------");
            Integer value = r.nextInt(25) + 1; //value is between 1 and 25
            Integer sign = (r.nextInt(2) == 0) ? -1 : 1; //delta is either -1 or 1
            Integer delta = sign * value; //delta is between -25 and 25, 0 excluded
            System.out.println("Delta: " + delta);
            ArrayList<ArrayList<Integer>> newSolution = generateNewSolution(delta, currentSolution);
            if (Euclidean.getDistance(newSolution.get(2), newSolution.get(3)) < 128) {
                System.out.println("Distance between jTextField1 and jTextField1TextColor is less than 128");
                // so that background color is black, the distance of text color between bg color is 128
                newSolution.set(2, new ArrayList<Integer>(Arrays.asList(0, 0, 0)));
                newSolution.set(3, new ArrayList<Integer>(Arrays.asList(74, 74, 74)));
            }
            double newFitness = function.apply(newSolution);
            System.out.println("New solution: " + newSolution + "\nNew fitness: " + newFitness);
            // accept it or not
            if (shouldAccept(currentFitness, newFitness, temperature)) {
                currentSolution = newSolution;
                currentFitness = newFitness;
                currentBestImage = sa.imageContent;
            }
            System.out.println("Current solution: " + currentSolution + "\nCurrent fitness: " + currentFitness);

            if (i == 9) {
                sa.saveScreenShot(currentBestImage, "10.png");
                System.out.println("============================================");
                System.out.println("10th solution: " + currentSolution + "\n10th fitness: " + currentFitness);
            } else if (i == 99) {
                sa.saveScreenShot(currentBestImage, "100.png");
                System.out.println("============================================");
                System.out.println("100th solution: " + currentSolution + "\n100th fitness: " + currentFitness);
            } else if (i == iterations-1) {
                if (i == 999) {
                    sa.saveScreenShot(currentBestImage, "1000.png");
                    System.out.println("============================================");
                    System.out.println("1000th solution: " + currentSolution + "\n1000th fitness: " + currentFitness);
                } else {
                    sa.saveScreenShot(currentBestImage, "final.png");
                    System.out.println("============================================");
                    System.out.println("Final solution: " + currentSolution + "\nFinal fitness: " + currentFitness);
                }
            }

            temperature *= 1 - coolingRate;
        }
        return currentSolution;
    }

    public static ArrayList<ArrayList<Integer>> calculatorSearch(Function<ArrayList<ArrayList<Integer>>, Double> function, 
                                                int iterations, double initialTemperature, double coolingRate) {
        ArrayList<ArrayList<Integer>> currentSolution = new ArrayList<ArrayList<Integer>>();
        double temperature = initialTemperature;
        // currentSolution contains RGB values for each caculator GUI component. 
        // All 27 components needed
        for (int i = 0; i < 27; i++) {
            currentSolution.add(new ArrayList<Integer>(Arrays.asList(new Integer[]{r.nextInt(256), r.nextInt(256), r.nextInt(256)})));
            // 19. jTextField1 (start from 0)
            // 20. jTextField1TextColor (start from 0)
        }

        double currentFitness = function.apply(currentSolution);
        System.out.println("Initial solution: " + currentSolution + "\nInitial fitness: " + currentFitness);
        ca.saveScreenShot(null, "initial.png");

        for (int i = 0; i < iterations; i++) {
            System.out.println("--------------------------------------------");
            Integer value = r.nextInt(25) + 1; //value is between 1 and 25
            Integer sign = (r.nextInt(2) == 0) ? -1 : 1; //delta is either -1 or 1
            Integer delta = sign * value; //delta is between -25 and 25, 0 excluded
            System.out.println("Delta: " + delta);
            ArrayList<ArrayList<Integer>> newSolution = generateNewSolution(delta, currentSolution);
            if (Euclidean.getDistance(newSolution.get(19), newSolution.get(20)) < 128) {
                System.out.println("Distance between jTextField1 and jTextField1TextColor is less than 128");
                // so that background color is black, the distance of text color between bg color is 128
                newSolution.set(19, new ArrayList<Integer>(Arrays.asList(0, 0, 0)));
                newSolution.set(20, new ArrayList<Integer>(Arrays.asList(74, 74, 74)));
            }
            double newFitness = function.apply(newSolution);
            System.out.println("New solution: " + newSolution + "\nNew fitness: " + newFitness);
            // accept it or not
            if (shouldAccept(currentFitness, newFitness, temperature)) {
                currentSolution = newSolution;
                currentFitness = newFitness;
                currentBestImage = ca.imageContent;
            }

            System.out.println("Current solution: " + currentSolution + "\nCurrent fitness: " + currentFitness);

            if (i == 9) {
                ca.saveScreenShot(currentBestImage, "10.png");
                System.out.println("============================================");
                System.out.println("10th solution: " + currentSolution + "\n10th fitness: " + currentFitness);
            } else if (i == 99) {
                ca.saveScreenShot(currentBestImage, "100.png");
                System.out.println("============================================");
                System.out.println("100th solution: " + currentSolution + "\n100th fitness: " + currentFitness);
            } else if (i == iterations-1) {
                if (i == 999) {
                    ca.saveScreenShot(currentBestImage, "1000.png");
                    System.out.println("============================================");
                    System.out.println("1000th solution: " + currentSolution + "\n1000th fitness: " + currentFitness);
                } else {
                    ca.saveScreenShot(currentBestImage, "final.png");
                    System.out.println("============================================");
                    System.out.println("Final solution: " + currentSolution + "\nFinal fitness: " + currentFitness);
                }
            }
            temperature *= 1 - coolingRate;
        }

        return currentSolution;
    }

    /**
     * Generate new solution based on the current solution and delta
     * @param delta
     * @param currentSolution
     * @return new solution
     */
    public static ArrayList<ArrayList<Integer>> generateNewSolution(Integer delta, ArrayList<ArrayList<Integer>> currentSolution) {
        ArrayList<ArrayList<Integer>> newSolution = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < currentSolution.size(); i++) {
            ArrayList<Integer> newRGB = new ArrayList<Integer>();
            for (int j = 0; j < 3; j++) {
                int newRGBValue = currentSolution.get(i).get(j) + delta;
                if (newRGBValue < 0) {
                    newRGBValue = 0;
                } else if (newRGBValue > 255) {
                    newRGBValue = 255;
                }
                newRGB.add(newRGBValue);
            }
            newSolution.add(newRGB);
        }
        return newSolution;
    }

    /**
     * Check if the new solution should be accepted
     * @param currentFitness
     * @param newFitness
     * @param temperature
     * @return true if the new solution should be accepted, false otherwise
     */
    private static boolean shouldAccept(double currentFitness, double newFitness, double temperature) {
        if (newFitness < currentFitness) {
            return true;
        }
        double delta = newFitness - currentFitness;
        return Math.exp(-delta / temperature) > r.nextDouble();
    }

    public static void main(String[] args) {
        double temperature = 1000;
        double coolingRate = 0.003;

        simpleAppSearch(currentSolution -> sa.runApp(currentSolution), 10, temperature, coolingRate);
        System.out.println("###############################################\n");
        calculatorSearch(currentSolution -> ca.runApp(currentSolution), 10, temperature, coolingRate);
    }
}
