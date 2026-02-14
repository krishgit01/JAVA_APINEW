package regex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateRegex {
    public static void main(String[] args) {
        String text = "{VC.##siteid##.region.##env##.service.##servicename##.##version##testing}";
        String regex = "##(\\w+)##";
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("siteid", "INIDIA");
        valuesMap.put("env", "prod");

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        StringBuffer resultBuff = new StringBuffer();
        //classic loop approach
        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = valuesMap.getOrDefault(key, matcher.group());
            matcher.appendReplacement(resultBuff, replacement);
        }
        matcher.appendTail(resultBuff);
        System.out.println("resultBuff value is : " + resultBuff.toString());

        //lambda approach
        String result = matcher.replaceAll(match -> {
            String key = match.group(1);
            return valuesMap.getOrDefault(key, matcher.group());
        });
        System.out.println("result value is : " + result);
    }
}
