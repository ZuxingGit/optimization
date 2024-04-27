package com.group1.task3.part1.algorithm;

import java.awt.image.BufferedImage;
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

    /**
	 * Calculate the Euclidean distance between two solutions
	 * @param referenceImage
	 * @param currentImage
	 * @return the sum of Euclidean colour differences for each pixel of two images
	 */
	public static double getEuclideanDistanceSum(BufferedImage refImage, BufferedImage currentImage) {
		double distance = 0;
		int height = refImage.getHeight();
		int width = refImage.getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int refRGB = refImage.getRGB(x, y);
				int currentRGB = currentImage.getRGB(x, y);
				int refRed = (refRGB >> 16) & 0xff;
				int refGreen = (refRGB >> 8) & 0xff;
				int refBlue = refRGB & 0xff;
				int currentRed = (currentRGB >> 16) & 0xff;
				int currentGreen = (currentRGB >> 8) & 0xff;
				int currentBlue = currentRGB & 0xff;
				double d = Math.pow(refRed - currentRed, 2) + Math.pow(refGreen - currentGreen, 2) + Math.pow(refBlue - currentBlue, 2);
				distance += Math.sqrt(d);
			}
		}
		System.out.println("Total Euclidean distance: " + distance);

		return distance;
	}
}
