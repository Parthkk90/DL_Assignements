package Assignment-3;

import java.util.*;

public class familyprasing {
    
    // Knowledge base to store family relationships
    private Map<String, List<String>> knowledgeBase;
    private Set<String> people;
    
    public familyprasing() {
        knowledgeBase = new HashMap<>();
        people = new HashSet<>();
    }
    
    // Add a relationship to the knowledge base
    public void addRelationship(String relation, String person1, String person2) {
        String fact = relation + "(" + person1 + ", " + person2 + ")";
        knowledgeBase.computeIfAbsent(relation, k -> new ArrayList<>()).add(fact);
        people.add(person1);
        people.add(person2);
    }
    
    // Parse and add family facts
    public void parseFamilyFacts() {
        // Sample family tree data
        // Parents
        addRelationship("parent", "John", "Alice");
        addRelationship("parent", "John", "Bob");
        addRelationship("parent", "Mary", "Alice");
        addRelationship("parent", "Mary", "Bob");
        addRelationship("parent", "Alice", "Charlie");
        addRelationship("parent", "Alice", "Diana");
        addRelationship("parent", "Bob", "Eve");
        addRelationship("parent", "Bob", "Frank");
        
        // Spouses
        addRelationship("spouse", "John", "Mary");
        addRelationship("spouse", "Mary", "John");
        addRelationship("spouse", "Alice", "David");
        addRelationship("spouse", "David", "Alice");
        addRelationship("spouse", "Bob", "Carol");
        addRelationship("spouse", "Carol", "Bob");
        
        // Gender information
        addRelationship("male", "John", "true");
        addRelationship("male", "Bob", "true");
        addRelationship("male", "Charlie", "true");
        addRelationship("male", "Frank", "true");
        addRelationship("male", "David", "true");
        addRelationship("female", "Mary", "true");
        addRelationship("female", "Alice", "true");
        addRelationship("female", "Diana", "true");
        addRelationship("female", "Eve", "true");
        addRelationship("female", "Carol", "true");
        
        // Derive additional relationships
        deriveRelationships();
    }
    
    // Derive additional relationships from base facts
    public void deriveRelationships() {
        deriveChildRelations();
        deriveSiblingRelations();
        deriveGrandparentRelations();
        deriveUncleAuntRelations();
        deriveCousins();
    }
    
    // Derive child relationships from parent relationships
    private void deriveChildRelations() {
        List<String> parentFacts = knowledgeBase.getOrDefault("parent", new ArrayList<>());
        for (String fact : parentFacts) {
            String[] parts = extractRelationParts(fact);
            if (parts != null) {
                addRelationship("child", parts[1], parts[0]);
            }
        }
    }
    
    // Derive sibling relationships
    private void deriveSiblingRelations() {
        List<String> parentFacts = knowledgeBase.getOrDefault("parent", new ArrayList<>());
        Map<String, List<String>> parentChildren = new HashMap<>();
        
        // Group children by parent
        for (String fact : parentFacts) {
            String[] parts = extractRelationParts(fact);
            if (parts != null) {
                parentChildren.computeIfAbsent(parts[0], k -> new ArrayList<>()).add(parts[1]);
            }
        }
        
        // Create sibling relationships
        for (List<String> children : parentChildren.values()) {
            for (int i = 0; i < children.size(); i++) {
                for (int j = i + 1; j < children.size(); j++) {
                    addRelationship("sibling", children.get(i), children.get(j));
                    addRelationship("sibling", children.get(j), children.get(i));
                }
            }
        }
    }
    
    // Derive grandparent relationships
    private void deriveGrandparentRelations() {
        List<String> parentFacts = knowledgeBase.getOrDefault("parent", new ArrayList<>());
        
        for (String parentFact1 : parentFacts) {
            String[] parts1 = extractRelationParts(parentFact1);
            if (parts1 != null) {
                for (String parentFact2 : parentFacts) {
                    String[] parts2 = extractRelationParts(parentFact2);
                    if (parts2 != null && parts1[1].equals(parts2[0])) {
                        addRelationship("grandparent", parts1[0], parts2[1]);
                        addRelationship("grandchild", parts2[1], parts1[0]);
                    }
                }
            }
        }
    }
    
    // Derive uncle/aunt relationships
    private void deriveUncleAuntRelations() {
        List<String> parentFacts = knowledgeBase.getOrDefault("parent", new ArrayList<>());
        List<String> siblingFacts = knowledgeBase.getOrDefault("sibling", new ArrayList<>());
        
        for (String parentFact : parentFacts) {
            String[] parentParts = extractRelationParts(parentFact);
            if (parentParts != null) {
                for (String siblingFact : siblingFacts) {
                    String[] siblingParts = extractRelationParts(siblingFact);
                    if (siblingParts != null && parentParts[0].equals(siblingParts[0])) {
                        if (isMale(siblingParts[1])) {
                            addRelationship("uncle", siblingParts[1], parentParts[1]);
                            addRelationship("nephew_niece", parentParts[1], siblingParts[1]);
                        } else if (isFemale(siblingParts[1])) {
                            addRelationship("aunt", siblingParts[1], parentParts[1]);
                            addRelationship("nephew_niece", parentParts[1], siblingParts[1]);
                        }
                    }
                }
            }
        }
    }
    
    // Derive cousin relationships
    private void deriveCousins() {
        List<String> uncleAuntFacts = new ArrayList<>();
        uncleAuntFacts.addAll(knowledgeBase.getOrDefault("uncle", new ArrayList<>()));
        uncleAuntFacts.addAll(knowledgeBase.getOrDefault("aunt", new ArrayList<>()));
        
        List<String> parentFacts = knowledgeBase.getOrDefault("parent", new ArrayList<>());
        
        for (String uncleAuntFact : uncleAuntFacts) {
            String[] uaParts = extractRelationParts(uncleAuntFact);
            if (uaParts != null) {
                for (String parentFact : parentFacts) {
                    String[] parentParts = extractRelationParts(parentFact);
                    if (parentParts != null && uaParts[0].equals(parentParts[0])) {
                        addRelationship("cousin", uaParts[1], parentParts[1]);
                        addRelationship("cousin", parentParts[1], uaParts[1]);
                    }
                }
            }
        }
    }
    
    // Helper method to check if person is male
    private boolean isMale(String person) {
        List<String> maleFacts = knowledgeBase.getOrDefault("male", new ArrayList<>());
        return maleFacts.stream().anyMatch(fact -> fact.contains(person + ", true"));
    }
    
    // Helper method to check if person is female
    private boolean isFemale(String person) {
        List<String> femaleFacts = knowledgeBase.getOrDefault("female", new ArrayList<>());
        return femaleFacts.stream().anyMatch(fact -> fact.contains(person + ", true"));
    }
    
    // Extract relation parts from fact string
    private String[] extractRelationParts(String fact) {
        try {
            int start = fact.indexOf('(') + 1;
            int end = fact.indexOf(')');
            String content = fact.substring(start, end);
            return content.split(", ");
        } catch (Exception e) {
            return null;
        }
    }
    
    // Query relationships
    public List<String> queryRelation(String relation, String person) {
        List<String> results = new ArrayList<>();
        List<String> facts = knowledgeBase.getOrDefault(relation, new ArrayList<>());
        
        for (String fact : facts) {
            String[] parts = extractRelationParts(fact);
            if (parts != null && parts[0].equals(person)) {
                results.add(parts[1]);
            }
        }
        
        return results;
    }
    
    // Query specific relationship between two people
    public boolean hasRelationship(String relation, String person1, String person2) {
        List<String> facts = knowledgeBase.getOrDefault(relation, new ArrayList<>());
        String targetFact = relation + "(" + person1 + ", " + person2 + ")";
        return facts.contains(targetFact);
    }
    
    // Get all relationships for a person
    public Map<String, List<String>> getAllRelationships(String person) {
        Map<String, List<String>> relationships = new HashMap<>();
        
        for (String relation : knowledgeBase.keySet()) {
            List<String> related = queryRelation(relation, person);
            if (!related.isEmpty()) {
                relationships.put(relation, related);
            }
        }
        
        return relationships;
    }
    
    // Display the family tree
    public void displayFamilyTree() {
        System.out.println("=== FAMILY TREE KNOWLEDGE BASE ===\n");
        
        for (String person : people) {
            System.out.println("Relationships for " + person + ":");
            Map<String, List<String>> relationships = getAllRelationships(person);
            
            for (Map.Entry<String, List<String>> entry : relationships.entrySet()) {
                String relation = entry.getKey();
                List<String> related = entry.getValue();
                
                if (!related.isEmpty() && !relation.equals("male") && !relation.equals("female")) {
                    System.out.println("  " + relation + ": " + String.join(", ", related));
                }
            }
            System.out.println();
        }
    }
    
    // Interactive query system
    public void interactiveQuery(Scanner scanner) {
        System.out.println("\n=== INTERACTIVE FAMILY TREE QUERY ===");
        System.out.println("Available relations: parent, child, sibling, grandparent, grandchild, uncle, aunt, nephew_niece, cousin, spouse");
        System.out.println("Available people: " + String.join(", ", people));
        System.out.println("Type 'exit' to quit\n");
        
        while (true) {
            System.out.print("Enter query (relation person) or 'all person': ");
            String input = scanner.nextLine().trim();
            
            if (input.equals("exit")) {
                break;
            }
            
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                System.out.println("Invalid format. Use: relation person");
                continue;
            }
            
            String relation = parts[0];
            String person = parts[1];
            
            if (relation.equals("all")) {
                Map<String, List<String>> allRel = getAllRelationships(person);
                System.out.println("All relationships for " + person + ":");
                for (Map.Entry<String, List<String>> entry : allRel.entrySet()) {
                    if (!entry.getKey().equals("male") && !entry.getKey().equals("female")) {
                        System.out.println("  " + entry.getKey() + ": " + String.join(", ", entry.getValue()));
                    }
                }
            } else {
                List<String> results = queryRelation(relation, person);
                if (results.isEmpty()) {
                    System.out.println("No " + relation + " found for " + person);
                } else {
                    System.out.println(person + "'s " + relation + ": " + String.join(", ", results));
                }
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        familyprasing parser = new familyprasing();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== FAMILY TREE PARSER WITH KNOWLEDGE BASE ===\n");
        
        // Parse family facts
        System.out.println("Parsing family facts...");
        parser.parseFamilyFacts();
        System.out.println("Family facts parsed successfully!\n");
        
        // Display the complete family tree
        parser.displayFamilyTree();
        
        // Interactive query system
        parser.interactiveQuery(scanner);
        
        // Example queries
        System.out.println("=== EXAMPLE QUERIES ===");
        System.out.println("Alice's children: " + parser.queryRelation("child", "Alice"));
        System.out.println("Bob's siblings: " + parser.queryRelation("sibling", "Bob"));
        System.out.println("Charlie's grandparents: " + parser.queryRelation("grandparent", "Charlie"));
        System.out.println("Is John a parent of Alice? " + parser.hasRelationship("parent", "John", "Alice"));
        System.out.println("Eve's cousins: " + parser.queryRelation("cousin", "Eve"));
        
        scanner.close();
    }
}
