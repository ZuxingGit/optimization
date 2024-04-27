package com.group1.task3.part1.guioptimiser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @autuor: Zuxing
 */
public class EnergyConsumptionComputer {
    double numberOfNexusPixels = 3686400;
    
    // calculate charge consumption for one pixel
    public double calculateChargeConsumptionPerPixel(int red, int green, int blue) {
        // Define consumption rates for each color (in mA)
        // check tab "data_used" in screenOn.xlsx in task1 folder
        double redConsumptionRate = 130.7744809/numberOfNexusPixels; // Red pixel
        double greenConsumptionRate = 141.8636958/numberOfNexusPixels; // Green pixel
        double blueConsumptionRate = 241.2239456/numberOfNexusPixels; // Blue pixel

        // Calculate total charge consumption for the pixel based on color values
        double totalConsumption = (red * redConsumptionRate + green * greenConsumptionRate + blue * blueConsumptionRate) / 255.0;

        return totalConsumption;
    }

    // Function to load an image from file
    public BufferedImage loadImage(String imagePath) {
        BufferedImage img = null;
        File f = null;

        try {
            f = new File(imagePath);
            img = ImageIO.read(f);
            return img;

        } catch (IOException e) {
            // Handle IO exceptions
            System.out.println(e);
            return null;
        }
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
