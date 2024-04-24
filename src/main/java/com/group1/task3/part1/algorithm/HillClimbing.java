package com.group1.task3.part1.algorithm;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

import com.group1.task3.part1.guioptimiser.SimpleAppLauncher;

public class HillClimbing {
    private static final Random r = new Random();
    private static SimpleAppLauncher sa = new SimpleAppLauncher();
    private static ArrayList<ArrayList<Integer>> currentSolution = new ArrayList<ArrayList<Integer>>();
    private static BufferedImage currentBestImage;

    public static ArrayList<ArrayList<Integer>> search(Function<ArrayList<ArrayList<Integer>>, Double> function, int iterations) {
        // RGB contains RGB values for each GUI component. Only 6 components needed
        currentSolution.add(new ArrayList<Integer>(Arrays.asList(new Integer[]{r.nextInt(256), r.nextInt(256), r.nextInt(256)})));
        currentSolution.add(new ArrayList<Integer>(Arrays.asList(new Integer[]{r.nextInt(256), r.nextInt(256), r.nextInt(256)})));
        currentSolution.add(new ArrayList<Integer>(Arrays.asList(new Integer[]{r.nextInt(256), r.nextInt(256), r.nextInt(256)})));
        currentSolution.add(new ArrayList<Integer>(Arrays.asList(new Integer[]{r.nextInt(256), r.nextInt(256), r.nextInt(256)})));
        currentSolution.add(new ArrayList<Integer>(Arrays.asList(new Integer[]{r.nextInt(256), r.nextInt(256), r.nextInt(256)})));
        currentSolution.add(new ArrayList<Integer>(Arrays.asList(new Integer[]{r.nextInt(256), r.nextInt(256), r.nextInt(256)})));
        // ArrayList<ArrayList<Integer>> currentSolution = lowerBound + r.nextInt(upperBound - lowerBound) + 1;
        double currentFitness = function.apply(currentSolution);
        System.out.println("Initial solution: " + currentSolution + "\nInitial fitness: " + currentFitness);
        sa.saveScreenShot(null, "initial.png");

        for (int i = 0; i < iterations; i++) {
            System.out.println("--------------------------------------------");
            Integer delta = (r.nextInt(2) == 0) ? -1 : 1; //delta is either -1 or 1
            System.out.println("Delta: " + delta);
            ArrayList<ArrayList<Integer>> newSolution = generateNewSolution(delta);
            double newFitness = function.apply(newSolution);
            System.out.println("New solution: " + newSolution + "\nNew fitness: " + newFitness);

            if (newFitness < currentFitness) {
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
            } else if (i==iterations-1) {
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
        return currentSolution;
    }

    public static ArrayList<ArrayList<Integer>> generateNewSolution(Integer delta) {
        ArrayList<ArrayList<Integer>> newSolution = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < currentSolution.size(); i++) {
            ArrayList<Integer> newRGB = new ArrayList<Integer>();
            for (int j = 0; j < currentSolution.get(i).size(); j++) {
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
        search(currentSolution -> sa.runApp(currentSolution), 10);

    }

}
