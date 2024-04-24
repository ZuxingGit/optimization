package com.group1.task3.part1.algorithm;

import java.util.ArrayList;

/**
 * @author Zuxing
 */
public class Euclidean {
    
    /**
     * Calculate the Euclidean distance between two colors
     * @param jTextField1
     * @param jTextField1TextColor
     * @return the Euclidean distance between the two colors
     * @author Xingyuan, Zuxing
     */
    public static double getDistance(ArrayList<Integer> jTextField1, ArrayList<Integer> jTextField1TextColor) {
        int redDistance = jTextField1.get(0) - jTextField1TextColor.get(0);
        int greenDistance = jTextField1.get(1) - jTextField1TextColor.get(1);
        int blueDistance = jTextField1.get(2) - jTextField1TextColor.get(2);

        return Math.sqrt(redDistance * redDistance + greenDistance * greenDistance + blueDistance * blueDistance);
    }
}
