package guioptimiser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class SimulatedAnnealing {

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
        Random random = new Random();
        double temperature = 10000;
        double coolingRate = 0.003;
        int count = 0;
        ArrayList<Integer> bestTextColor = null;
        ArrayList<Integer> bestFieldColor = null;
        ArrayList<Integer> jTextField1TextColor = changeColor();
        ArrayList<Integer> jTextField1 = changeColor();
        String[] str1 = new String[]{"example.png"};
        //
        // generate screenshot
        //
        double currentScore = Consumption.Main(str1);
        double bestScore = currentScore;

        while ((temperature > 1.0) && (count < 100)) { // 尝试100次迭代
            ArrayList<Integer> newTextColor = mutateColor(jTextField1TextColor);
            ArrayList<Integer> newTextField = mutateColor(jTextField1);
            //
            //    generate screenshot
            //
            double newScore = Consumption.Main(str1);

            count++;
            if (getDistance(jTextField1, jTextField1TextColor) >= 128) {
                count--;
                continue;
            }

            String[] strs = new String[]{"example.png"};
            if (newScore < currentScore || Math.exp(bestScore-newScore)/temperature > random.nextDouble()) {
                jTextField1TextColor = newTextColor;
                jTextField1 = newTextField;

                if (currentScore < bestScore) {
                    bestTextColor = jTextField1TextColor;
                    bestFieldColor = jTextField1TextColor;
                    bestScore = currentScore;
                }

            temperature *= 1- coolingRate;
            }
        }
    }

}
