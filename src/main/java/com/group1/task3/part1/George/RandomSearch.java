package guioptimiser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class RandomSearch {

    public static ArrayList<Integer> changeColor() {
        Random randomInt = new Random();

        return new ArrayList<Integer>(Arrays.asList(new Integer[]{randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256)}));
    }

    public static double getDistance(ArrayList<Integer> jTextField1, ArrayList<Integer> jTextField1TextColor){


        int redDistance = jTextField1.get(0) - jTextField1TextColor.get(0);
        int greenDistance = jTextField1.get(1) - jTextField1TextColor.get(1);
        int blueDistance = jTextField1.get(2) - jTextField1TextColor.get(2);

        return Math.sqrt(redDistance * redDistance + greenDistance * greenDistance + blueDistance * blueDistance);

    }

    public static ArrayList<Integer> main(String[] args) {
        double bestScore = 0.0;
        ArrayList<Integer> bestTextColor = null;
        ArrayList<Integer> bestBackgroundColor = null;
        for (int i = 0; i < 1000; i++) { // 尝试1000次迭代
            ArrayList<Integer> jTextField1TextColor = changeColor();
            ArrayList<Integer> jTextField1 = changeColor();


            if (getDistance(jTextField1, jTextField1TextColor) <= 128) {
                i--;
                continue;
                }
            //
            // generate screenshot
            //
            String[] strs = new String[]{"image-1713263135305.bmp"};
            double newScore = Consumption.Main(strs);
            if (newScore < bestScore) {
                bestScore = newScore;
                bestTextColor = jTextField1TextColor;

            }

        }
        System.out.println(bestTextColor);
        return bestTextColor;
    }

}
