package guioptimiser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class HillClimbing {

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

    public static ArrayList<Integer> mutateColor(ArrayList<Integer> color) {
        Random randomInt = new Random();
        ArrayList<Integer> newColor = null;
        int mutationAmount = randomInt.nextInt(21) - 10;
        for (int i = 0; i < color.size(); i++) {
            int old = color.get(i);
            int newRgb = old + mutationAmount;
            if (newRgb < 0){
                newRgb += 256;
            }
            newColor.add(newRgb);
        }

        return newColor;
    }


    public static void main(String[] args) {
        double bestScore = 0.0;
        ArrayList<Integer> bestTextColor = null;
        ArrayList<Integer> bestBackgroundColor = null;
        ArrayList<Integer> jTextField1TextColor = changeColor();
        ArrayList<Integer> jTextField1 = changeColor();

        for (int i = 0; i < 100; i++) { // 尝试100次迭代
            jTextField1TextColor = mutateColor(jTextField1TextColor);
            jTextField1 = mutateColor(jTextField1);


            if (getDistance(jTextField1, jTextField1TextColor) <= 128) {
                i--;
                continue;
            }
            //
            // generate screenshot
            //
            String[] strs = new String[]{"Red", "Green", "Blue"};
            double newScore = Consumption.Main(strs);
            if (newScore < bestScore) {
                bestScore = newScore;
                bestTextColor = jTextField1TextColor;
                bestBackgroundColor = jTextField1;
            }
        }
    }

}
