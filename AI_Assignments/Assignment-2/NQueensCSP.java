import java.util.*;

public class NQueensCSP {
    private int n;
    private int[] queens; // queens[i] = column position of queen in row i
    private int solutions = 0;
    
    public NQueensCSP(int n) {
        this.n = n;
        this.queens = new int[n];
        Arrays.fill(queens, -1); // -1 means no queen placed
    }
    
    // Check if placing a queen at (row, col) is safe
    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            // Check column conflict
            if (queens[i] == col) {
                return false;
            }
            
            // Check diagonal conflicts
            if (Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }
    
    // Backtracking algorithm to solve N-Queens
    private boolean solveNQueens(int row) {
        // Base case: all queens placed successfully
        if (row == n) {
            solutions++;
            return true;
        }
        
        // Try placing queen in each column of current row
        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                queens[row] = col; // Place queen
                
                if (solveNQueens(row + 1)) {
                    return true; // Found solution
                }
                
                queens[row] = -1; // Backtrack
            }
        }
        
        return false; // No solution found
    }
    
    // Find all solutions
    private void findAllSolutions(int row) {
        if (row == n) {
            solutions++;
            printSolution();
            return;
        }
        
        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                queens[row] = col;
                findAllSolutions(row + 1);
                queens[row] = -1; // Backtrack
            }
        }
    }
    
    // Print the current solution
    private void printSolution() {
        System.out.println("Solution " + solutions + ":");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (queens[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Solve and find first solution
    public boolean solve() {
        System.out.println("Solving " + n + "-Queens problem...");
        long startTime = System.currentTimeMillis();
        
        boolean found = solveNQueens(0);
        
        long endTime = System.currentTimeMillis();
        
        if (found) {
            System.out.println("Solution found!");
            printSolution();
        } else {
            System.out.println("No solution exists for " + n + "-Queens");
        }
        
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        return found;
    }
    
    // Find and print all solutions
    public void solveAll() {
        System.out.println("Finding all solutions for " + n + "-Queens problem...");
        solutions = 0;
        long startTime = System.currentTimeMillis();
        
        findAllSolutions(0);
        
        long endTime = System.currentTimeMillis();
        System.out.println("Total solutions found: " + solutions);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
    
    public static void main(String[] args) {
        // Test with different board sizes
        int[] boardSizes = {4, 6, 8};
        
        for (int size : boardSizes) {
            System.out.println("=".repeat(40));
            NQueensCSP nQueens = new NQueensCSP(size);
            
            // Find first solution
            nQueens.solve();
            
            // For smaller boards, find all solutions
            if (size <= 6) {
                System.out.println("\nFinding all solutions:");
                NQueensCSP allSolutions = new NQueensCSP(size);
                allSolutions.solveAll();
            }
            System.out.println();
        }
    }
}