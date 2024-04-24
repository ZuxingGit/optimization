package com.group1.task3.part1.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SimpleAppColor {
    // list all bachground color of all GUI components.
    // Their values are retrieved from csv file.
    public Color mainFrameColor;
    public Color jButton1;
    public Color jTextField1;
    public Color jTextField1TextColor;
    public Color jLabel1;
    public Color jPanel1;


    private String colorCsvFilename;

    public SimpleAppColor(String colorCsvFilename) {
        this.colorCsvFilename = colorCsvFilename;
        int[] RGB = readFromConfigurationFile("mainFrameColor");
        mainFrameColor = new Color(RGB[0], RGB[1], RGB[2]);
        // System.out.println(mainFrameColor.toString());
        RGB = readFromConfigurationFile("jButton1");
        jButton1 = new Color(RGB[0], RGB[1], RGB[2]);
        
        RGB = readFromConfigurationFile("jTextField1");
        jTextField1 = new Color(RGB[0], RGB[1], RGB[2]);
        
        RGB = readFromConfigurationFile("jTextField1TextColor");
        jTextField1TextColor = new Color(RGB[0], RGB[1], RGB[2]);

        RGB = readFromConfigurationFile("jLabel1");
        jLabel1 = new Color(RGB[0], RGB[1], RGB[2]);

        RGB = readFromConfigurationFile("jPanel1");
        jPanel1 = new Color(RGB[0], RGB[1], RGB[2]);
    }

    public int[] readFromConfigurationFile(String key) {

        final String csvFile = colorCsvFilename;

        BufferedReader br = null;
        String line = "";
        final String cvsSplitBy = ",";
        try {
            //File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
            //System.out.println(jarDir.getAbsolutePath());
            String dir = (new File("dir").getAbsolutePath().replace("dir", ""));
            dir = dir + "src/main/java/com/group1/task3/part1/guioptimiser/";
            System.out.println(dir);
            br = new BufferedReader(new FileReader(new File(dir, csvFile)));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                //System.out.println(line);
                String[] value = line.split(cvsSplitBy);

                if (value.length != 4) {
                    throw new IOException(csvFile + " should be CSV with 4 columns: <key>,<value1,value2,value3>");
                }

                if (value[0].equals(key)) {
                    br.close();
                    int[] RGB = new int[value.length - 1];
                    for (int i = 0; i < RGB.length; i++) {
                        RGB[i] = Integer.parseInt(value[i + 1]);
                    }
                    return RGB;
                }
            }
            throw new IOException("Cannot find key '" + key + "' within configuration value '" + csvFile + "'");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null; // or "-1" check with Markus.
    }
}