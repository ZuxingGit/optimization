// package com.group1.task3.part1.algorithm;

// import java.util.Random;
// import java.util.function.Function;

// public class SimulatedAnnealing {
//     private static final Random r = new Random();

//     public static double search(Function<Double, Double> function, double lowerBound, double upperBound, int iterations, double initialTemp, double coolingRate) {
//         double currentSolution = lowerBound + r.nextDouble() * (upperBound - lowerBound);
//         double currentFitness = function.apply(currentSolution);

//         double temperature = initialTemp;

//         for (int i = 0; i < iterations; i++) {
//             double newSolution = currentSolution + (r.nextDouble() - 0.5);
//             double newFitness = function.apply(newSolution);

//             if (shouldAccept(currentFitness, newFitness, temperature)) {
//                 currentSolution = newSolution;
//                 currentFitness = newFitness;
//             }

//             temperature *= 1 - coolingRate;
//         }

//         return currentSolution;
//     }

//     private static boolean shouldAccept(double currentFitness, double newFitness, double temperature) {
//         if (newFitness < currentFitness) {
//             return true;
//         }
//         double delta = newFitness - currentFitness;
//         return Math.exp(-delta / temperature) > r.nextDouble();
//     }
// }

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

    public static ArrayList<ArrayList<Integer>> simpleAppSearch(Function<ArrayList<ArrayList<Integer>>, Double> function, int iterations) {
        ArrayList<ArrayList<Integer>> currentSolution = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> bestSolution = new ArrayList<ArrayList<Integer>>();
        // currentSolution contains RGB values for each simpleApp GUI component.
        // Only 6 components needed
        for (int i=0;i<6;i++){
            currentSolution.add(new ArrayList<Integer>(Arrays.asList(new Integer[]{r.nextInt(256), r.nextInt(256), r.nextInt(256)})));
        }
        // ArrayList<ArrayList<Integer>> currentSolution = lowerBound + r.nextInt(upperBound - lowerBound) + 1;
        double currentFitness = function.apply(currentSolution);
        double bestFitness = currentFitness;
        System.out.println("Initial solution: " + currentSolution + "\nInitial fitness: " + currentFitness);
        sa.saveScreenShot(null, "initial.png");

        double temperature = 10000.0;
        double coolingRate = 0.003;

        for (int i = 0; i < iterations; i++) {
            if (temperature > 1.0) {
                System.out.println("--------------------------------------------");
                int delta = r.nextInt(51) - 25;//delta in range (-25~25)
                System.out.println("Delta: " + delta);
                ArrayList<ArrayList<Integer>> newSolution = generateNewSolution(delta, currentSolution);
                double newFitness = function.apply(newSolution);
                System.out.println("New solution: " + newSolution + "\nNew fitness: " + newFitness);

                if (newFitness < currentFitness || Math.exp(bestFitness - newFitness) / temperature > r.nextDouble()) {
                    currentSolution = newSolution;
                    currentFitness = newFitness;
                    currentBestImage = sa.imageContent;
                    if (currentFitness< bestFitness) {
                        bestSolution = currentSolution;
                        bestFitness = currentFitness;
                    }
                }
                temperature *= 1- coolingRate;
                System.out.println("Current solution: " + currentSolution + "\nCurrent fitness: " + currentFitness);

                if (i == 9) {
                    sa.saveScreenShot(currentBestImage, "10.png");
                    System.out.println("============================================");
                    System.out.println("10th solution: " + currentSolution + "\n10th fitness: " + currentFitness);
                } else if (i == 99) {
                    sa.saveScreenShot(currentBestImage, "100.png");
                    System.out.println("============================================");
                    System.out.println("100th solution: " + currentSolution + "\n100th fitness: " + currentFitness);
                } else if (i == iterations - 1) {
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

            }
        }
        return bestSolution;
    }


    public static ArrayList<ArrayList<Integer>> caculatorSearch(Function<ArrayList<ArrayList<Integer>>, Double> function, int iterations) {
        ArrayList<ArrayList<Integer>> currentSolution = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> bestSolution = new ArrayList<ArrayList<Integer>>();
        // currentSolution contains RGB values for each caculator GUI component.
        // All 27 components needed
        for (int i=0;i<27;i++){
            currentSolution.add(new ArrayList<Integer>(Arrays.asList(new Integer[]{r.nextInt(256), r.nextInt(256), r.nextInt(256)})));
        }

        // ArrayList<ArrayList<Integer>> currentSolution = lowerBound + r.nextInt(upperBound - lowerBound) + 1;
        double currentFitness = function.apply(currentSolution);
        double bestFitness = currentFitness;
        System.out.println("Initial solution: " + currentSolution + "\nInitial fitness: " + currentFitness);
        ca.saveScreenShot(null, "initial.png");

        double temperature = 10000.0;
        double coolingRate = 0.003;

        for (int i = 0; i < iterations; i++) {
            if (temperature > 1.0) {
                System.out.println("--------------------------------------------");
                int delta = r.nextInt(51) - 25;//delta in range (-25~25)
                System.out.println("Delta: " + delta);
                ArrayList<ArrayList<Integer>> newSolution = generateNewSolution(delta, currentSolution);
                double newFitness = function.apply(newSolution);
                System.out.println("New solution: " + newSolution + "\nNew fitness: " + newFitness);

                if (newFitness < currentFitness || Math.exp(bestFitness - newFitness) / temperature > r.nextDouble()) {
                    currentSolution = newSolution;
                    currentFitness = newFitness;
                    currentBestImage = sa.imageContent;
                    if (currentFitness < bestFitness) {
                        bestSolution = currentSolution;
                        bestFitness = currentFitness;
                    }
                }
                temperature *= 1 - coolingRate;
                System.out.println("Current solution: " + currentSolution + "\nCurrent fitness: " + currentFitness);

                if (i == 9) {
                    ca.saveScreenShot(currentBestImage, "10.png");
                    System.out.println("============================================");
                    System.out.println("10th solution: " + currentSolution + "\n10th fitness: " + currentFitness);
                } else if (i == 99) {
                    ca.saveScreenShot(currentBestImage, "100.png");
                    System.out.println("============================================");
                    System.out.println("100th solution: " + currentSolution + "\n100th fitness: " + currentFitness);
                } else if (i == iterations - 1) {
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
            }
        }
        return bestSolution;
    }

    /**
     * Generate new solution based on the current solution and delta

     */
    public static ArrayList<ArrayList<Integer>> generateNewSolution(int delta, ArrayList<ArrayList<Integer>> currentSolution) {
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

    public static void main(String[] args) {
        simpleAppSearch(currentSolution -> sa.runApp(currentSolution), 10);
        // caculatorSearch(currentSolution -> ca.runApp(currentSolution), 10);

    }

}
