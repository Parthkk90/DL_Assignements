import java.util.*;

public class Backwardchain {
    private Set<String> facts;           // Known facts
    private Set<String> rules;           // If-then rules
    private List<String> proofSteps;     // Track proof process
    private Set<String> visitedGoals;    // Prevent infinite recursion
    
    public Backwardchain() {
        facts = new HashSet<>();
        rules = new HashSet<>();
        proofSteps = new ArrayList<>();
        visitedGoals = new HashSet<>();
    }
    
    // Add a fact to the knowledge base
    public void addFact(String fact) {
        facts.add(fact.trim().toLowerCase());
    }
    
    // Add a rule in the format "IF condition THEN conclusion"
    public void addRule(String rule) {
        rules.add(rule.trim().toLowerCase());
    }
    
    // Parse rule to extract condition and conclusion
    private String[] parseRule(String rule) {
        if (rule.contains("if") && rule.contains("then")) {
            String[] parts = rule.split("then");
            if (parts.length == 2) {
                String condition = parts[0].replace("if", "").trim();
                String conclusion = parts[1].trim();
                return new String[]{condition, conclusion};
            }
        }
        return null;
    }
    
    // Main backward chaining algorithm
    public boolean proveGoal(String goal) {
        goal = goal.trim().toLowerCase();
        
        System.out.println("=== BACKWARD CHAINING ALGORITHM ===");
        System.out.println("Trying to prove: " + goal);
        System.out.println("Known facts: " + facts);
        System.out.println("Rules: " + rules);
        System.out.println();
        
        proofSteps.clear();
        visitedGoals.clear();
        
        boolean result = backwardChain(goal, 0);
        
        System.out.println("=== PROOF STEPS ===");
        for (String step : proofSteps) {
            System.out.println(step);
        }
        
        System.out.println("\nResult: " + goal + " is " + (result ? "TRUE" : "FALSE"));
        return result;
    }
    
    // Recursive backward chaining function
    private boolean backwardChain(String goal, int depth) {
        String indent = "  ".repeat(depth);
        
        // Prevent infinite recursion
        if (visitedGoals.contains(goal)) {
            proofSteps.add(indent + "❌ Already visited: " + goal + " (avoiding infinite loop)");
            return false;
        }
        
        visitedGoals.add(goal);
        proofSteps.add(indent + "🎯 Goal: " + goal);
        
        // Check if goal is a known fact
        if (facts.contains(goal)) {
            proofSteps.add(indent + "✅ Found as fact: " + goal);
            visitedGoals.remove(goal);
            return true;
        }
        
        // Try to prove goal using rules
        for (String rule : rules) {
            String[] parsed = parseRule(rule);
            if (parsed != null) {
                String condition = parsed[0];
                String conclusion = parsed[1];
                
                // If this rule can prove our goal
                if (conclusion.equals(goal)) {
                    proofSteps.add(indent + "📋 Trying rule: IF " + condition + " THEN " + conclusion);
                    
                    // Try to prove the condition
                    if (proveCondition(condition, depth + 1)) {
                        proofSteps.add(indent + "✅ Rule succeeded: " + goal + " proven!");
                        visitedGoals.remove(goal);
                        return true;
                    } else {
                        proofSteps.add(indent + "❌ Rule failed: condition not satisfied");
                    }
                }
            }
        }
        
        proofSteps.add(indent + "❌ Cannot prove: " + goal);
        visitedGoals.remove(goal);
        return false;
    }
    
    // Prove a condition (may contain AND/OR)
    private boolean proveCondition(String condition, int depth) {
        condition = condition.trim();
        String indent = "  ".repeat(depth);
        
        // Handle AND conditions
        if (condition.contains(" and ")) {
            String[] andParts = condition.split(" and ");
            proofSteps.add(indent + "🔗 Proving AND condition: " + condition);
            
            for (String part : andParts) {
                if (!backwardChain(part.trim(), depth + 1)) {
                    proofSteps.add(indent + "❌ AND failed: " + part.trim() + " is false");
                    return false;
                }
            }
            proofSteps.add(indent + "✅ AND succeeded: all parts proven");
            return true;
        }
        
        // Handle OR conditions
        if (condition.contains(" or ")) {
            String[] orParts = condition.split(" or ");
            proofSteps.add(indent + "🔄 Proving OR condition: " + condition);
            
            for (String part : orParts) {
                if (backwardChain(part.trim(), depth + 1)) {
                    proofSteps.add(indent + "✅ OR succeeded: " + part.trim() + " is true");
                    return true;
                }
            }
            proofSteps.add(indent + "❌ OR failed: no part is true");
            return false;
        }
        
        // Simple condition (single goal)
        return backwardChain(condition, depth);
    }
    
    // Interactive query system
    public void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== INTERACTIVE BACKWARD CHAINING ===");
        System.out.println("Commands: 'prove <goal>', 'facts', 'rules', 'quit'");
        
        while (true) {
            System.out.print("\nQuery: ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                break;
            } else if (input.equalsIgnoreCase("facts")) {
                System.out.println("Known facts: " + facts);
            } else if (input.equalsIgnoreCase("rules")) {
                System.out.println("Rules:");
                for (String rule : rules) {
                    System.out.println("  " + rule);
                }
            } else if (input.toLowerCase().startsWith("prove ")) {
                String goal = input.substring(6).trim();
                proveGoal(goal);
            } else {
                System.out.println("Invalid command. Try 'prove <goal>', 'facts', 'rules', or 'quit'");
            }
        }
    }
    
    // Example 1: Animal classification
    public static void animalExample() {
        System.out.println("EXAMPLE 1: ANIMAL CLASSIFICATION");
        System.out.println("=".repeat(50));
        
        Backwardchain bc = new Backwardchain();
        
        // Facts
        bc.addFact("tweety has feathers");
        bc.addFact("tweety has wings");
        bc.addFact("fido has fur");
        bc.addFact("fido barks");
        
        // Rules
        bc.addRule("IF tweety has feathers and tweety has wings THEN tweety is bird");
        bc.addRule("IF tweety is bird THEN tweety can fly");
        bc.addRule("IF fido has fur and fido barks THEN fido is dog");
        bc.addRule("IF fido is dog THEN fido is mammal");
        bc.addRule("IF fido is mammal THEN fido is warm-blooded");
        
        // Test queries
        bc.proveGoal("tweety can fly");
        System.out.println("\n" + "-".repeat(30) + "\n");
        bc.proveGoal("fido is warm-blooded");
        System.out.println("\n" + "-".repeat(30) + "\n");
        bc.proveGoal("tweety is mammal");
        System.out.println();
    }
    
    // Example 2: Medical diagnosis
    public static void medicalExample() {
        System.out.println("EXAMPLE 2: MEDICAL DIAGNOSIS");
        System.out.println("=".repeat(50));
        
        Backwardchain bc = new Backwardchain();
        
        // Facts (symptoms)
        bc.addFact("patient has fever");
        bc.addFact("patient has cough");
        bc.addFact("patient has headache");
        
        // Rules
        bc.addRule("IF patient has fever and patient has cough THEN patient has flu");
        bc.addRule("IF patient has flu THEN patient needs rest");
        bc.addRule("IF patient has fever and patient has headache THEN patient has cold");
        bc.addRule("IF patient has flu or patient has cold THEN patient needs medication");
        bc.addRule("IF patient needs medication THEN patient should see doctor");
        
        // Test queries
        bc.proveGoal("patient should see doctor");
        System.out.println("\n" + "-".repeat(30) + "\n");
        bc.proveGoal("patient needs rest");
        System.out.println("\n" + "-".repeat(30) + "\n");
        bc.proveGoal("patient has pneumonia");
        System.out.println();
    }
    
    // Example 3: Family relationships
    public static void familyExample() {
        System.out.println("EXAMPLE 3: FAMILY RELATIONSHIPS");
        System.out.println("=".repeat(50));
        
        Backwardchain bc = new Backwardchain();
        
        // Facts
        bc.addFact("john is father of mary");
        bc.addFact("mary is mother of alice");
        bc.addFact("john is male");
        bc.addFact("mary is female");
        
        // Rules
        bc.addRule("IF john is father of mary and mary is mother of alice THEN john is grandfather of alice");
        bc.addRule("IF john is male and john is grandfather of alice THEN john is alice's grandfather");
        bc.addRule("IF mary is female and mary is mother of alice THEN mary is alice's mother");
        bc.addRule("IF john is grandfather of alice THEN alice has grandfather");
        
        // Test queries
        bc.proveGoal("alice has grandfather");
        System.out.println("\n" + "-".repeat(30) + "\n");
        bc.proveGoal("john is alice's grandfather");
        System.out.println();
    }
    
    // Example 4: Detective mystery (goal-driven reasoning)
    public static void detectiveExample() {
        System.out.println("EXAMPLE 4: DETECTIVE MYSTERY");
        System.out.println("=".repeat(50));
        
        Backwardchain bc = new Backwardchain();
        
        // Facts (evidence)
        bc.addFact("window is broken");
        bc.addFact("safe is empty");
        bc.addFact("john was at scene");
        bc.addFact("john has key");
        
        // Rules (detective reasoning)
        bc.addRule("IF window is broken THEN someone entered house");
        bc.addRule("IF someone entered house and safe is empty THEN robbery occurred");
        bc.addRule("IF john was at scene and john has key THEN john had access");
        bc.addRule("IF robbery occurred and john had access THEN john is suspect");
        bc.addRule("IF john is suspect THEN investigate john");
        
        // Try to prove the conclusion
        bc.proveGoal("investigate john");
        System.out.println();
    }
    
    public static void main(String[] args) {
        // Run examples
        animalExample();
        medicalExample();
        familyExample();
        detectiveExample();
        
        // Interactive mode
        System.out.println("INTERACTIVE MODE");
        System.out.println("=".repeat(50));
        
        Backwardchain bc = new Backwardchain();
        
        // Add some sample data
        bc.addFact("sky is blue");
        bc.addFact("grass is green");
        bc.addFact("sun is shining");
        
        bc.addRule("IF sky is blue and sun is shining THEN weather is nice");
        bc.addRule("IF weather is nice THEN good day for picnic");
        bc.addRule("IF grass is green THEN spring season");
        
        System.out.println("Sample knowledge base loaded!");
        System.out.println("Try: 'prove good day for picnic' or 'prove spring season'");
        
        bc.interactiveMode();
    }
}
