package codeWar01.codeWar0124.key5;

import java.util.Arrays;

/**
 * @author skin
 * @createTime 2021年01月24日
 * @Description The maximum sum subarray problem consists in finding the maximum sum of a contiguous subsequence in an array or list of integers:
 * Easy case is when the list is made up of only positive numbers and the maximum sum is the sum of the whole array. If the list is made up of only negative numbers, return 0 instead.
 * <p>
 * Empty list is considered to have zero greatest sum. Note that the empty list or array is also a valid sublist/subarray.
 */
public class MaximumSubarraySum {

    public static void main(String[] args) {

        int[] arr = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(sequence(arr));
    }

    public static int sequence(int[] arr) {


        long count = Arrays.stream(arr).filter(i -> i > 0).count();
        if (count == 0) {
            return 0;
        }

        int maxResult = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int result = 0;
                for (int k = i; k <= j; k++) {
                    result += arr[k];
                }
                if (maxResult < result) {
                    maxResult = result;
                }
            }
        }
        return maxResult;
    }

    public static int study(int[] arr) {
        int max_ending_here = 0, max_so_far = 0;
        for (int v : arr) {
            max_ending_here = Math.max(0, max_ending_here + v);
            max_so_far = Math.max(max_so_far, max_ending_here);
        }
        return max_so_far;
    }
}
