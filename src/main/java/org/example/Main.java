package org.example;
import java.util.*;

public class Main extends Utils{


        // Define the four colors as integers
        // Entry point

        public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the size of the board:");
            int n = in.nextInt();// Size of the checkerboard

            for (int i = 0; i < 10; i++) {
                initialStates.add(generateRandomBoard(10));
            }

            for (int i = 0; i < 10; i++) {
                System.out.println("Initial Board:");
                printBoard(initialStates.get(i));

                // Solve using Hill Climbing
                System.out.println("\nSolving with Hill Climbing:");
                long time = System.currentTimeMillis();
                int[][] hillClimbingSolution = solveWithHillClimbing(initialStates.get(i));
                long endTime = System.currentTimeMillis();
                printBoard(hillClimbingSolution);
                System.out.println("Completion time: " + (endTime - time) + " ms");

                // Solve using Genetic Algorithm
                System.out.println("\nSolving with Genetic Algorithm:");
                time = System.currentTimeMillis();
                int[][] geneticSolution = solveWithGeneticAlgorithm(initialStates.get(i), 100, 1000);
                endTime = System.currentTimeMillis();
                printBoard(geneticSolution);
                System.out.println("Completion time: " + (endTime - time) + " ms");
                System.out.println();
            }
        }

}