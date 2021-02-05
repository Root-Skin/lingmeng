package codeWar01.codeWar0125;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author skin
 * @createTime 2021年01月25日
 * @Description Well met with Fibonacci bigger brother, AKA Tribonacci.
 * <p>
 * As the name may already reveal, it works basically like a Fibonacci,
 * but summing the last 3 (instead of 2) numbers of the sequence to generate the next.
 * And, worse part of it, regrettably I won't get to hear non-native Italian speakers trying to pronounce it :(
 * <p>
 * So, if we are to start our Tribonacci sequence with [1, 1, 1] as a starting input (AKA signature),
 * we have this sequence:
 */
public class TribonacciSequence {
    public static void main(String[] args) {
        double[] s = new double[]{1, 1, 1};
//        System.out.println(Arrays.toString(s));
        int n = 10;
        System.out.println(Arrays.toString(tribonacci(s, n)));
    }

    public static double[] tribonacci(double[] s, int n) {
        // hackonacci me
        List<Double> list = new ArrayList(Arrays.asList(s));
        if (n <= 3) {
            double[] result = Arrays.copyOfRange(s, 0, n);
            return result;
        } else {
            double[] doubles = Arrays.copyOfRange(s, 0, 3);
            double[] result = new double[n];
            result[0] = doubles[0];
            result[1] = doubles[1];
            result[2] = doubles[2];
            for (int i = 3; i < n; i++) {
                result[i] = result[i - 1] + result[i - 2] + result[i - 3];
            }
            return result;
        }
    }
    public double[] study(double[] s, int n) {
//        double[] tritab=Arrays.copyOf(s, n);
//        for(int i=3;i<n;i++){
//            tritab[i]=tritab[i-1]+tritab[i-2]+tritab[i-3];
//        }
//        return tritab;

        double[] r = new double[n];
        for(int i = 0; i < n; i++){
            r[i] = (i<3)?s[i]:r[i-3]+r[i-2]+r[i-1];
        }
        return r;

    }
}
