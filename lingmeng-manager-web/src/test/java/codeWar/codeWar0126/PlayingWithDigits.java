package codeWar01.codeWar0126;

/**
 * @author skin
 * @createTime 2021年01月26日
 * @Description
 * Some numbers have funny properties. For example:
 *
 * 89 --> 8¹ + 9² = 89 * 1
 *
 * 695 --> 6² + 9³ + 5⁴= 1390 = 695 * 2
 *
 * 46288 --> 4³ + 6⁴+ 2⁵ + 8⁶ + 8⁷ = 2360688 = 46288 * 51
 *
 * Given a positive integer n written as abcd... (a, b, c, d... being digits) and a positive integer p
 *
 * we want to find a positive integer k, if it exists, such as the sum of the digits of n taken to the successive powers of p is equal to k * n.
 * In other words:
 *
 * Is there an integer k such as : (a ^ p + b ^ (p+1) + c ^(p+2) + d ^ (p+3) + ...) = n * k
 *
 * If it is the case we will return k, if not return -1.
 *
 * Note: n and p will always be given as strictly positive integers.
 */
public class PlayingWithDigits {
    public static void main(String[] args) {

        int n=46288,p=3;
        System.out.println(digPow(n,p));
        System.out.println(study(n,p));
    }
    public static long digPow(int n, int p) {
        // your code
        double result = 0;
        String[] split = String.valueOf(n).split("");
        for(int i=0;i<split.length;i++){
            result += Math.pow(Double.parseDouble(split[i]), p);
            p++;
        }
        if(result%n==0){
            return (long) (result/n);
        }
        return -1;
    }

    public static long study(int n, int p) {
        String intString = String.valueOf(n);
        long sum = 0;
        for (int i = 0; i < intString.length(); ++i, ++p)
            sum += Math.pow(Character.getNumericValue(intString.charAt(i)), p);
        return (sum % n == 0) ? sum / n : -1;
    }

}
