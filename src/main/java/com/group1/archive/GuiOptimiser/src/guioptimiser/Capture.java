/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guioptimiser;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Mahmoud-Uni
 */
public class Capture {
    public String takeScreenShoot()
    {
        String fileName = "";
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            fileName = "image-"+System.currentTimeMillis()+".png";
            // Create the directory if it doesn't exist, for storing the screenshots
            File directory = new File("screenshots/");
            if (!directory.exists()) {
                directory.mkdirs(); // Create directory and parent directories if necessary
            }

            ImageIO.write(capture, "png", new File(directory, fileName));
        } catch (AWTException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }
    
}