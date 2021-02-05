package codeWar01.codeWar0121.key6;

import java.util.Arrays;

/**
 * @author skin
 * @createTime 2021年01月21日
 * @Description Write a function, persistence, that takes in a positive parameter num and returns its multiplicative persistence,
 * <p>
 * which is the number of times you must multiply the digits in num until you reach a single digit.
 */
public class PersistentBugger {
    public static void main(String[] args) {

        long n = 999;
//        System.out.println(persistence(n));
        System.out.println(study(n));
    }

    public static int persistence(long n) {
        // your code
        int result = 0;
        char[] chars = String.valueOf(n).toCharArray();
        int i = 0;
        int count = 0;
        iner:
        while (i < chars.length - 1) {
            int beginValue = Integer.parseInt(String.valueOf(chars[i]));
            int endValue = Integer.parseInt(String.valueOf(chars[i + 1]));
            if (i == 0) {
                result = result + beginValue * endValue;
            } else {
                result = result * endValue;
            }
            if (String.valueOf(result).length() == 1 && i == chars.length - 2) {
                count++;
                return count;
            }
            if (i == chars.length - 2) {
                chars = String.valueOf(result).toCharArray();
                result = 0;
                i = 0;
                count++;
                continue iner;
            }
            i++;
        }
        return count;
    }
    public static int study(long n) {
//        long m = 1, r = n;
//        if (r / 10 == 0)
//            return 0;
//        for (r = n; r != 0; r /= 10)
//            m *= r % 10;
//        return study(m) + 1;

        if (n < 10) return 0;

        final long newN = Arrays.stream(String.valueOf(n).split(""))
                .mapToLong(Long::valueOf)
                .reduce(1, (acc, next) -> acc * next);

        return persistence(newN) + 1;
    }
}
