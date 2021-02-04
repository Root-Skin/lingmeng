package codeWar01.codeWar0126;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年01月26日
 * @Description You are given an array (which will have a length of at least 3, but could be very large) containing integers.
 * The array is either entirely comprised of odd integers or entirely comprised of even integers except for a single integer N.
 * Write a method that takes the array as an argument and returns this "outlier" N.
 */
public class FindTheParityOutlier {
    public static void main(String[] args) {

        int[] integers = new int[]{Integer.MAX_VALUE, 0, 1};
        System.out.println(find(integers));
    }

    static int find(int[] integers) {
        List<Integer> result ;
        if(integers.length==1){
            return integers[0];
        }else{

            long even = Arrays.stream(integers).filter(i -> i % 2 == 0).count();
            long odd = Arrays.stream(integers).filter(i -> i % 2 != 0).count();
            if(even>odd){
                result = Arrays.stream(integers).filter(i -> i % 2 != 0).boxed().collect(Collectors.toList());
            }else{
                result = Arrays.stream(integers).filter(i -> i % 2 == 0).boxed().collect(Collectors.toList());
            }
        }
        return result.get(0);
    }

    public static int study(int[] integers) {
        // Since we are warned the array may be very large, we should avoid counting values any more than we need to.

        // We only need the first 3 integers to determine whether we are chasing odds or evens.
        // So, take the first 3 integers and compute the value of Math.abs(i) % 2 on each of them.
        // It will be 0 for even numbers and 1 for odd numbers.
        // Now, add them. If sum is 0 or 1, then we are chasing odds. If sum is 2 or 3, then we are chasing evens.
//        int sum = Arrays.stream(integers).limit(3).map(i -> Math.abs(i) % 2).sum();
//        int mod = (sum == 0 || sum == 1) ? 1 : 0;
//
//        return Arrays.stream(integers).parallel() // call parallel to get as much bang for the buck on a "large" array
//                .filter(n -> Math.abs(n) % 2 == mod).findFirst().getAsInt();

        int[] odd = Arrays.stream(integers).filter(n -> n % 2 != 0).toArray();
        int[] even = Arrays.stream(integers).filter(n -> n % 2 == 0).toArray();

        return odd.length == 1 ? odd[0] : even[0];
    }
}
