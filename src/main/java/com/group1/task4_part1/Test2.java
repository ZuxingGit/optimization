import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import org.moeaframework.algorithm.MOEAD;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
//import org.moeaframework.core.indicator.Indicators;
import org.moeaframework.core.indicator.Hypervolume;

import org.moeaframework.problem.ZDT.ZDT1;
import org.moeaframework.problem.ZDT.ZDT2;
import org.moeaframework.problem.DTLZ.DTLZ3;
import org.moeaframework.problem.DTLZ.DTLZ4;

import org.moeaframework.analysis.plot.Plot;

public class Test2 {


    public static void saveToCsv(double avg,double stdDev) {
        String filePath = "MOEAD-output.csv";

        try {
            FileWriter csvWriter = new FileWriter(filePath, true);

            csvWriter.append(Double.toString(avg));
            csvWriter.append(",");
            csvWriter.append(Double.toString(stdDev));
            csvWriter.append("\n");


            csvWriter.close();

            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }


    public static void plot2D(NondominatedPopulation approximationSet, int count){
        Plot plt = new Plot();
        plt.add("MOEAD", approximationSet);
        String currentPath = System.getProperty("user.dir");
        String folderPath = currentPath + "/MOEAD-2d-pics";
//        System.out.println(folderPath);
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
//            System.out.println("Folder is created");
//        } else {
//            System.out.println("Folder already exists");
//        }

        try {
            plt.save(folderPath + "/" + count + ".png");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static String createFolder(){
        String currentPath = System.getProperty("user.dir");
        String folderPath = currentPath + "/MOEAD-solutions";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folderPath;
    }


    public static double runMOEAD(Problem problem, int seed, int probNum, int count, String refPath) throws IOException {
        MOEAD algorithm = new MOEAD(problem);
        algorithm.setInitialPopulationSize(seed);
        algorithm.run(10000);
//        algorithm.getResult().display();

        NondominatedPopulation approximationSet = algorithm.getResult();
        if (probNum<2) {
            plot2D(approximationSet, count);
        }
        else {
            String path = createFolder();
            approximationSet.saveCSV(new File(path + "/" + count + ".csv"));
        }

        NondominatedPopulation referenceSet = NondominatedPopulation.loadReferenceSet(refPath);

        Hypervolume volume = new Hypervolume(problem,referenceSet);
        double hypervolume = volume.evaluate(approximationSet);

        return hypervolume;
    }

    public static double getAverage(double[] numbers) {
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        return sum / numbers.length;
    }

    public static double getStandardDeviation(double[] numbers, double average) {
        double sumSquaredDiff = 0;
        for (double num : numbers) {
            double diff = num - average;
            sumSquaredDiff += diff * diff;
        }
        double variance = sumSquaredDiff / numbers.length;
        return Math.sqrt(variance);
    }

    public static void main(String[] args) throws IOException {
        int[] seeds = {20, 100};
        ArrayList<Problem> probList = new ArrayList<>();
        probList.add(new ZDT1());
        probList.add(new ZDT2());
        probList.add(new DTLZ3(3));
        probList.add(new DTLZ4(3));
        String[] referencePaths = {"pf/ZDT1.pf","pf/ZDT2.pf", "pf/DTLZ3.3D.pf","pf/DTLZ4.3D.pf"};

        int count = 1;
        for(int i=0; i<probList.size(); i++)
        {
            for(int j=0; j<seeds.length; j++)
            {
                double[] hypervolumes = new double[10];
                for(int k=0; k<10; k++)
                {
                    hypervolumes[k] = runMOEAD(probList.get(i),seeds[j],i,count, referencePaths[i]);
                    count++;

                }
                double avg = getAverage(hypervolumes);
                double stdDev = getStandardDeviation(hypervolumes,avg);
                saveToCsv(avg,stdDev);
                System.out.println(avg);
                System.out.println(stdDev);
            }
        }
    }
}
