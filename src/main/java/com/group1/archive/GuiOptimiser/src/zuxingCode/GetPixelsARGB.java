package zuxingCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.PathMatcher;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: Zuxing
 * @source: https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
 * @assistant: Copilot
 */
public class GetPixelsARGB {

    public Pixel[][] getPixalArray() {
        BufferedImage img = null;
        // File f = null;
        // define the pattern using the * wildcard
        String pattern = "image-*.png";
        // Create a PathMatcher for the specified pattern
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        // specify the path to the directory for searching
        Path dir = Paths.get("./GuiOptimiser/screenshots");

        // read image
        try {
            // Use AtomicReference to allow modifications inside lambda
            AtomicReference<File> f = new AtomicReference<>();
            Path matchedFile = Files.walk(dir)
                .filter(Files::isRegularFile)
                .filter(path -> matcher.matches(path.getFileName()))
                .findFirst()
                .orElse(null);
            if(matchedFile != null)
                f.set(matchedFile.toFile());
            else {
                System.out.println("No file found");
                return null;
            }
            img = ImageIO.read(f.get());
        } catch (IOException e) {
            System.out.println(e);
        }

        int width = img.getWidth();
        int height = img.getHeight();
        System.out.println("Width: " + width + " Height: " + height);

        Pixel[][] pixels = new Pixel[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // get pixel value
                int p = img.getRGB(x, y);
                pixels[x][y] = new Pixel(p);
            }
        }
        return pixels;
    }

}
