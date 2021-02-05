package codeWar01.codeWar0126;

/**
 * @author skin
 * @createTime 2021年01月26日
 * @Description Write a function that takes an integer as input,
 * and returns the number of bits that are equal to one in the binary representation of that number.
 * You can guarantee that input is non-negative.
 * <p>
 * Example: The binary representation of 1234 is 10011010010, so the function should return 5 in this case
 */
public class BitCounting {
    public static void main(String[] args) {


        System.out.println(countBits(1234));

    }

    public static int countBits(int n) {
        // Show me the code!
        StringBuilder sb = new StringBuilder();
        String count = count(n, sb);
        int result = (int) count.chars().filter(i -> i == '1').count();
        return result;

    }

    private static String count(int n, StringBuilder sb) {
        if (n / 2 != 0) {
            sb.append(n % 2);
            return count(n / 2, sb);
        } else {
            sb.append(n % 2);
        }
        return sb.toString();
    }

    private static int study(int n) {
//        return Integer.bitCount(n);

        return (int) Integer.toBinaryString(n).chars()
                .filter(c -> c == '1')
                .count();
    }
}
