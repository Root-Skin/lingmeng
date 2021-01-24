package codeWar.codeWar0124;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skin
 * @createTime 2021年01月24日
 * @Description
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
 *
 * Finish the solution so that it returns the sum of all the multiples of 3 or 5 below the number passed in.
 *
 * Note: If the number is a multiple of both 3 and 5, only count it once. Also, if a number is negative, return 0(for languages that do have them)
 *
 * Courtesy of projecteuler.net
 */
public class Multiplesof3or5 {
    public static void main(String[] args) {

        int number = 10;
        System.out.println(solution(number));


    }

    public static int solution(int number) {
        //TODO: Code stuff here

        List<Integer> result = new ArrayList();
        if (number == 0) {
            return 0;
        } else {
            for (int i = 1; i < number; i++) {
                if (i % 3 == 0 && i % 5 == 0) {
                    result.add(i);
                    continue;
                } else if (i % 3 == 0) {
                    result.add(i);
                } else if (i % 5 == 0) {
                    result.add(i);
                }
            }
        }
        Integer sumResult  = result.stream().reduce(0, (acc, n) -> acc + n);
        return sumResult;
    }


    public static int study(int number) {
        //TODO: Code stuff here
//        return IntStream.range(0, number)
//                .filter(n -> (n % 3 == 0) || (n % 5 == 0))
//                .sum();

        int sum=0;

        for (int i=0; i < number; i++){
            if (i%3==0 || i%5==0){sum+=i;}
        }
        return sum;
    }
}
