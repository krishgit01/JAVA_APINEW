package regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimplePattern {
    public static void main(String[] args) {
        {
            String text = "The cat sat on the mat.";
            Pattern pattern = Pattern.compile("cat");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                System.out.println("Found: " + matcher.group());
                System.out.println("Found 'cat' at index: " + matcher.start());
            } else {
                System.out.println("Not found");
            }
            Pattern pattern1 = Pattern.compile("\\d+");
            String[] parts = pattern1.split("cat123dog456bird");
            System.out.println("Split result:");
            System.out.println(String.join("\n", parts));
            Arrays.stream(parts).forEach(System.out::println);

            boolean matches = Pattern.matches("\\d+", "12345"); // true
            System.out.println("Does '12345' match '\\d+'? " + matches);

            String html = "<div>Hello</div><div>World</div>";
            // Greedy - matches from first < to last >
            Pattern greedyPattern = Pattern.compile("<div>.*</div>");
            Matcher greedyMatcher = greedyPattern.matcher(html);
            if (greedyMatcher.find()) {
                System.out.println("Greedy match: " + greedyMatcher.group());
            }
            // Reluctant - matches minimal content
            Pattern relecutantPattern = Pattern.compile("<div>.*?</div>");
            Matcher reluctantMatcher = relecutantPattern.matcher(html);
            System.out.println("Reluctant matches:");
            while (reluctantMatcher.find()) {
                System.out.println(reluctantMatcher.group());
            }


            // Match lines starting with "Error"
            String log = "Error: File not found\nInfo: Processing\nError: Timeout";
            Pattern pattern2 = Pattern.compile("^Error.*",Pattern.MULTILINE);
            Matcher matcher2 = pattern2.matcher(log);
            while (matcher2.find()) {
                System.out.println(matcher2.group());
            }
        }
    }
}