package codeWar.codeWar0124;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年01月24日
 * @Description The goal of this exercise is to convert a string to a new string where each character
 * in the new string is "(" if that character appears only once in the original string,
 * or ")" if that character appears more than once in the original string.
 * Ignore capitalization when determining if a character is a duplicate.
 */
public class DuplicateEncoder {
    public static void main(String[] args) {

        String word = "Success";
        System.out.println(encode(word));
    }

    static String encode(String word) {

        StringBuilder result = new StringBuilder();
        String[] split = word.toLowerCase().split("");


        Map<String, Long> collect = Arrays.stream(word.toLowerCase().split(""))
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));

        for (String s : split) {
            if (collect.get(s) > 1) {
                result.append(s.replace(s, ")"));
            } else {
                result.append(s.replace(s, "("));
            }
        }
        return result.toString();
    }

    static String study(String word) {
//        word = word.toLowerCase();
//        String result = "";
//        for (int i = 0; i < word.length(); ++i) {
//            char c = word.charAt(i);
//            result += word.lastIndexOf(c) == word.indexOf(c) ? "(" : ")";
//        }
//        return result;

        return word.toLowerCase()
                .chars()
                .mapToObj(i -> String.valueOf((char)i))
                .map(i -> word.toLowerCase().indexOf(i) == word.toLowerCase().lastIndexOf(i) ? "(" : ")")
                .collect(Collectors.joining());
    }


}
