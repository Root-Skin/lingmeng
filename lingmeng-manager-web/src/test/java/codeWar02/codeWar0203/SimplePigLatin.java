package codeWar02.codeWar0203;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author skin
 * @createTime 2021年02月03日
 * @Description
 * Move the first letter of each word to the end of it,
 * then add "ay" to the end of the word. Leave punctuation marks untouched.
 */
public class SimplePigLatin {
    public static void main(String[] args) {

        String str  = "![]";
        System.out.println(pigIt(str));
    }
    public static String pigIt(String str) {
        // Write code here
        String collect = Stream.of(str.split(" ")).map(i -> {
            String temp = i.replaceAll("\\p{P}", "");
            StringBuilder result = new StringBuilder();
            if (temp.length() >= 1) {
                String begin = temp.substring(0, 1);
                if (temp.length() >= 2) {
                    String remain = temp.substring(1, temp.length());
                    result.append(remain + begin + "ay");
                } else {
                    result.append(begin + "ay");
                }
            } else if (temp.length() == 0) {
                    result .append(i);
            }
            return result;
        }).collect(Collectors.joining(" "));
        return  collect;
    }

    public static String study(String str) {
        return str.replaceAll("(\\w)(\\w*)", "$2$1ay");
    }
}
