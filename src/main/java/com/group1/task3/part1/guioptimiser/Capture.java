/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group1.task3.part1.guioptimiser;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Mahmoud-Uni
 */
public class Capture {
    private String path;    

    public Capture(String targetApp) {
        // Set the path to store the screenshots based on the target application
        if (targetApp.contains("SimpleMainFrame")) {
            this.path = "src/main/java/com/group1/task3/part1/guioptimiser/screenshots_simpleApp/";
        } else if (targetApp.contains("CalculatorMainFrame")) {
            this.path = "src/main/java/com/group1/task3/part1/guioptimiser/screenshots_calculator/";
        } else {
            this.path = "src/main/java/com/group1/task3/part1/guioptimiser/screenshots/";
        }
    }

    public BufferedImage takeScreenShot()
    {
        
        BufferedImage capture = null; // Initialize the capture variable
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            capture = new Robot().createScreenCapture(screenRect);
        } catch (AWTException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return capture;
    }
    
    public void saveScreenShot(BufferedImage imgData, String fileName)
    {
        try {
            // Create the directory if it doesn't exist, for storing the screenshots
            File directory = new File(this.path);
            if (!directory.exists()) {
                directory.mkdirs(); // Create directory and parent directories if necessary
            }

            ImageIO.write(imgData, "png", new File(directory, fileName));
        } catch (IOException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}