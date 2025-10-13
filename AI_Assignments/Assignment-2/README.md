# Assignment-2: Constraint Satisfaction Problems (CSP)

## Overview
Implementation of three classic Constraint Satisfaction Problems using backtracking algorithms with simple, easy-to-understand logic.

## Files
- **`NQueensCSP.java`** - N-Queens problem solver
- **`GraphColoringCSP.java`** - Graph coloring problem solver
- **`SudokuCSP.java`** - Sudoku puzzle solver

## Problems Implemented

### 1. N-Queens Problem
Place N queens on an N×N chessboard so that no two queens attack each other.
- **Variables:** Queen positions in each row
- **Domain:** Column positions (0 to N-1)
- **Constraints:** No two queens on same row, column, or diagonal
- **Features:** Find first solution or enumerate all solutions

### 2. Graph Coloring Problem
Color vertices of a graph such that no two adjacent vertices have the same color.
- **Variables:** Color assignment for each vertex
- **Domain:** Available colors (0 to k-1)
- **Constraints:** Adjacent vertices must have different colors
- **Examples:** Triangle, square, and complete graphs
- **Features:** Minimum color detection

### 3. Sudoku Solver
Fill a 9×9 grid with digits 1-9 following Sudoku rules.
- **Variables:** Numbers in empty cells
- **Domain:** Numbers 1-9
- **Constraints:** Row, column, and 3×3 subgrid uniqueness
- **Features:** Visual board display with proper formatting

## How to Run
```bash
# Compile and run each CSP solver
javac NQueensCSP.java
java NQueensCSP

javac GraphColoringCSP.java
java GraphColoringCSP

javac SudokuCSP.java
java SudokuCSP
```

## Key CSP Concepts Demonstrated
- **Backtracking algorithm**
- **Constraint checking and propagation**
- **Variable assignment and domain reduction**
- **Solution validation**
- **Performance measurement**

## Example Results
- **N-Queens:** Shows board configurations with queen placements
- **Graph Coloring:** Displays vertex-color assignments with validation
- **Sudoku:** Visualizes solved puzzle with clear formatting

All implementations use simple, clear logic without complex optimizations, making them excellent for learning CSP fundamentals.