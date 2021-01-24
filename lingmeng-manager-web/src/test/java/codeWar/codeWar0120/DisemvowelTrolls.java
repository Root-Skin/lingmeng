package codeWar.codeWar0120;

/**
 * @author skin
 * @createTime 2021年01月20日
 * @Description
 */
public class DisemvowelTrolls {

    public static void main(String[] args) {
        String str = "This website is for losers LOL!";
        System.out.println(disemvowel(str));

    }

    public static String disemvowel(String str) {
        // Code away...
        return str.replaceAll("(?i)[aeiou]", "");
    }
}
