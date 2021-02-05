package codeWar01.codeWar0120;

import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年01月20日
 * @Description
 * Welcome. In this kata, you are asked to square every digit of a number and concatenate them.
 *
 * For example, if we run 9119 through the function, 811181 will come out, because 92 is 81 and 12 is 1.
 *
 * Note: The function accepts an integer and returns an integer
 */
public class SquareEveryDigit {
    public static void main(String[] args) {
        System.out.println(squareDigits(221));
    }

    public static int squareDigits(int n) {
        // TODO Implement me
        String result="";
        String s = String.valueOf(n);
        char[] chars = s.toCharArray();
        for(char charNum:chars){
            int pow = (int)Math.pow(Integer.parseInt(String.valueOf(charNum)), 2);
            System.out.println(pow);
            result = result+pow;
        }

        return Integer.parseInt(result);
    }

    public static int study(int n) {
        // TODO Implement me
        String result = "";

        while (n != 0) {
            int digit = n % 10 ;
            result = digit*digit + result ;
            n /= 10 ;
        }

        return Integer.parseInt(result) ;
    }

    public static int study2(int n) {
        return Integer.parseInt(String.valueOf(n)
                .chars()
                .map(i -> Integer.parseInt(String.valueOf((char) i)))
                .map(i -> i * i)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("")));



    }

}
