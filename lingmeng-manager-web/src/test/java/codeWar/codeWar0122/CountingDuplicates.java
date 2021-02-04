package codeWar01.codeWar0122;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author skin
 * @createTime 2021年01月22日
 * @Description
 * Count the number of Duplicates
 * Write a function that will return the count of distinct case-insensitive alphabetic characters
 * and numeric digits that occur more than once in the input string.
 * The input string can be assumed to contain only alphabets (both uppercase and lowercase) and numeric digits.
 */
public class CountingDuplicates {
    public static void main(String[] args) {

        String text = "bBbaaccde";
        System.out.println(duplicateCount(text));
    }

    public static int duplicateCount(String text) {
        // Write your code here
        int length = Arrays
                .stream(text.toLowerCase().split(""))
                .collect(Collectors.groupingBy(String::toString, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(i -> i.getValue() > 1)
                .map(Map.Entry::getKey).collect(Collectors.joining()).length();

        return length;

    }

    public static int study(String text) {
//        int ans = 0;
//        text = text.toLowerCase();
//        while (text.length() > 0) {
//            String firstLetter = text.substring(0,1);
//            text = text.substring(1);
//            if (text.contains(firstLetter)) ans ++;
//            text = text.replace(firstLetter, "");
//        }
//        return ans;

//        return (int) charFrequenciesMap(text).values().stream()
//                .filter(i -> i > 1)
//                .count();


        return (int)text.toLowerCase()
                .chars()
                .boxed()
                .collect(Collectors.groupingBy(k->k,Collectors.counting()))
                .values()
                .stream()
                .filter(e->e>1).count();
    }

    private static Map<Character, Long> charFrequenciesMap(final String text) {
        return text.codePoints()
                .map(Character::toLowerCase)
                .mapToObj(c -> (char) c)
                .collect(groupingBy(identity(), counting()));
    }
}
