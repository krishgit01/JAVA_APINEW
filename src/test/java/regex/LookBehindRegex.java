package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LookBehindRegex {
    public static void main(String[] args) {

        // Example 1: Your original example
        System.out.println("=== EXAMPLE 1: Basic Lookbehind ===");
        String text1 = "100USD 200INR";

        String positiveLookBehind = "(?<=\\d)USD";
        System.out.println("Text: " + text1);
        System.out.println("Pattern: " + positiveLookBehind + " (USD preceded by digit)");
        patternMatcher(text1, positiveLookBehind);

        String negativeLookBehind = "(?<!\\d)USD";
        System.out.println("\nPattern: " + negativeLookBehind + " (USD NOT preceded by digit)");
        patternMatcher(text1, negativeLookBehind);


        // Example 2: Better example for negative lookbehind
        System.out.println("\n=== EXAMPLE 2: Mix of currencies ===");
        String text2 = "100USD USD 200INR INR";

        System.out.println("Text: " + text2);
        System.out.println("Pattern: " + positiveLookBehind + " (USD preceded by digit)");
        patternMatcher(text2, positiveLookBehind);

        System.out.println("\nPattern: " + negativeLookBehind + " (USD NOT preceded by digit)");
        patternMatcher(text2, negativeLookBehind);


        // Example 3: Extract just the numbers before currency
        System.out.println("\n=== EXAMPLE 3: Extract numbers BEFORE currency ===");
        String text3 = "100USD 200INR 50EUR";
        String extractNumbers = "\\d+(?=USD|INR|EUR)";

        System.out.println("Text: " + text3);
        System.out.println("Pattern: " + extractNumbers + " (numbers followed by currency)");
        patternMatcher(text3, extractNumbers);


        // Example 4: Show start and end positions
        System.out.println("\n=== EXAMPLE 4: Understanding positions ===");
        String text4 = "Price: 100USD";
        System.out.println("Text: '" + text4 + "'");
        System.out.println("Indices: 0123456789...");

        System.out.println("\nPattern: (?<=\\d)USD");
        patternMatcherWithPositions(text4, "(?<=\\d)USD");

        System.out.println("\nPattern: \\d+USD (without lookbehind)");
        patternMatcherWithPositions(text4, "\\d+USD");
    }

    private static void patternMatcher(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        boolean found = false;
        while (matcher.find()) {
            System.out.println("  Found: '" + matcher.group() + "'");
            found = true;
        }

        if (!found) {
            System.out.println("  No match found!");
        }
    }

    // Enhanced version showing positions
    private static void patternMatcherWithPositions(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        boolean found = false;
        while (matcher.find()) {
            System.out.println("  Found: '" + matcher.group() + "'");
            System.out.println("  Start index: " + matcher.start());
            System.out.println("  End index: " + matcher.end());
            found = true;
        }

        if (!found) {
            System.out.println("  No match found!");
        }
    }
}