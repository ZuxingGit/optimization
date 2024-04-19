package com.group1.task2.part2;

public class Test {
    public static void main(String[] args) {
        GetPixelsARGB getPixelsARGB = new GetPixelsARGB();
        Pixel[][] pixels = getPixelsARGB.getPixalArray();
        System.out.println("done");
        // for (Pixel[] row : pixels) {
        //     for (Pixel pixel : row) {
        //         System.out.println(pixel.toString());
        //     }
        // }
    }
}
