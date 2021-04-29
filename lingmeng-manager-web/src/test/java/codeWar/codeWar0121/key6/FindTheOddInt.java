package codeWar01.codeWar0121.key6;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**


/**
 * @author skin
 * @date 2021/1/21 15:26
 * @description:
 * Given an array of integers, find the one that appears an odd number of times.
 *
 * There will always be only one integer that appears an odd number of times.
 *
 */
public class FindTheOddInt {
    public static void main(String[] args) {
        int[] a = new int[]{20,1,-1,2,-2,3,3,5,5,1,2,4,20,4,-1,-2,5};

//        System.out.println(findIt(a));
        System.out.println(study(a));
    }

    public static int findIt(int[] a) {

        List<Integer> list1 = Arrays.stream(a).boxed().collect(Collectors.toList());
        return list1.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()))
                .entrySet()
                .stream().filter(i -> i.getValue() % 2 == 1).map(Map.Entry::getKey).findFirst().get();
    }

    public static int study(int[] a) {
        /**
         * I think this is what it works:
         *
         * A^A = 0
         *
         * A^0 = A
         *
         * A^B^C^A^B = A^A^B^B^C = 0^0^C = C
         *
         * This solution is amazing,i would never think like that.
         */
//        int xor = 0;
//        for (int i = 0; i < a.length; i++) {
//            xor ^= a[i];
//            System.out.println(xor);
//        }
//        return xor;

        return Arrays.stream(a).reduce(0, (x, y) -> x ^ y);

    }


}

