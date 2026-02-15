package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LookAheadRegex {
    public static void main(String[] args) {
        String text = "100USD 200INR";
        String PositiveLookAheadRegex = "\\d+(?=USD)"; // Positive Lookahead for 'USD'
        patternMatcher(text, PositiveLookAheadRegex);

        String NegativeLookAheadRegex = "\\d+(?!USD)"; // Negative Lookahead for 'USD'
        patternMatcher(text, NegativeLookAheadRegex);
    }

    private static void patternMatcher(String text, String regex) {
        System.out.println("-----------------------------");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println("Found: " + matcher.group());
        }
    }
}
