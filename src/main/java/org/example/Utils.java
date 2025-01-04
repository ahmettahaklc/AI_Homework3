package org.example;

import java.util.*;

public class Utils {
    public static final int[] COLORS = {0, 1, 2, 3};
    public static LinkedList<int[][]> initialStates = new LinkedList<>();


    // Generate a random checkerboard with colors assigned randomly
    public static int[][] generateRandomBoard(int n) {
        Random rand = new Random();
        int[][] board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = COLORS[rand.nextInt(COLORS.length)];
            }
        }
        return board;
    }

    // Hill Climbing Algorithm
    public static int[][] solveWithHillClimbing(int[][] board) {
        int n = board.length;
        int[][] current = copyBoard(board);
        int currentConflicts = calculateConflicts(current);

        while (true) {
            int[][] next = getBestNeighbor(current);
            int nextConflicts = calculateConflicts(next);

            if (nextConflicts >= currentConflicts) {
                break; // No improvement possible
            }

            current = next;
            currentConflicts = nextConflicts;
        }

        return current;
    }

    // Get the best neighboring board by minimizing conflicts
    public static int[][] getBestNeighbor(int[][] board) {
        int n = board.length;
        int[][] bestBoard = copyBoard(board);
        int minConflicts = calculateConflicts(board);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int originalColor = board[i][j];
                for (int color : COLORS) {
                    if (color != originalColor) {
                        board[i][j] = color;
                        int conflicts = calculateConflicts(board);
                        if (conflicts < minConflicts) {
                            minConflicts = conflicts;
                            bestBoard = copyBoard(board);
                        }
                    }
                }
                board[i][j] = originalColor; // Restore original color
            }
        }

        return bestBoard;
    }

    // Genetic Algorithm
    public static int[][] solveWithGeneticAlgorithm(int[][] board, int populationSize, int generations) {
        int n = board.length;
        List<int[][]> population = generateInitialPopulation(n, populationSize);

        for (int gen = 0; gen < generations; gen++) {
            population.sort(Comparator.comparingInt(Utils::calculateConflicts));
            if (calculateConflicts(population.get(0)) == 0) {
                break; // Found a solution
            }

            population = createNextGeneration(population, populationSize);
        }

        return population.get(0); // Return the best solution found
    }

    public static List<int[][]> generateInitialPopulation(int n, int size) {
        List<int[][]> population = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            population.add(generateRandomBoard(n));
        }
        return population;
    }

    public static List<int[][]> createNextGeneration(List<int[][]> population, int size) {
        List<int[][]> nextGeneration = new ArrayList<>();
        Random rand = new Random();

        while (nextGeneration.size() < size) {
            int[][] parent1 = population.get(rand.nextInt(population.size() / 2));
            int[][] parent2 = population.get(rand.nextInt(population.size() / 2));
            nextGeneration.add(crossover(parent1, parent2));
        }

        for (int[][] individual : nextGeneration) {
            if (rand.nextDouble() < 0.1) { // Mutation probability
                mutate(individual);
            }
        }

        return nextGeneration;
    }

    public static int[][] crossover(int[][] parent1, int[][] parent2) {
        int n = parent1.length;
        int[][] child = new int[n][n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                child[i][j] = rand.nextBoolean() ? parent1[i][j] : parent2[i][j];
            }
        }
        return child;
    }

    public static void mutate(int[][] board) {
        Random rand = new Random();
        int i = rand.nextInt(board.length);
        int j = rand.nextInt(board.length);
        board[i][j] = COLORS[rand.nextInt(COLORS.length)];
    }

    // Calculate the number of conflicts on the board
    public static int calculateConflicts(int[][] board) {
        int conflicts = 0;
        int n = board.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0 && board[i][j] == board[i - 1][j]) conflicts++;
                if (j > 0 && board[i][j] == board[i][j - 1]) conflicts++;
            }
        }

        return conflicts;
    }

    // Utility methods
    public static int[][] copyBoard(int[][] board) {
        int n = board.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    public static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
