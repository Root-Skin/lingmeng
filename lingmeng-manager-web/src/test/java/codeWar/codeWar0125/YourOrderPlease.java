package codeWar.codeWar0125;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年01月25日
 * @Description
 * Your task is to sort a given string. Each word in the string will contain a single number.
 * This number is the position the word should have in the result.
 * Note: Numbers can be from 1 to 9. So 1 will be the first word (not 0).
 * If the input string is empty, return an empty string.
 * The words in the input String will only contain valid consecutive numbers.
 */
public class YourOrderPlease {
    public static void main(String[] args) {

        String words = "is2 Thi1s T4est 3a";
        System.out.println(order(words));

    }
    public static String order(String words) {
        // ...
        String collect = Arrays.asList(words.split(" "))
                .stream()
                .sorted(Comparator.comparing(i -> Integer.parseInt(i.replaceAll("(?i)[a-z]", ""))))
                .collect(Collectors.joining(" "));
        return  collect;
    }
    public static String study(String words) {
        return Arrays.stream(words.split(" "))
                .sorted(Comparator.comparing(s -> Integer.valueOf(s.replaceAll("\\D", ""))))
                .reduce((a, b) -> a + " " + b).get();
    }
}
