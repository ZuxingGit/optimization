package com.group1.task4.part2.NSGA2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.algorithm.SPEA2;
import org.moeaframework.core.Constraint;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

import com.group1.task3.part1.algorithm.Euclidean;
import com.group1.task4.part2.ModfiedMOEAClass.CustomNSGAII;
import com.group1.task4.part2.appLauncher.SimpleAppLauncher;
import com.group1.task4.part2.gui.SimpleAppColor;

/**
 * It's also very easy to add your own test or real-world problems for use in the MOEA
 * Framework.  Here we recreate the Srinivas test problem and solve it using NSGA-II. 
 */
public class SimpleApp_NSGA_II {
	private static SimpleAppLauncher sa = new SimpleAppLauncher();
	private static BufferedImage referenceImage = null;
	private static double referenceCharge = 0;
	private static ArrayList<ArrayList<Integer>> referenceSolution = new ArrayList<ArrayList<Integer>>();
	private static int k = 0;
	private static int count = 0;

	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>> referenceSolution = getReferenceSolution();
		System.out.println("Reference solution: " + referenceSolution);
		referenceCharge = sa.runApp(referenceSolution);
		referenceImage = sa.imageContent;
System.out.println("==========================================================");

        Problem problem = new Srinivas();
        
        CustomNSGAII algorithm = new CustomNSGAII(problem);
        // SPEA2 algorithm = new SPEA2(problem);

        try {
            algorithm.setInitialPopulationSize(20); //20
            algorithm.initialize();
            String path = sa.getParentDir() + "src/main/java/com/group1/task4/part2/outcomes/simpleApp/";
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
				sa.runApp(oneSolution);
				sa.saveScreenShot(null,"solution" + count + ".png");
				count++;
			});
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static class Srinivas extends AbstractProblem {

		/**
		 * Creates the problem with 18 decision variables, 2 objectives, and two constraints.
		 */
		public Srinivas() {
			super(18, 2, 2);
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
			double f1 = Euclidean.getEuclideanDistanceSum(referenceImage, sa.imageContent);
			// obj2: charge
			double f2 = sa.runApp(currentSolution);
			System.out.println("--------------------------------------------------------");
			// constraint: Euclidean distance between text and its background colors, not square root
			double c1 = Math.pow(currentSolution.get(3).get(0)-currentSolution.get(2).get(0), 2.0) 
						+ Math.pow(currentSolution.get(3).get(1)-currentSolution.get(2).get(1), 2.0)
						+ Math.pow(currentSolution.get(3).get(2)-currentSolution.get(2).get(2), 2.0);
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
			Solution solution = new Solution(18, 2, 2);
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
	 * Get the reference solution from simpleAppScheme.csv
	 * @return referenceSolution
	 */
	public static ArrayList<ArrayList<Integer>> getReferenceSolution() {
		SimpleAppColor simpleAppColor = new SimpleAppColor("simpleAppScheme.csv");
		referenceSolution.add(new ArrayList<Integer>() {{
			add(simpleAppColor.mainFrameColor.getRed());
			add(simpleAppColor.mainFrameColor.getGreen());
			add(simpleAppColor.mainFrameColor.getBlue());
		}});
		referenceSolution.add(new ArrayList<Integer>() {{
			add(simpleAppColor.jButton1.getRed());
			add(simpleAppColor.jButton1.getGreen());
			add(simpleAppColor.jButton1.getBlue());
		}});
		referenceSolution.add(new ArrayList<Integer>() {{
			add(simpleAppColor.jTextField1.getRed());
			add(simpleAppColor.jTextField1.getGreen());
			add(simpleAppColor.jTextField1.getBlue());
		}});
		referenceSolution.add(new ArrayList<Integer>() {{
			add(simpleAppColor.jTextField1TextColor.getRed());
			add(simpleAppColor.jTextField1TextColor.getGreen());
			add(simpleAppColor.jTextField1TextColor.getBlue());
		}});
		referenceSolution.add(new ArrayList<Integer>() {{
			add(simpleAppColor.jLabel1.getRed());
			add(simpleAppColor.jLabel1.getGreen());
			add(simpleAppColor.jLabel1.getBlue());
		}});
		referenceSolution.add(new ArrayList<Integer>() {{
			add(simpleAppColor.jPanel1.getRed());
			add(simpleAppColor.jPanel1.getGreen());
			add(simpleAppColor.jPanel1.getBlue());
		}});
		return referenceSolution;
	}

}
