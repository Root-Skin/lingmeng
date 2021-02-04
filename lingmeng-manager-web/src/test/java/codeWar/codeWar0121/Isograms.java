package codeWar01.codeWar0121;

import java.util.stream.Stream;

/**
 * @author skin
 * @createTime 2021年01月21日
 * @Description
 * An isogram is a word that has no repeating letters, consecutive or non-consecutive.
 * Implement a function that determines whether a string that contains only letters is an isogram.
 * Assume the empty string is an isogram. Ignore letter case.
 */
public class Isograms {

    public static void main(String[] args) {

        String str = "moOse";
        System.out.println(isIsogram(str));
    }

    public static boolean  isIsogram(String str) {
        String[] split = str.toLowerCase().split("");
        return  Stream.of(split).distinct().count()==split.length? true:false;
    }

    public static boolean  study(String str) {
        return str.length() == str.toLowerCase().chars().distinct().count();
    }
}
