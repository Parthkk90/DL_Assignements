# Assignment 5 - Minimax Algorithm for Game Playing

## Overview
This assignment implements the **Minimax Algorithm** for game playing, specifically demonstrated with a Tic-Tac-Toe game.

## Files
- `ComprehensiveMinimax.java` - Complete minimax implementation with multiple features
- `MiniMaxTicTacToe.java` - Basic minimax implementation for Tic-Tac-Toe

## Features Implemented

### 1. Basic Minimax Algorithm
- **Concept**: Assumes both players play optimally
- **Maximizing Player**: Tries to maximize the score
- **Minimizing Player**: Tries to minimize the score
- **Time Complexity**: O(b^d) where b = branching factor, d = depth

### 2. Alpha-Beta Pruning
- **Optimization**: Reduces the search space significantly
- **Alpha**: Best value maximizing player can guarantee
- **Beta**: Best value minimizing player can guarantee
- **Pruning**: Skip branches when alpha ≥ beta
- **Time Complexity**: O(b^(d/2)) in best case

### 3. Interactive Tic-Tac-Toe Game
- Human vs AI gameplay
- Choice between basic Minimax and Alpha-Beta pruning
- Real-time move evaluation display
- Performance metrics (nodes evaluated, time taken)

### 4. Performance Analysis
- Side-by-side comparison of basic Minimax vs Alpha-Beta
- Metrics include:
  - Execution time
  - Number of nodes evaluated
  - Speedup factor
  - Node reduction percentage

## Key Classes and Methods

### TicTacToeGame Class
- `minimax(depth, isMaximizingPlayer)` - Basic minimax implementation
- `minimaxAlphaBeta(depth, isMaximizingPlayer, alpha, beta)` - Optimized version
- `findBestMove()` - Find optimal move using basic minimax
- `findBestMoveAlphaBeta()` - Find optimal move using alpha-beta pruning
- `evaluate()` - Board position evaluation function

### Game Features
- **Board Representation**: 3x3 character array
- **Players**: Human ('O') vs AI ('X')
- **Move Validation**: Ensures legal moves only
- **Game State Detection**: Win/lose/draw conditions
- **Interactive Interface**: Command-line based gameplay

## Algorithm Explanation

### Minimax Process
1. **Generate Moves**: List all possible legal moves
2. **Recursive Evaluation**: For each move, evaluate resulting position
3. **Maximize/Minimize**: Choose move that optimizes player's outcome
4. **Backtrack**: Return the best move found

### Alpha-Beta Pruning Process
1. **Initialize**: Alpha = -∞, Beta = +∞
2. **Update Bounds**: Alpha for maximizing, Beta for minimizing player
3. **Prune**: Skip remaining branches when alpha ≥ beta
4. **Efficiency**: Significantly reduces nodes evaluated

## Usage Examples

### Running the Program
```bash
javac ComprehensiveMinimax.java
java ComprehensiveMinimax
```

### Menu Options
1. **Play with Basic Minimax**: Experience full tree search
2. **Play with Alpha-Beta**: Experience optimized search
3. **Performance Comparison**: See efficiency improvements
4. **Concept Explanation**: Learn algorithm details
5. **Test Position**: Analyze specific game states

## Expected Outcomes
- **Perfect Play**: AI never loses when playing optimally
- **Performance**: Alpha-Beta significantly faster than basic Minimax
- **Educational**: Clear demonstration of algorithm concepts

## Applications
- **Board Games**: Chess, Checkers, Othello
- **Card Games**: Various strategic card games
- **Decision Making**: General adversarial search problems
- **Game AI**: Foundation for more complex game algorithms

## Technical Notes
- **Evaluation Function**: Returns +10 for AI win, -10 for human win, 0 for draw
- **Depth Penalty**: Earlier wins/losses are preferred
- **Move Ordering**: Can affect Alpha-Beta efficiency
- **Memory Usage**: Recursive implementation with call stack