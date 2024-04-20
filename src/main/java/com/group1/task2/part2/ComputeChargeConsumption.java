package com.group1.task2.part2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Chung, Zuxing
 */
public class ComputeChargeConsumption {
    private static String parentDir = "";

    // Define the function to calculate charge consumption for one pixel
    public static double calculateChargeConsumptionPerPixel(int red, int green, int blue) {
        // Define consumption rates for each color (in mA)
        // check tab "data_used" in screenOn.xlsx in task1 folder
        double redConsumptionRate = 130.7744809; // Red LED
        double greenConsumptionRate = 141.8636958; // Green LED
        double blueConsumptionRate = 241.2239456; // Blue LED

        // Calculate total charge consumption for the pixel based on color values
        double totalConsumption = (red * redConsumptionRate + green * greenConsumptionRate + blue * blueConsumptionRate) / 255.0;

        return totalConsumption;
    }

    // Function to load an image from file
    public static BufferedImage loadImage(String imagePath) {
        BufferedImage img = null;
        File f = null;
        parentDir = getParentDir();
        // System.out.println(parentDir);
        try {
            f = new File("src/main/java/com/group1/task2/part2/" + imagePath);
            img = ImageIO.read(f);
            return img;

        } catch (IOException e) {
            // Handle IO exceptions
            System.out.println(e);
            return null;
        }
    }

    public static String getParentDir() {
        String dir = "";
        try {
            File temp = new File("temp");
            dir = temp.getAbsolutePath().replace("temp", "");
            //System.out.println(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;
    }

    /**
     * Calculate the total charge consumption for the entire image
     * @param img
     * @return total charge consumption
     * @author Zuxing
     */
    public static double calculateTotalChargeConsumption(BufferedImage img) {
        double totalChargeConsumption = 0;
        int width = img.getWidth();
        int height = img.getHeight();
        System.out.println("Number of pixels: " + width * height);

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

    public static void main(String[] args) {
        String[] images = {"example1.png", "example2.png", "example3.png"};
        for (String imagePath : images) {
            System.out.println("===== Processing image: " + imagePath + " =====");
            BufferedImage image = loadImage(imagePath);
            double totalChargeConsumption = calculateTotalChargeConsumption(image);
            System.out.println("Total charge consumption for " + imagePath + ": " + totalChargeConsumption);
        }
    }
}
