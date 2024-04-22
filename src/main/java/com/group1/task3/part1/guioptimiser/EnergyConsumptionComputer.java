package com.group1.task3.part1.guioptimiser;

import java.awt.image.BufferedImage;

/**
 * @autuor: Zuxing
 */
public class EnergyConsumptionComputer {
    
    // calculate charge consumption for one pixel
    public double calculateChargeConsumptionPerPixel(int red, int green, int blue) {
        // Define consumption rates for each color (in mA)
        // check tab "data_used" in screenOn.xlsx in task1 folder
        double redConsumptionRate = 130.7744809; // Red pixel
        double greenConsumptionRate = 141.8636958; // Green pixel
        double blueConsumptionRate = 241.2239456; // Blue pixel

        // Calculate total charge consumption for the pixel based on color values
        double totalConsumption = (red * redConsumptionRate + green * greenConsumptionRate + blue * blueConsumptionRate) / 255.0;

        return totalConsumption;
    }

    /**
     * Calculate the total charge consumption for the entire image
     * @param img
     * @return total charge consumption
     * @author Zuxing, Chung
     */
    public double calculateTotalChargeConsumption(BufferedImage img) {
        double totalChargeConsumption = 0;
        int width = img.getWidth();
        int height = img.getHeight();

        // Iterate over each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Extract the RGB color components
                int p = img.getRGB(x, y);
                int red = (p >> 16) & 0xff;
                int green = (p >> 8) & 0xff;
                int blue = p & 0xff;

                // Calculate the charge consumption for the pixel
                double pixelChargeConsumption = calculateChargeConsumptionPerPixel(red, green, blue);
                totalChargeConsumption += pixelChargeConsumption;
            }
        }

        return totalChargeConsumption;
    }
}
