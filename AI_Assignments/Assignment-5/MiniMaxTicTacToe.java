import java.util.*;

public class MiniMaxTicTacToe {

    static char[][] board = {
        {'_', '_', '_'},
        {'_', '_', '_'},
        {'_', '_', '_'}
    };

    // Function to print board
    static void printBoard() {
        for (char[] row : board) {
            for (char cell : row) System.out.print(cell + " ");
            System.out.println();
        }
    }

    // Check if any moves are left
    static boolean isMovesLeft() {
        for (char[] row : board)
            for (char cell : row)
                if (cell == '_')
                    return true;
        return false;
    }

    // Evaluate the board
    static int evaluate() {
        // Rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] &&
                board[row][1] == board[row][2]) {
                if (board[row][0] == 'X') return +10;
                else if (board[row][0] == 'O') return -10;
            }
        }

        // Columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] &&
                board[1][col] == board[2][col]) {
                if (board[0][col] == 'X') return +10;
                else if (board[0][col] == 'O') return -10;
            }
        }

        // Diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'X') return +10;
            else if (board[0][0] == 'O') return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'X') return +10;
            else if (board[0][2] == 'O') return -10;
        }

        // Otherwise no winner
        return 0;
    }

    // Minimax function
    static int minimax(int depth, boolean isMax) {
        int score = evaluate();

        // If MAX wins
        if (score == 10)
            return score - depth;

        // If MIN wins
        if (score == -10)
            return score + depth;

        // If no moves left -> draw
        if (!isMovesLeft())
            return 0;

        if (isMax) { // AI’s move
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = 'X';
                        best = Math.max(best, minimax(depth + 1, false));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        } else { // Human’s move
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = 'O';
                        best = Math.min(best, minimax(depth + 1, true));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    // Function to find the best move
    static int[] findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = 'X';
                    int moveVal = minimax(0, false);
                    board[i][j] = '_';

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        System.out.println("Best Move value: " + bestVal);
        return bestMove;
    }

    public static void main(String[] args) {
        board[0][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'X';
        board[1][0] = 'O';
        board[2][2] = '_';

        System.out.println("Current Board:");
        printBoard();

        int[] bestMove = findBestMove();
        System.out.println("\nBest Move for AI: (" + bestMove[0] + ", " + bestMove[1] + ")");
    }
}
