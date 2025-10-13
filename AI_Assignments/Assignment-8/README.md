# Assignment-8: Backward Chaining Algorithm

## Overview
Implementation of Backward Chaining algorithm for logical inference using goal-driven reasoning. The system works backward from a goal to determine if it can be proven using available facts and rules.

## Files
- **`Backwardchain.java`** - Complete backward chaining implementation with multiple examples

## Algorithm Description
Backward Chaining is a **goal-driven** inference method that:
1. **Starts with a goal** to prove
2. **Searches for rules** that could establish the goal
3. **Recursively proves** the conditions of applicable rules
4. **Succeeds** if all subgoals can be proven
5. **Fails** if no proof path exists

## How Backward Chaining Works
```
Goal: "tweety can fly"

Step 1: Find rule → IF tweety is bird THEN tweety can fly
Step 2: New goal → "tweety is bird"
Step 3: Find rule → IF tweety has feathers AND tweety has wings THEN tweety is bird
Step 4: Prove → "tweety has feathers" ✓ (fact)
Step 5: Prove → "tweety has wings" ✓ (fact)
Step 6: Conclude → "tweety is bird" ✓
Step 7: Conclude → "tweety can fly" ✓

Result: GOAL PROVEN!
```

## Key Features

### Core Functionality
- **Goal-driven reasoning:** Starts with target conclusion
- **Rule-based inference:** Uses IF-THEN rules for proof
- **Recursive proof:** Breaks complex goals into subgoals
- **Cycle detection:** Prevents infinite loops during proof
- **Visual proof tracking:** Shows step-by-step reasoning process

### Condition Handling
- **AND conditions:** All parts must be proven true
- **OR conditions:** At least one part must be true
- **Simple conditions:** Direct fact verification

### Visual Output
Uses emoji indicators for clear understanding:
- 🎯 Goal to prove
- ✅ Success/Fact found
- ❌ Failure/Cannot prove
- 📋 Rule application
- 🔗 AND condition
- 🔄 OR condition

## Example Domains

### 1. Animal Classification
**Goal:** Prove "tweety can fly"
- Uses animal characteristics to classify and deduce abilities

### 2. Medical Diagnosis
**Goal:** Prove "patient should see doctor"
- Uses symptoms and medical rules for diagnosis

### 3. Family Relationships
**Goal:** Prove "alice has grandfather"
- Uses family facts to establish relationships

### 4. Detective Mystery
**Goal:** Prove "investigate john"
- Uses evidence and detective reasoning to reach conclusions

## Interactive Mode
The system includes an interactive query interface:
- **Commands:** `prove <goal>`, `facts`, `rules`, `quit`
- **Real-time queries:** Test any goal against the knowledge base
- **Knowledge inspection:** View current facts and rules

## How to Run
```bash
# Compile and run
javac Backwardchain.java
java Backwardchain
```

## Sample Session
```
Query: prove tweety can fly

🎯 Goal: tweety can fly
📋 Trying rule: IF tweety is bird THEN tweety can fly
  🎯 Goal: tweety is bird
  📋 Trying rule: IF tweety has feathers and tweety has wings THEN tweety is bird
    🔗 Proving AND condition: tweety has feathers and tweety has wings
      🎯 Goal: tweety has feathers
      ✅ Found as fact: tweety has feathers
      🎯 Goal: tweety has wings
      ✅ Found as fact: tweety has wings
    ✅ AND succeeded: all parts proven
  ✅ Rule succeeded: tweety is bird proven!
✅ Rule succeeded: tweety can fly proven!

Result: tweety can fly is TRUE
```

## Key AI Concepts Demonstrated
- **Goal-oriented problem solving**
- **Logical inference and proof**
- **Rule-based expert systems**
- **Recursive reasoning strategies**
- **Knowledge base querying**

This implementation shows how AI systems can work backward from desired conclusions to determine if they can be logically proven from available knowledge.