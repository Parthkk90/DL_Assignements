import java.util.*;

public class PuzzleBFS {
    private static final int SIZE = 3;
    private static final int[][] GOAL_STATE = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    
    // Puzzle state class
    static class PuzzleState {
        int[][] board;
        int emptyRow, emptyCol;
        int depth;
        PuzzleState parent; // To track the solution path
        
        public PuzzleState(int[][] board, int depth, PuzzleState parent) {
            this.board = new int[SIZE][SIZE];
            this.depth = depth;
            this.parent = parent;
            
            // Copy board and find empty position (0)
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    this.board[i][j] = board[i][j];
                    if (board[i][j] == 0) {
                        this.emptyRow = i;
                        this.emptyCol = j;
                    }
                }
            }
        }
        
        // Check if current state is goal state
        public boolean isGoal() {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] != GOAL_STATE[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        
        // Generate all possible next states
        public List<PuzzleState> getNextStates() {
            List<PuzzleState> nextStates = new ArrayList<>();
            
            // Possible moves: up, down, left, right
            int[] dRow = {-1, 1, 0, 0};
            int[] dCol = {0, 0, -1, 1};
            
            for (int i = 0; i < 4; i++) {
                int newRow = emptyRow + dRow[i];
                int newCol = emptyCol + dCol[i];
                
                // Check if move is valid
                if (newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE) {
                    // Create new board with moved tile
                    int[][] newBoard = new int[SIZE][SIZE];
                    for (int r = 0; r < SIZE; r++) {
                        for (int c = 0; c < SIZE; c++) {
                            newBoard[r][c] = board[r][c];
                        }
                    }
                    
                    // Swap empty space with adjacent tile
                    newBoard[emptyRow][emptyCol] = board[newRow][newCol];
                    newBoard[newRow][newCol] = 0;
                    
                    nextStates.add(new PuzzleState(newBoard, depth + 1, this));
                }
            }
            
            return nextStates;
        }
        
        // Convert board to string for comparison
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    sb.append(board[i][j]);
                }
            }
            return sb.toString();
        }
        
        // Print board in readable format
        public void printBoard() {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print(board[i][j] + " ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    
    // BFS algorithm implementation
    public static PuzzleState solveBFS(PuzzleState initial) {
        Queue<PuzzleState> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(initial);
        visited.add(initial.toString());
        
        int nodesExplored = 0;
        
        while (!queue.isEmpty()) {
            PuzzleState current = queue.poll();
            nodesExplored++;
            
            // Check if goal reached
            if (current.isGoal()) {
                System.out.println("Solution found!");
                System.out.println("Nodes explored: " + nodesExplored);
                System.out.println("Solution depth: " + current.depth);
                return current;
            }
            
            // Add all possible next states to queue
            List<PuzzleState> nextStates = current.getNextStates();
            for (PuzzleState nextState : nextStates) {
                String stateString = nextState.toString();
                if (!visited.contains(stateString)) {
                    visited.add(stateString);
                    queue.offer(nextState);
                }
            }
        }
        
        System.out.println("No solution found!");
        return null;
    }
    
    // Print the solution path
    public static void printSolutionPath(PuzzleState solution) {
        if (solution == null) {
            System.out.println("No solution to print.");
            return;
        }
        
        List<PuzzleState> path = new ArrayList<>();
        PuzzleState current = solution;
        
        // Build path from solution to initial state
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        
        // Print path from initial to solution
        Collections.reverse(path);
        System.out.println("Solution path:");
        for (int i = 0; i < path.size(); i++) {
            System.out.println("Step " + i + ":");
            path.get(i).printBoard();
        }
    }
    
    public static void main(String[] args) {
        // Example initial state (solvable puzzle)
        int[][] initialBoard = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
        
        System.out.println("8-Puzzle BFS Solver");
        System.out.println("Initial state:");
        
        PuzzleState initial = new PuzzleState(initialBoard, 0, null);
        initial.printBoard();
        
        System.out.println("Goal state:");
        PuzzleState goal = new PuzzleState(GOAL_STATE, 0, null);
        goal.printBoard();
        
        System.out.println("Starting BFS search...");
        long startTime = System.currentTimeMillis();
        
        PuzzleState solution = solveBFS(initial);
        
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        
        // Print the solution path
        printSolutionPath(solution);
    }
}