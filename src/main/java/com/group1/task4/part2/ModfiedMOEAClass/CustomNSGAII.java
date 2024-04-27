package com.group1.task4.part2.ModfiedMOEAClass;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;

public class CustomNSGAII extends NSGAII {
    private Population initialPopulation;

    public CustomNSGAII(Problem problem) {
        super(problem);
    }

    @Override
    public void initialize() {
        super.initialize();
        initialPopulation = new Population(getPopulation());
    }

    public Population getInitialPopulation() {
        return initialPopulation;
    }
}
