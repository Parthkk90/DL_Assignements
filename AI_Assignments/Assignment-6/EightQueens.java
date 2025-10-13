public class EightQueens {
    static final int N = 8;
    int[][] board = new int[N][N];

    // Function to print the board
    void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] == 1 ? "Q " : ". ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Check if placing a queen at board[row][col] is safe
    boolean isSafe(int row, int col) {
        // Check column
        for (int i = 0; i < row; i++)
            if (board[i][col] == 1)
                return false;

        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < N; i--, j++)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    // Recursive function to solve the problem
    boolean solve(int row) {
        if (row == N) {
            printBoard();
            return true; // Print one solution; return false if you want all solutions
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                board[row][col] = 1; // place queen
                if (solve(row + 1)) // recursive call
                    return true;
                board[row][col] = 0; // backtrack
            }
        }
        return false; // No position found in this row
    }

    public static void main(String[] args) {
        EightQueens q = new EightQueens();
        if (!q.solve(0))
            System.out.println("No solution exists");
    }
}
