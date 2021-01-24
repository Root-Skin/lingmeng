package codeWar.codeWar0121;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author skin
 * @createTime 2021年01月21日
 * @Description Simple, given a string of words, return the length of the shortest word(s).
 * <p>
 * String will never be empty and you do not need to account for different data types.
 */
public class ShortestWord {
    public static void main(String[] args) {

        String s = "bitcoin take over the world2233333 maybe who knows perhaps";
        System.out.println(study(s));

    }

    public static int findShort(String s) {

        int result = Arrays.stream(s.split(" "))
                .map(i -> i.replaceAll("(?i)[^a-z]", ""))
                .min(Comparator.comparingInt(i -> i.length()))
                .get().length();

        return result;
    }

    public static int study(String s) {
//
//        return Stream.of(s.split(" "))
//                .mapToInt(String::length)
//                .min()
//                .getAsInt();

        return Arrays.stream(s.split(" ")).mapToInt(String::length).min().getAsInt();
    }
}
