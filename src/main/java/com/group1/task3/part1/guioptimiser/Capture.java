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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import com.group1.task3.part1.algorithm.RandomSerach;

/**
 *
 * @author Mahmoud-Uni
 */
public class Capture {
    RandomSerach randomSerach = new RandomSerach();
    private String path;

    public Capture(String targetApp) {
        if (targetApp.contains("SimpleMainFrame")) {
            this.path = "src/main/java/com/group1/task3/part1/guioptimiser/screenshots_simpleApp/";
        } else if (targetApp.contains("CalculatorMainFrame")) {
            this.path = "src/main/java/com/group1/task3/part1/guioptimiser/screenshots_calculator/";
        } else {
            this.path = "src/main/java/com/group1/task3/part1/guioptimiser/screenshots/";
        }
    }

    public String takeScreenShoot()
    {
        String fileName = "";
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            // check the newest screenshot's energy consumption before saving/discarding it
            EnergyConsumptionComputer energyConsumptionComputer = new EnergyConsumptionComputer();
            double totalChargeConsumption = energyConsumptionComputer.calculateTotalChargeConsumption(capture);
            System.out.println("Total charge consumption for the screenshot: " + totalChargeConsumption + " mA");
            
            if (randomSerach.search(totalChargeConsumption)) {
                // Delete the old screenshot
                String oldFileName = randomSerach.getBestColourSettings();
                if (oldFileName != null && !oldFileName.isEmpty()) {
                    File oldFile = new File(path + oldFileName);
                    if (oldFile.exists()) {
                        oldFile.delete();
                        System.out.println("Old screenshot deleted: " + oldFileName);
                    }
                }
                // Save the new screenshot to a file
                fileName = "image-"+System.currentTimeMillis()+".png";
                // Create the directory if it doesn't exist, for storing the screenshots
                File directory = new File(path);
                if (!directory.exists()) {
                    directory.mkdirs(); // Create directory and parent directories if necessary
                } /* else {
                    // Delete all the old screenshots from previous runs
                    File[] oldFiles = directory.listFiles();
                    for (File oldFile : oldFiles) {
                        oldFile.delete();
                    }
                } */
                ImageIO.write(capture, "png", new File(directory, fileName));
                randomSerach.setBestColourSettings(fileName);
                randomSerach.setBestEnergyConsumption(totalChargeConsumption);
                System.out.println("New best screenshot saved: " + fileName);
            } else {
                System.out.println("New screenshot discarded.");
            }
        } catch (AWTException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }
    
}