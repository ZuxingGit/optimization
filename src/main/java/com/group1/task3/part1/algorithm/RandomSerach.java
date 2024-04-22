package com.group1.task3.part1.algorithm;

/**
 * Random search algorithm to find the best colour settings for energy consumption optimisation.
 * @author Zuxing
 */
public class RandomSerach {
    private String bestColourSettings;
    private double bestEnergyConsumption;

    public RandomSerach() {
        bestColourSettings = "";
        bestEnergyConsumption = Double.MAX_VALUE;
    }

    public boolean search(double newEnergyConsumption) {
        // Compare the new energy consumption with the best energy consumption
        if (newEnergyConsumption < bestEnergyConsumption) {
            return true;
        } else {
            return false;
        }
    }

    public void setBestColourSettings(String bestColourSettings) {
        this.bestColourSettings = bestColourSettings;
    }

    public void setBestEnergyConsumption(double bestEnergyConsumption) {
        this.bestEnergyConsumption = bestEnergyConsumption;
    }

    public String getBestColourSettings() {
        return bestColourSettings;
    }

    public double getBestEnergyConsumption() {
        return bestEnergyConsumption;
    }
}
