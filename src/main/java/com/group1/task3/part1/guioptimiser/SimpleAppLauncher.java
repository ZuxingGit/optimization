/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group1.task3.part1.guioptimiser;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Mahmoud-Uni, Zuxing
 */
public class SimpleAppLauncher {

    // private static String TARGET_APP = "calculator.jar";
    public String TARGET_APP = "com.group1.task3.part1.gui.SimpleMainFrame";
    //private static final String TARGET_APP = "simpleApp.jar";
    private static final String TARGET_APP_COLOR = "color_simpleApp.csv";
    public final int TARGET_APP_RUNNINGTIME = 2500;
    // private static final String JAVA_COMMAND = "java -jar ";
    private static String JAVA_COMMAND = "java -cp ";
    public String parentDir = "";
    private Capture capture = new Capture(TARGET_APP);
    // private static ArrayList<ArrayList<Integer>> RGB = new ArrayList<>();
    // private static Random randomInt = new Random();
    public BufferedImage imageContent;

    public SimpleAppLauncher() {
        // parentDir = getParentDir() + "src/main/java/com/group1/task3/part1/guioptimiser/";
        parentDir = getParentDir();
        //System.out.println(parentDir.concat(TARGET_APP));
    }
    
    public double runApp(ArrayList<ArrayList<Integer>> solution) {
        changeColorAll(solution);
        String path = parentDir.concat("target/classes/ ").concat(TARGET_APP);
        double totalChargeConsumption = 0;
        try {
            //java -jar C:\Users\Mahmoud-Uni\Documents\NetBeansProjects\calculator\dist\calculator.jar

            //path = "\""+path+"\"";
            // System.out.println("Target App: " + path);

            Runtime runTime = Runtime.getRuntime();
            // System.out.println(JAVA_COMMAND.concat(path));
            Process process = runTime.exec(JAVA_COMMAND.concat(path));
            try {
                Thread.sleep(TARGET_APP_RUNNINGTIME);
                imageContent = capture.takeScreenShot();
                EnergyConsumptionComputer eneConsComputer = new EnergyConsumptionComputer();
                totalChargeConsumption = eneConsComputer.calculateTotalChargeConsumption(imageContent);
//                BufferedReader stdError = new BufferedReader(new
//                InputStreamReader(process.getErrorStream()));
//                String line = "";
//                while((line=stdError.readLine())!=null)
//                {
//                    System.out.println("error!");
//                    System.out.println(line);
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalChargeConsumption;
    }

    /**
     * Save the BufferedImage of the image to a file
     * @param currentBestImage
     * @param fileName
     */
    public void saveScreenShot(BufferedImage currentBestImage, String fileName) {
        if (currentBestImage == null) { // if null then save current scrShot content
            capture.saveScreenShot(imageContent, fileName);
        } else {
            capture.saveScreenShot(currentBestImage, fileName);
        }
    }

    public void changeColorAll(ArrayList<ArrayList<Integer>> RGB) {
        try {
            // guiComponents contains GUI components' name.
            ArrayList<String> guiComponents = new ArrayList<>();
            guiComponents.add("mainFrameColor"); // both apps
            guiComponents.add("jButton1");// both apps
            guiComponents.add("jTextField1");// both apps
            guiComponents.add("jTextField1TextColor");// both apps
            guiComponents.add("jLabel1");// both apps
            guiComponents.add("jPanel1");// both apps

            saveToCSV(parentDir.concat("src/main/java/com/group1/task3/part1/guioptimiser/")
                                .concat(TARGET_APP_COLOR), guiComponents, RGB);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveToCSV(String filePath, ArrayList<String> guiComponents, ArrayList<ArrayList<Integer>> RGB) {
        try {
            // System.out.println("Saving to: " + filePath);
            BufferedWriter br = new BufferedWriter(new FileWriter(new File(filePath)));
            String line = "";
            for (int i = 0; i < guiComponents.size(); i++) {
                line += guiComponents.get(i).concat(",").concat(RGB.get(i).toString().replace("[", "").replace("]", "").replaceAll("\\s", "")) + "\n";
                //System.out.println(line);
            }
            br.write(line);
            br.flush();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getParentDir() {
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
}
