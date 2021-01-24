package codeWar.codeWar0120;


import java.util.ArrayList;
import java.util.List;

/**
 * @author skin
 * @createTime 2021年01月20日
 * @Description
 * Return the number (count) of vowels in the given string.
 *
 * We will consider a, e, i, o, u as vowels for this Kata (but not y).
 *
 * The input string will only consist of lower case letters and/or spaces.
 */

public class VowelCount {
    public static void main(String[] args) {
        String str = "abracadabra";
        System.out.println(getCount(str));
    }
    public static int getCount(String str) {
        int vowelsCount = 0;
        // your code here
        String data = str.trim();
        List list = new ArrayList<String>(){{
            add("a"); add("e"); add("i"); add("o"); add("u");
        }};
//        String[] list = new String[]{"a","e","i","o","e"};
//        Arrays.asList(arr)
        for (char aChar : str.toCharArray()) {
            if(list.contains(Character.toString(aChar))){
                vowelsCount++;
            }
        }
        return vowelsCount;
    }

    public static int study(String str) {
        // your code here
        return str.replaceAll("(?i)[^aeiou]", "").length();

    }
}
