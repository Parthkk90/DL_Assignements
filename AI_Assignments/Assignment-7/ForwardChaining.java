import java.util.*;

public class ForwardChaining {
    private Set<String> facts;           // Known facts
    private Set<String> rules;           // If-then rules
    private Set<String> inferredFacts;   // Newly derived facts
    private List<String> inferenceSteps; // Track inference process
    
    public ForwardChaining() {
        facts = new HashSet<>();
        rules = new HashSet<>();
        inferredFacts = new HashSet<>();
        inferenceSteps = new ArrayList<>();
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
    
    // Check if a condition is satisfied by current facts
    private boolean isConditionSatisfied(String condition) {
        condition = condition.trim();
        
        // Handle AND conditions (e.g., "A and B")
        if (condition.contains("and")) {
            String[] andParts = condition.split("and");
            for (String part : andParts) {
                if (!facts.contains(part.trim()) && !inferredFacts.contains(part.trim())) {
                    return false;
                }
            }
            return true;
        }
        
        // Handle OR conditions (e.g., "A or B")
        if (condition.contains("or")) {
            String[] orParts = condition.split("or");
            for (String part : orParts) {
                if (facts.contains(part.trim()) || inferredFacts.contains(part.trim())) {
                    return true;
                }
            }
            return false;
        }
        
        // Simple condition (single fact)
        return facts.contains(condition) || inferredFacts.contains(condition);
    }
    
    // Main forward chaining algorithm
    public void performForwardChaining() {
        System.out.println("=== FORWARD CHAINING ALGORITHM ===");
        System.out.println("Initial Facts: " + facts);
        System.out.println("Rules: " + rules);
        System.out.println();
        
        boolean newFactsAdded = true;
        int iteration = 0;
        
        while (newFactsAdded) {
            iteration++;
            newFactsAdded = false;
            Set<String> currentIterationFacts = new HashSet<>();
            
            System.out.println("Iteration " + iteration + ":");
            
            // Check each rule
            for (String rule : rules) {
                String[] parsed = parseRule(rule);
                if (parsed != null) {
                    String condition = parsed[0];
                    String conclusion = parsed[1];
                    
                    // If condition is satisfied and conclusion is not already known
                    if (isConditionSatisfied(condition) && 
                        !facts.contains(conclusion) && 
                        !inferredFacts.contains(conclusion)) {
                        
                        currentIterationFacts.add(conclusion);
                        inferredFacts.add(conclusion);
                        newFactsAdded = true;
                        
                        String step = "Rule applied: IF " + condition + " THEN " + conclusion + 
                                     " → Inferred: " + conclusion;
                        inferenceSteps.add(step);
                        System.out.println("  " + step);
                    }
                }
            }
            
            if (!newFactsAdded) {
                System.out.println("  No new facts inferred.");
            }
            
            System.out.println();
        }
        
        System.out.println("Forward chaining completed in " + iteration + " iterations.");
        System.out.println("Total inferred facts: " + inferredFacts.size());
    }
    
    // Query if a fact can be inferred
    public boolean query(String queryFact) {
        queryFact = queryFact.trim().toLowerCase();
        
        if (facts.contains(queryFact)) {
            System.out.println("'" + queryFact + "' is a known fact.");
            return true;
        }
        
        if (inferredFacts.contains(queryFact)) {
            System.out.println("'" + queryFact + "' can be inferred through forward chaining.");
            return true;
        }
        
        System.out.println("'" + queryFact + "' cannot be proven with current knowledge.");
        return false;
    }
    
    // Print all facts (original and inferred)
    public void printAllFacts() {
        System.out.println("=== ALL FACTS ===");
        System.out.println("Original Facts: " + facts);
        System.out.println("Inferred Facts: " + inferredFacts);
        
        Set<String> allFacts = new HashSet<>(facts);
        allFacts.addAll(inferredFacts);
        System.out.println("Combined Facts: " + allFacts);
        System.out.println();
    }
    
    // Print inference steps
    public void printInferenceSteps() {
        System.out.println("=== INFERENCE STEPS ===");
        for (int i = 0; i < inferenceSteps.size(); i++) {
            System.out.println((i + 1) + ". " + inferenceSteps.get(i));
        }
        System.out.println();
    }
    
    // Example 1: Animal classification
    public static void animalExample() {
        System.out.println("EXAMPLE 1: ANIMAL CLASSIFICATION");
        System.out.println("=".repeat(50));
        
        ForwardChaining fc = new ForwardChaining();
        
        // Facts
        fc.addFact("tweety has feathers");
        fc.addFact("tweety has wings");
        fc.addFact("fido has fur");
        fc.addFact("fido barks");
        
        // Rules
        fc.addRule("IF tweety has feathers and tweety has wings THEN tweety is a bird");
        fc.addRule("IF tweety is a bird THEN tweety can fly");
        fc.addRule("IF fido has fur and fido barks THEN fido is a dog");
        fc.addRule("IF fido is a dog THEN fido is a mammal");
        fc.addRule("IF fido is a mammal THEN fido is warm-blooded");
        
        // Perform forward chaining
        fc.performForwardChaining();
        fc.printAllFacts();
        fc.printInferenceSteps();
        
        // Test queries
        System.out.println("=== QUERIES ===");
        fc.query("tweety can fly");
        fc.query("fido is warm-blooded");
        fc.query("tweety is a mammal");
        System.out.println();
    }
    
    // Example 2: Medical diagnosis
    public static void medicalExample() {
        System.out.println("EXAMPLE 2: MEDICAL DIAGNOSIS");
        System.out.println("=".repeat(50));
        
        ForwardChaining fc = new ForwardChaining();
        
        // Facts (symptoms)
        fc.addFact("patient has fever");
        fc.addFact("patient has cough");
        fc.addFact("patient has headache");
        
        // Rules
        fc.addRule("IF patient has fever and patient has cough THEN patient has flu");
        fc.addRule("IF patient has flu THEN patient needs rest");
        fc.addRule("IF patient has fever and patient has headache THEN patient might have cold");
        fc.addRule("IF patient has flu or patient might have cold THEN patient needs medication");
        fc.addRule("IF patient needs medication THEN patient should see doctor");
        
        fc.performForwardChaining();
        fc.printAllFacts();
        fc.printInferenceSteps();
        
        System.out.println("=== QUERIES ===");
        fc.query("patient should see doctor");
        fc.query("patient needs rest");
        fc.query("patient has pneumonia");
        System.out.println();
    }
    
    // Example 3: Simple family relationships
    public static void familyExample() {
        System.out.println("EXAMPLE 3: FAMILY RELATIONSHIPS");
        System.out.println("=".repeat(50));
        
        ForwardChaining fc = new ForwardChaining();
        
        // Facts
        fc.addFact("john is father of mary");
        fc.addFact("mary is mother of alice");
        fc.addFact("john is male");
        fc.addFact("mary is female");
        
        // Rules
        fc.addRule("IF john is father of mary and mary is mother of alice THEN john is grandfather of alice");
        fc.addRule("IF john is male and john is grandfather of alice THEN john is alice's grandfather");
        fc.addRule("IF mary is female and mary is mother of alice THEN mary is alice's mother");
        fc.addRule("IF john is grandfather of alice THEN alice has grandfather");
        
        fc.performForwardChaining();
        fc.printAllFacts();
        fc.printInferenceSteps();
        
        System.out.println("=== QUERIES ===");
        fc.query("alice has grandfather");
        fc.query("john is alice's grandfather");
        fc.query("mary is alice's mother");
        System.out.println();
    }
    
    public static void main(String[] args) {
        // Run all examples
        animalExample();
        medicalExample();
        familyExample();
        
        // Interactive example
        System.out.println("INTERACTIVE EXAMPLE");
        System.out.println("=".repeat(50));
        
        ForwardChaining fc = new ForwardChaining();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter facts (type 'done' when finished):");
        while (true) {
            System.out.print("Fact: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;
            if (!input.isEmpty()) {
                fc.addFact(input);
            }
        }
        
        System.out.println("\nEnter rules in format 'IF condition THEN conclusion' (type 'done' when finished):");
        while (true) {
            System.out.print("Rule: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;
            if (!input.isEmpty()) {
                fc.addRule(input);
            }
        }
        
        fc.performForwardChaining();
        fc.printAllFacts();
        fc.printInferenceSteps();
        
        System.out.println("\nEnter queries (type 'quit' to exit):");
        while (true) {
            System.out.print("Query: ");
            String query = scanner.nextLine().trim();
            if (query.equalsIgnoreCase("quit")) break;
            if (!query.isEmpty()) {
                fc.query(query);
            }
        }
        
        scanner.close();
    }
}
