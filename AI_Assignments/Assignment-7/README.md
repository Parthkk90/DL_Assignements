# Assignment-7: Forward Chaining Algorithm

## Overview
Implementation of Forward Chaining algorithm for logical inference using simple, easy-to-understand logic. The system automatically derives new facts from existing knowledge using IF-THEN rules.

## Files
- **`ForwardChaining.java`** - Comprehensive forward chaining implementation
- **`forward_chain.java`** - Simplified version with basic functionality

## Algorithm Description
Forward Chaining is a **data-driven** inference method that:
1. **Starts with known facts**
2. **Applies rules** to derive new facts
3. **Repeats** until no new facts can be inferred
4. **Converges** when knowledge base is complete

## Features

### Comprehensive Implementation (ForwardChaining.java)
- **Complex Conditions:** Handles AND/OR operations in rules
- **Multiple Examples:** Animal classification, medical diagnosis, family relationships
- **Interactive Mode:** User can input custom facts and rules
- **Detailed Tracking:** Shows inference steps and iterations
- **Query System:** Test if facts can be proven

### Simple Implementation (forward_chain.java)
- **Basic Forward Chaining:** Simple condition → conclusion rules
- **Clear Logic:** Minimal code with straightforward approach
- **Example Scenarios:** Animal and medical diagnosis examples

## How Forward Chaining Works
```
Initial Facts: [it is raining, john has umbrella]

Rules:
- IF it is raining THEN ground gets wet
- IF john has umbrella AND it is raining THEN john stays dry

Iteration 1:
  ✓ Applied: it is raining → ground gets wet
  ✓ Applied: john has umbrella AND it is raining → john stays dry

Final Facts: [it is raining, john has umbrella, ground gets wet, john stays dry]
```

## Example Domains

### 1. Animal Classification
- Facts: "tweety has feathers", "tweety has wings"
- Rules: Classify animals based on characteristics
- Output: Derive "tweety is bird", "tweety can fly"

### 2. Medical Diagnosis
- Facts: Patient symptoms
- Rules: Medical knowledge for diagnosis
- Output: Diagnoses and treatment recommendations

### 3. Detective Mystery
- Facts: Crime scene evidence
- Rules: Detective reasoning
- Output: Suspect identification and actions

## How to Run
```bash
# Run comprehensive version
javac ForwardChaining.java
java ForwardChaining

# Run simple version
javac forward_chain.java
java forward_chain
```

## Interactive Mode
The comprehensive version includes an interactive mode where users can:
- Enter custom facts
- Define IF-THEN rules
- Query specific goals
- See step-by-step inference process

## Key AI Concepts Demonstrated
- **Knowledge-based reasoning**
- **Rule-based expert systems**
- **Automated fact derivation**
- **Inference engine design**
- **Data-driven problem solving**

This implementation showcases how AI systems can automatically reason and derive new knowledge from existing facts using logical rules.