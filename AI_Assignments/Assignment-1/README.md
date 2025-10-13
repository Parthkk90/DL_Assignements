# Assignment-1: Search Algorithms for 8-Puzzle Problem

## Overview
Implementation of Depth-First Search (DFS) and Breadth-First Search (BFS) algorithms to solve the classic 8-puzzle problem.

## Files
- **`PuzzleDFS.java`** - Depth-First Search implementation
- **`PuzzleBFS.java`** - Breadth-First Search implementation

## Problem Description
The 8-puzzle consists of a 3x3 grid with 8 numbered tiles and one empty space. The goal is to arrange the tiles in numerical order by sliding them into the empty space.

**Goal State:**
```
1 2 3
4 5 6
7 8  
```

## Features

### DFS Implementation
- **Stack-based exploration** (LIFO)
- **Depth limiting** to prevent infinite search
- **Memory efficient** but may not find optimal solution
- **Duplicate state detection**

### BFS Implementation
- **Queue-based exploration** (FIFO)
- **Guarantees optimal solution** (minimum moves)
- **Complete solution path tracking**
- **Step-by-step move visualization**

## How to Run
```bash
# Compile and run DFS
javac PuzzleDFS.java
java PuzzleDFS

# Compile and run BFS
javac PuzzleBFS.java
java PuzzleBFS
```

## Key Concepts Demonstrated
- **State space search**
- **Graph traversal algorithms**
- **Duplicate detection using visited states**
- **Move generation and validation**
- **Solution path reconstruction**

## Example Output
Both programs solve a sample 8-puzzle and display:
- Initial puzzle state
- Solution steps (BFS shows complete path)
- Execution time
- Number of nodes explored