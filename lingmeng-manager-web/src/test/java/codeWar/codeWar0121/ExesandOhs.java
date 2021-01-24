package codeWar.codeWar0121;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年01月21日
 * @Description
 * Check to see if a string has the same amount of 'x's and 'o's.
 * The method must return a boolean and be case insensitive.
 * The string can contain any char.
 */
public class ExesandOhs {

    public static void main(String[] args) {
        String str = "xxxXooOosso";
        System.out.println(getXO(str));
    }

    public static boolean getXO (String str) {
//        str.toLowerCase().chars().collect(Collectors.groupingBy(String::toString,Collectors.counting()));

        Long x = Arrays.stream(str.toLowerCase().split("")).collect(Collectors.groupingBy(String::toString, Collectors.counting())).get("x");
        Long o = Arrays.stream(str.toLowerCase().split("")).collect(Collectors.groupingBy(String::toString, Collectors.counting())).get("o");
        // Good Luck!!
        return  x==o;
    }

    public static boolean study (String str) {
        str = str.toLowerCase();
        return str.replace("o","").length() == str.replace("x","").length();
    }
}
