import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    // Define the function to calculate charge consumption for each pixel
    public static double calculateChargeConsumptionPerPixel(int red, int green, int blue) {
        // Define consumption rates for each color (in mA)
        double redConsumptionRate = 120; // Red LED
        double greenConsumptionRate = 140; // Green LED
        double blueConsumptionRate = 240; // Blue LED
        
        // Calculate total charge consumption for the pixel based on color values
        double totalConsumption = (red * redConsumptionRate + green * greenConsumptionRate + blue * blueConsumptionRate)/ 255.0;

        return totalConsumption;
    }

    // Function to load an image from file
    public static BufferedImage loadImage(String imagePath) {
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

    public static void main(String[] args) {
        // Load the image file
        String imagePath = args[0]; // Replace this with the path to your image
        BufferedImage image = loadImage(imagePath);

        // Calculate the total charge consumption for the entire image
        double totalChargeConsumption = 0;
        int width = image.getWidth();
        int height = image.getHeight();

        // Iterate over each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Extract the RGB color components
                int p = image.getRGB(x, y);
                int red = (p >> 16) & 0xff;
                int green = (p >> 8) & 0xff;
                int blue = p & 0xff;

                // Calculate the charge consumption for the pixel
                double pixelChargeConsumption = calculateChargeConsumptionPerPixel(red, green, blue);
                totalChargeConsumption += pixelChargeConsumption;

            }
        }

        // Output
        System.out.println(totalChargeConsumption);

    }
}
