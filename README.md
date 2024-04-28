# COMP SCI 4409/7409 Search Based Software Engineering: Assignment 2

## Overview
This repository contains the solution code for the course of COMP SCI 4409/7409 Search Based Software Engineering for Assignment 2, the assignment aims to explore techniques for optimizing energy consumption in Android applications through a series of tasks.

**Group Members**:
* a1788962, a1788962@adelaide.edu.au, Shu Chang Chung
* a1808020, a1808020@adelaide.edu.au, Xingyuan Zhang
* a1816653, a1816653@adelaide.edu.au, Zuxing Wu
* a1903852, a1903852@adelaide.edu.au, Weifan Liu

## Instructions
### Development Environment
1. JDK: 17
2. IDE: VS Code
3. Build tool: Maven
4. MOEAFramework version: 4.0 (It's added to pom.xml)

After you set up the above environment requirements correctly, open this project with VS Code. Let it compile for about `10` seconds.

### Task 2: Determine the Surrogate Function
1. Find folder: `/optimization/src/main/java/com/group1/task2/part2`
2. Open `ComputeChargeConsumption.java`, and click `Run` button on the upper right corner of the window. It will process 3 example images and print output in terminal/console.
3. You can see the information of every image: number of pixels & total charge consumption.

### Task 3: Single-Objective Minimisation of Charge Consumption 
#### Part1
1. Find folder: `src/main/java/com/group1/task3/part1/algorithm`, you will see 3 algorithms' implementation:
`RandomSearch.java, HillClimbing.java, SimulatedAnnealing.java`
2. You can open either one of these 3 files and click `Run` button on the upper right corner of the window. It will start two apps' interfaces(simpleApp first), take screenshots, calculate energy consumption. The algorithm decides whether pick the current solution or just discard it.
3. The result of running these 3 algorithms can be found in: `src/main/java/com/group1/task3/part1/guioptimiser/screenshots`. We stored the `.png`(10th, 100th, 1000th) and `.csv`(10th, 100th, 1000th) for different Apps, algorithms and rounds(1-10).
#### Part2
We store plots of our data in `src/main/java/com/group1/task3/part2/t3plots` for comparison.


### Task 4: Multi-Objective Optimisation
#### Part1

#### Part2
1. Find folder: `src/main/java/com/group1/task4/part2/NSGA2`, you will see 2 files: `Calculator_NSGA_II.java, SimpleApp_NSGA_II.java`.
2. You can open either of these 2 files, and click `Run` button. It will start the respective app and optimize the solution using `NSGA2` algorithm.
3. The results of running these 2 programs are stored in `src/main/java/com/group1/task4/part2/outcomes`. It contains `.csv` files for 1st and last generation, and `.png` files of the final population.


### Acknowledgement
We used Copilot in our coding & debugging process.