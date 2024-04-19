package com.group1.archive.GuiOptimiser.src.zuxingCode;

public class Test {
    public static void main(String[] args) {
        GetPixelsARGB getPixelsARGB = new GetPixelsARGB();
        Pixel[][] pixels = getPixelsARGB.getPixalArray();
        for (Pixel[] row : pixels) {
            for (Pixel pixel : row) {
                System.out.println(pixel.toString());
            }
        }
    }
}
