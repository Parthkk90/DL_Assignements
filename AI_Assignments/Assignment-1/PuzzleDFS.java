import java.util.*;

public class PuzzleDFS {
    private static final int SIZE = 3;
    private static final int[][] GOAL_STATE = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    
    // Puzzle state class
    static class PuzzleState {
        int[][] board;
        int emptyRow, emptyCol;
        int depth;
        
        public PuzzleState(int[][] board, int depth) {
            this.board = new int[SIZE][SIZE];
            this.depth = depth;
            
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
                    
                    nextStates.add(new PuzzleState(newBoard, depth + 1));
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
    
    // DFS algorithm implementation
    public static boolean solveDFS(PuzzleState initial, int maxDepth) {
        Set<String> visited = new HashSet<>();
        Stack<PuzzleState> stack = new Stack<>();
        
        stack.push(initial);
        
        while (!stack.isEmpty()) {
            PuzzleState current = stack.pop();
            
            // Check if goal reached
            if (current.isGoal()) {
                System.out.println("Solution found at depth: " + current.depth);
                current.printBoard();
                return true;
            }
            
            // Skip if already visited or depth limit exceeded
            String stateString = current.toString();
            if (visited.contains(stateString) || current.depth >= maxDepth) {
                continue;
            }
            
            visited.add(stateString);
            
            // Add all possible next states to stack
            List<PuzzleState> nextStates = current.getNextStates();
            for (PuzzleState nextState : nextStates) {
                if (!visited.contains(nextState.toString())) {
                    stack.push(nextState);
                }
            }
        }
        
        System.out.println("No solution found within depth limit: " + maxDepth);
        return false;
    }
    
    public static void main(String[] args) {
        // Example initial state (solvable puzzle)
        int[][] initialBoard = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
        
        System.out.println("8-Puzzle DFS Solver");
        System.out.println("Initial state:");
        
        PuzzleState initial = new PuzzleState(initialBoard, 0);
        initial.printBoard();
        
        System.out.println("Goal state:");
        PuzzleState goal = new PuzzleState(GOAL_STATE, 0);
        goal.printBoard();
        
        System.out.println("Starting DFS search...");
        long startTime = System.currentTimeMillis();
        
        boolean solved = solveDFS(initial, 20); // Depth limit of 20
        
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        
        if (!solved) {
            System.out.println("Try increasing the depth limit for harder puzzles.");
        }
    }
}