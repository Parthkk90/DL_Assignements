import java.util.*;

/**
 * Comprehensive Minimax Algorithm Implementation for Game Playing
 * This implementation includes:
 * 1. Basic Minimax Algorithm
 * 2. Alpha-Beta Pruning Optimization
 * 3. Interactive Tic-Tac-Toe Game
 * 4. Generic Minimax Framework
 * 5. Performance Analysis
 */
public class ComprehensiveMinimax {
    
    // Tic-Tac-Toe game implementation using Minimax algorithm
    public static class TicTacToeGame {
        private char[][] board;
        private final char HUMAN = 'O';
        private final char AI = 'X';
        private final char EMPTY = '_';
        private int nodesEvaluated = 0;
        
        public TicTacToeGame() {
            board = new char[3][3];
            initializeBoard();
        }
        
        private void initializeBoard() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = EMPTY;
                }
            }
        }
        
        // Display the current board state
        public void displayBoard() {
            System.out.println("\nCurrent Board:");
            System.out.println("   0   1   2");
            for (int i = 0; i < 3; i++) {
                System.out.print(i + "  ");
                for (int j = 0; j < 3; j++) {
                    System.out.print(board[i][j]);
                    if (j < 2) System.out.print(" | ");
                }
                System.out.println();
                if (i < 2) System.out.println("   ---------");
            }
            System.out.println();
        }
        
        // Check if the move is valid
        public boolean isValidMove(int row, int col) {
            return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == EMPTY;
        }
        
        // Make a move on the board
        public void makeMove(int row, int col, char player) {
            if (isValidMove(row, col)) {
                board[row][col] = player;
            }
        }
        
        // Undo a move
        public void undoMove(int row, int col) {
            board[row][col] = EMPTY;
        }
        
        // Check if any moves are left
        public boolean isMovesLeft() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        return true;
                    }
                }
            }
            return false;
        }
        
        // Evaluate the board position
        public int evaluate() {
            nodesEvaluated++;
            
            // Check rows
            for (int i = 0; i < 3; i++) {
                if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                    if (board[i][0] == AI) return 10;
                    else if (board[i][0] == HUMAN) return -10;
                }
            }
            
            // Check columns
            for (int j = 0; j < 3; j++) {
                if (board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                    if (board[0][j] == AI) return 10;
                    else if (board[0][j] == HUMAN) return -10;
                }
            }
            
            // Check diagonals
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                if (board[0][0] == AI) return 10;
                else if (board[0][0] == HUMAN) return -10;
            }
            
            if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
                if (board[0][2] == AI) return 10;
                else if (board[0][2] == HUMAN) return -10;
            }
            
            return 0; // No winner
        }
        
        // Basic Minimax algorithm
        public int minimax(int depth, boolean isMaximizingPlayer) {
            int score = evaluate();
            
            // Base cases
            if (score == 10) return score - depth;  // AI wins
            if (score == -10) return score + depth; // Human wins
            if (!isMovesLeft()) return 0;           // Draw
            
            if (isMaximizingPlayer) {
                int bestValue = Integer.MIN_VALUE;
                
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == EMPTY) {
                            board[i][j] = AI;
                            bestValue = Math.max(bestValue, minimax(depth + 1, false));
                            board[i][j] = EMPTY;
                        }
                    }
                }
                return bestValue;
            } else {
                int bestValue = Integer.MAX_VALUE;
                
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == EMPTY) {
                            board[i][j] = HUMAN;
                            bestValue = Math.min(bestValue, minimax(depth + 1, true));
                            board[i][j] = EMPTY;
                        }
                    }
                }
                return bestValue;
            }
        }
        
        // Minimax with Alpha-Beta Pruning
        public int minimaxAlphaBeta(int depth, boolean isMaximizingPlayer, int alpha, int beta) {
            int score = evaluate();
            
            // Base cases
            if (score == 10) return score - depth;
            if (score == -10) return score + depth;
            if (!isMovesLeft()) return 0;
            
            if (isMaximizingPlayer) {
                int maxEval = Integer.MIN_VALUE;
                
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == EMPTY) {
                            board[i][j] = AI;
                            int eval = minimaxAlphaBeta(depth + 1, false, alpha, beta);
                            board[i][j] = EMPTY;
                            
                            maxEval = Math.max(maxEval, eval);
                            alpha = Math.max(alpha, eval);
                            
                            // Alpha-Beta Pruning
                            if (beta <= alpha) {
                                return maxEval; // Prune remaining branches
                            }
                        }
                    }
                }
                return maxEval;
            } else {
                int minEval = Integer.MAX_VALUE;
                
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == EMPTY) {
                            board[i][j] = HUMAN;
                            int eval = minimaxAlphaBeta(depth + 1, true, alpha, beta);
                            board[i][j] = EMPTY;
                            
                            minEval = Math.min(minEval, eval);
                            beta = Math.min(beta, eval);
                            
                            // Alpha-Beta Pruning
                            if (beta <= alpha) {
                                return minEval; // Prune remaining branches
                            }
                        }
                    }
                }
                return minEval;
            }
        }
        
        // Find the best move for AI using basic Minimax
        public int[] findBestMove() {
            int bestValue = Integer.MIN_VALUE;
            int[] bestMove = {-1, -1};
            nodesEvaluated = 0;
            
            System.out.println("AI is calculating the best move...");
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = AI;
                        int moveValue = minimax(0, false);
                        board[i][j] = EMPTY;
                        
                        System.out.println("Move (" + i + "," + j + ") has value: " + moveValue);
                        
                        if (moveValue > bestValue) {
                            bestMove[0] = i;
                            bestMove[1] = j;
                            bestValue = moveValue;
                        }
                    }
                }
            }
            
            System.out.println("Best move: (" + bestMove[0] + "," + bestMove[1] + 
                             ") with value: " + bestValue);
            System.out.println("Nodes evaluated: " + nodesEvaluated);
            return bestMove;
        }
        
        // Find the best move using Alpha-Beta Pruning
        public int[] findBestMoveAlphaBeta() {
            int bestValue = Integer.MIN_VALUE;
            int[] bestMove = {-1, -1};
            nodesEvaluated = 0;
            
            System.out.println("AI is calculating with Alpha-Beta pruning...");
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = AI;
                        int moveValue = minimaxAlphaBeta(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        board[i][j] = EMPTY;
                        
                        System.out.println("Move (" + i + "," + j + ") has value: " + moveValue);
                        
                        if (moveValue > bestValue) {
                            bestMove[0] = i;
                            bestMove[1] = j;
                            bestValue = moveValue;
                        }
                    }
                }
            }
            
            System.out.println("Best move: (" + bestMove[0] + "," + bestMove[1] + 
                             ") with value: " + bestValue);
            System.out.println("Nodes evaluated: " + nodesEvaluated);
            return bestMove;
        }
        
        // Check if game is over
        public boolean isGameOver() {
            return evaluate() != 0 || !isMovesLeft();
        }
        
        // Get the winner
        public char getWinner() {
            int score = evaluate();
            if (score > 0) return AI;
            if (score < 0) return HUMAN;
            return EMPTY;
        }
        
        // Play interactive game
        public void playGame(boolean useAlphaBeta) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n=== TIC-TAC-TOE WITH MINIMAX AI ===");
            System.out.println("You are 'O', AI is 'X'");
            System.out.println("Enter moves as row,col (0-2)");
            System.out.println("Using " + (useAlphaBeta ? "Alpha-Beta Pruning" : "Basic Minimax"));
            
            while (!isGameOver()) {
                displayBoard();
                
                // Human move
                System.out.print("Your move (row,col): ");
                try {
                    String[] input = scanner.nextLine().split(",");
                    int row = Integer.parseInt(input[0].trim());
                    int col = Integer.parseInt(input[1].trim());
                    
                    if (isValidMove(row, col)) {
                        makeMove(row, col, HUMAN);
                        
                        if (isGameOver()) break;
                        
                        // AI move
                        int[] aiMove = useAlphaBeta ? findBestMoveAlphaBeta() : findBestMove();
                        makeMove(aiMove[0], aiMove[1], AI);
                        System.out.println("AI played: (" + aiMove[0] + "," + aiMove[1] + ")");
                        
                    } else {
                        System.out.println("Invalid move! Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input format! Use row,col (e.g., 1,2)");
                }
            }
            
            displayBoard();
            char winner = getWinner();
            if (winner == HUMAN) {
                System.out.println("You win! (This shouldn't happen with perfect AI!)");
            } else if (winner == AI) {
                System.out.println("AI wins!");
            } else {
                System.out.println("It's a draw!");
            }
        }
        
        // Set specific board state for testing
        public void setBoardState(char[][] newBoard) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = newBoard[i][j];
                }
            }
        }
    }
    
    // Generic Minimax Framework
    public static abstract class GameState {
        public abstract List<GameState> getSuccessors(boolean isMaximizingPlayer);
        public abstract int evaluate();
        public abstract boolean isTerminal();
        public abstract GameState copy();
        public abstract void displayState();
        
        // Generic minimax implementation
        public int minimax(int depth, boolean isMaximizingPlayer) {
            if (isTerminal() || depth == 0) {
                return evaluate();
            }
            
            if (isMaximizingPlayer) {
                int maxEval = Integer.MIN_VALUE;
                for (GameState successor : getSuccessors(true)) {
                    int eval = successor.minimax(depth - 1, false);
                    maxEval = Math.max(maxEval, eval);
                }
                return maxEval;
            } else {
                int minEval = Integer.MAX_VALUE;
                for (GameState successor : getSuccessors(false)) {
                    int eval = successor.minimax(depth - 1, true);
                    minEval = Math.min(minEval, eval);
                }
                return minEval;
            }
        }
    }
    
    // Performance comparison between basic Minimax and Alpha-Beta
    public static void performanceComparison() {
        System.out.println("\n=== PERFORMANCE COMPARISON ===");
        
        TicTacToeGame game1 = new TicTacToeGame();
        TicTacToeGame game2 = new TicTacToeGame();
        
        // Set a mid-game position
        char[][] testBoard = {
            {'X', 'O', '_'},
            {'_', 'X', '_'},
            {'O', '_', '_'}
        };
        
        game1.setBoardState(testBoard);
        game2.setBoardState(testBoard);
        
        System.out.println("Test board position:");
        game1.displayBoard();
        
        // Test basic Minimax
        System.out.println("1. Basic Minimax:");
        long startTime = System.nanoTime();
        int[] move1 = game1.findBestMove();
        long endTime = System.nanoTime();
        long duration1 = endTime - startTime;
        
        // Test Alpha-Beta Pruning
        System.out.println("\n2. Alpha-Beta Pruning:");
        startTime = System.nanoTime();
        int[] move2 = game2.findBestMoveAlphaBeta();
        endTime = System.nanoTime();
        long duration2 = endTime - startTime;
        
        System.out.println("\n=== PERFORMANCE RESULTS ===");
        System.out.println("Basic Minimax:");
        System.out.println("  Time: " + duration1 / 1_000_000.0 + " ms");
        System.out.println("  Nodes evaluated: " + game1.nodesEvaluated);
        
        System.out.println("Alpha-Beta Pruning:");
        System.out.println("  Time: " + duration2 / 1_000_000.0 + " ms");
        System.out.println("  Nodes evaluated: " + game2.nodesEvaluated);
        
        double speedup = (double) duration1 / duration2;
        double nodeReduction = (double) (game1.nodesEvaluated - game2.nodesEvaluated) / game1.nodesEvaluated * 100;
        
        System.out.println("Speedup: " + String.format("%.2f", speedup) + "x");
        System.out.println("Node reduction: " + String.format("%.1f", nodeReduction) + "%");
    }
    
    // Demonstrate minimax concepts
    public static void demonstrateMinimaxConcepts() {
        System.out.println("\n=== MINIMAX ALGORITHM CONCEPTS ===");
        
        System.out.println("1. BASIC PRINCIPLE:");
        System.out.println("   - Minimax assumes both players play optimally");
        System.out.println("   - Maximizing player tries to maximize score");
        System.out.println("   - Minimizing player tries to minimize score");
        System.out.println("   - Algorithm explores all possible game states");
        
        System.out.println("\n2. ALGORITHM STEPS:");
        System.out.println("   a) Generate all possible moves");
        System.out.println("   b) For each move, recursively evaluate the position");
        System.out.println("   c) Choose the move that maximizes/minimizes the score");
        System.out.println("   d) Backtrack and choose the best move");
        
        System.out.println("\n3. ALPHA-BETA PRUNING:");
        System.out.println("   - Optimization technique to reduce search space");
        System.out.println("   - Alpha: best value maximizing player can guarantee");
        System.out.println("   - Beta: best value minimizing player can guarantee");
        System.out.println("   - Prune branches when alpha >= beta");
        
        System.out.println("\n4. TIME COMPLEXITY:");
        System.out.println("   - Basic Minimax: O(b^d) where b=branching factor, d=depth");
        System.out.println("   - Alpha-Beta: O(b^(d/2)) in best case");
        System.out.println("   - For Tic-Tac-Toe: b≈9, d≤9");
        
        System.out.println("\n5. APPLICATIONS:");
        System.out.println("   - Chess, Checkers, Tic-Tac-Toe");
        System.out.println("   - Board games, Card games");
        System.out.println("   - Decision making in AI");
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== COMPREHENSIVE MINIMAX ALGORITHM IMPLEMENTATION ===");
        
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Play Tic-Tac-Toe (Basic Minimax)");
            System.out.println("2. Play Tic-Tac-Toe (Alpha-Beta Pruning)");
            System.out.println("3. Performance Comparison");
            System.out.println("4. Minimax Concepts Explanation");
            System.out.println("5. Test Specific Position");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        new TicTacToeGame().playGame(false);
                        break;
                    case 2:
                        new TicTacToeGame().playGame(true);
                        break;
                    case 3:
                        performanceComparison();
                        break;
                    case 4:
                        demonstrateMinimaxConcepts();
                        break;
                    case 5:
                        testSpecificPosition();
                        break;
                    case 6:
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    // Test a specific game position
    public static void testSpecificPosition() {
        TicTacToeGame game = new TicTacToeGame();
        
        // Example critical position
        char[][] testBoard = {
            {'X', '_', 'O'},
            {'_', 'X', '_'},
            {'O', '_', '_'}
        };
        
        game.setBoardState(testBoard);
        
        System.out.println("\n=== TESTING SPECIFIC POSITION ===");
        System.out.println("Critical game position:");
        game.displayBoard();
        
        System.out.println("AI must find the winning move!");
        int[] bestMove = game.findBestMoveAlphaBeta();
        
        game.makeMove(bestMove[0], bestMove[1], 'X');
        System.out.println("\nAfter AI's move:");
        game.displayBoard();
        
        if (game.getWinner() == 'X') {
            System.out.println("AI found the winning move!");
        } else {
            System.out.println("Position analysis complete.");
        }
    }
}