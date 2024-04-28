package com.group1.task4.part2.NSGA2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Constraint;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

import com.group1.task3.part1.algorithm.Euclidean;
import com.group1.task4.part2.ModfiedMOEAClass.CustomNSGAII;
import com.group1.task4.part2.appLauncher.CalculatorLauncher;
import com.group1.task4.part2.gui.CalculatorColor;

public class Calculator_NSGA_II {
    private static CalculatorLauncher ca = new CalculatorLauncher();
    private static BufferedImage referenceImage = null;
    private static double referenceCharge = 0;
    private static ArrayList<ArrayList<Integer>> referenceSolution = new ArrayList<ArrayList<Integer>>();
    private static int k = 0;
    private static int count = 0;

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> referenceSolution = getReferenceSolution();
        System.out.println("Reference solution: " + referenceSolution);
        referenceCharge = ca.runApp(referenceSolution);
        referenceImage = ca.imageContent;
        System.out.println("==========================================================");

        Problem problem = new Srinivas();

        CustomNSGAII algorithm = new CustomNSGAII(problem);
        // SPEA2 algorithm = new SPEA2(problem);

        try {
            algorithm.setInitialPopulationSize(20); //20
            algorithm.initialize();
            String path = ca.getParentDir() + "src/main/java/com/group1/task4/part2/outcomes/calculator/";
            // get the initial population
            Population firstPopulation = algorithm.getInitialPopulation();
            // System.out.println("Initial population: " + firstPopulation.size());
            firstPopulation.saveCSV(new File(path + "firstPopulation.csv"));

            algorithm.run(10000); //10000
            // get the final population
            Population finalPopulation = algorithm.getPopulation();
            finalPopulation.saveCSV(new File(path + "finalPopulation.csv"));

            // algorithm.getResult().display();
            ArrayList<ArrayList<Integer>> oneSolution = new ArrayList<ArrayList<Integer>>();
            finalPopulation.iterator().forEachRemaining(solution -> {
                oneSolution.clear();
                k = 0;
                for (int i=0; i < referenceSolution.size(); i++) {
                    oneSolution.add(new ArrayList<Integer>() {{
                        add((int) EncodingUtils.getReal(solution.getVariable(k++)));
                        add((int) EncodingUtils.getReal(solution.getVariable(k++)));
                        add((int) EncodingUtils.getReal(solution.getVariable(k++)));
                    }});
                }
                ca.runApp(oneSolution);
                ca.saveScreenShot(null, "solution" + count + ".png");
                count++;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Srinivas extends AbstractProblem {

        /**
         * Creates the problem with 81(27x3) decision variables, 2 objectives, and 2 constraints.
         */
        public Srinivas() {
            super(81, 2, 2);
        }

        /**
		 * Function to evaluate each solution.
		 */
		@Override
		public void evaluate(Solution solution) {
			// need to evaluate 2 things: 𝚫colour & charge, to minimise them
			ArrayList<ArrayList<Integer>> currentSolution = new ArrayList<ArrayList<Integer>>();
			k = 0;
			for (int i=0; i < referenceSolution.size(); i++) {
				currentSolution.add(new ArrayList<Integer>() {{
					add((int) EncodingUtils.getReal(solution.getVariable(k++)));
					add((int) EncodingUtils.getReal(solution.getVariable(k++)));
					add((int) EncodingUtils.getReal(solution.getVariable(k++)));
				}});
			}
			System.out.println("Current solution: " + currentSolution);

			// double x = EncodingUtils.getReal(solution.getVariable(0));
			// double y = EncodingUtils.getReal(solution.getVariable(1));

			// obj1: 𝚫colour
			double f1 = Euclidean.getEuclideanDistanceSum(referenceImage, ca.imageContent);
			// obj2: charge consumption
			double f2 = ca.runApp(currentSolution);
            System.out.println("--------------------------------------------------------");
			// constraint: Euclidean distance between text and its background colors, not square root
			double c1 = Math.pow(currentSolution.get(19).get(0)-currentSolution.get(20).get(0), 2.0)
						+ Math.pow(currentSolution.get(19).get(1)-currentSolution.get(20).get(1), 2.0)
						+ Math.pow(currentSolution.get(19).get(2)-currentSolution.get(20).get(2), 2.0);
			double c2 = f2 - referenceCharge;// charge shoule be less than referenceCharge, otherwise meaningless

			// set the objective values - these are being minimized
			solution.setObjective(0, f1);
			solution.setObjective(1, f2);

			// set the constraints - use the methods in the Constraint class for convenience
			solution.setConstraint(0, Constraint.greaterThan(c1, 0));
			solution.setConstraint(1, Constraint.lessThan(c2, 0.0));
		}

		/**
		 * Function to create a new solution.  Here is where we define the types and
		 * bounds of each decision variables.
		 */
		@Override
		public Solution newSolution() {
			Solution solution = new Solution(81, 2, 2);
			int m = 0;
			for (int i=0; i < referenceSolution.size(); i++) {
				for (int j=0; j < 3; j++) {
					solution.setVariable(m++, new RealVariable(referenceSolution.get(i).get(j), 0, 255));
				}
			}
			return solution;
		}

    }

    /**
     * Get the reference solution from calculatorScheme.csv
     * @return
     */
    public static ArrayList<ArrayList<Integer>> getReferenceSolution() {
        CalculatorColor calculatorColor = new CalculatorColor("calculatorScheme.csv");
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.mainFrameColor.getRed());
            add(calculatorColor.mainFrameColor.getGreen());
            add(calculatorColor.mainFrameColor.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton1.getRed());
            add(calculatorColor.jButton1.getGreen());
            add(calculatorColor.jButton1.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton2.getRed());
            add(calculatorColor.jButton2.getGreen());
            add(calculatorColor.jButton2.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton3.getRed());
            add(calculatorColor.jButton3.getGreen());
            add(calculatorColor.jButton3.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton4.getRed());
            add(calculatorColor.jButton4.getGreen());
            add(calculatorColor.jButton4.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton5.getRed());
            add(calculatorColor.jButton5.getGreen());
            add(calculatorColor.jButton5.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton6.getRed());
            add(calculatorColor.jButton6.getGreen());
            add(calculatorColor.jButton6.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton7.getRed());
            add(calculatorColor.jButton7.getGreen());
            add(calculatorColor.jButton7.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton8.getRed());
            add(calculatorColor.jButton8.getGreen());
            add(calculatorColor.jButton8.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton9.getRed());
            add(calculatorColor.jButton9.getGreen());
            add(calculatorColor.jButton9.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton10.getRed());
            add(calculatorColor.jButton10.getGreen());
            add(calculatorColor.jButton10.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton11.getRed());
            add(calculatorColor.jButton11.getGreen());
            add(calculatorColor.jButton11.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton12.getRed());
            add(calculatorColor.jButton12.getGreen());
            add(calculatorColor.jButton12.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton13.getRed());
            add(calculatorColor.jButton13.getGreen());
            add(calculatorColor.jButton13.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton14.getRed());
            add(calculatorColor.jButton14.getGreen());
            add(calculatorColor.jButton14.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton15.getRed());
            add(calculatorColor.jButton15.getGreen());
            add(calculatorColor.jButton15.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton16.getRed());
            add(calculatorColor.jButton16.getGreen());
            add(calculatorColor.jButton16.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton17.getRed());
            add(calculatorColor.jButton17.getGreen());
            add(calculatorColor.jButton17.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jButton18.getRed());
            add(calculatorColor.jButton18.getGreen());
            add(calculatorColor.jButton18.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{ //19
            add(calculatorColor.jTextField1.getRed());
            add(calculatorColor.jTextField1.getGreen());
            add(calculatorColor.jTextField1.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{ //20
            add(calculatorColor.jTextField1TextColor.getRed());
            add(calculatorColor.jTextField1TextColor.getGreen());
            add(calculatorColor.jTextField1TextColor.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jLabel1.getRed());
            add(calculatorColor.jLabel1.getGreen());
            add(calculatorColor.jLabel1.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jPanel1.getRed());
            add(calculatorColor.jPanel1.getGreen());
            add(calculatorColor.jPanel1.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jPanel2.getRed());
            add(calculatorColor.jPanel2.getGreen());
            add(calculatorColor.jPanel2.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jPanel3.getRed());
            add(calculatorColor.jPanel3.getGreen());
            add(calculatorColor.jPanel3.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jPanel4.getRed());
            add(calculatorColor.jPanel4.getGreen());
            add(calculatorColor.jPanel4.getBlue());
        }});
        referenceSolution.add(new ArrayList<Integer>() {{
            add(calculatorColor.jPanel5.getRed());
            add(calculatorColor.jPanel5.getGreen());
            add(calculatorColor.jPanel5.getBlue());
        }});

        return referenceSolution;
    }
}
