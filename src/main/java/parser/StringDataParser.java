package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringDataParser {
    public static Integer extractNumber(String str) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(str);
        return m.find() ? Integer.parseInt(m.group()): null;
    }

    public static Integer extractNumber(String str, int index) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(str);

        for (int i = 0; i <= index; i++)
            m.find();

        return Integer.valueOf(m.group());
    }
}
