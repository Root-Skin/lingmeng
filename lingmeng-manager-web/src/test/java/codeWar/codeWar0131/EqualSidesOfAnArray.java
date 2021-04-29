package codeWar01.codeWar0131;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author skin
 * @createTime 2021年01月31日
 * @Description
 */
public class EqualSidesOfAnArray {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4};
        System.out.println(findEvenIndex(arr));
    }

    public static int findEvenIndex(int[] arr) {
        // your code
        List<Integer> collect = Arrays.stream(arr).boxed().collect(Collectors.toList());

        int right = 0;
        int left = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                List<Integer> integers = collect.subList(1, arr.length);
                Integer sumResult = integers.stream().reduce(0, (acc, n) -> acc + n);
                left = 0;
                right = sumResult;
            } else {
                List<Integer> integers = collect.subList(i + 1, arr.length);
                List<Integer> integers2 = collect.subList(0, i);
                left = integers2.stream().reduce(0, (acc, n) -> acc + n);
                right = integers.stream().reduce(0, (acc, n) -> acc + n);
            }
            if (left == right) {
                return i;
            }
        }
        return -1;
    }

    public static int study(int[] arr) {
        return IntStream.range(0, arr.length)
                .filter(n -> IntStream.of(arr).limit(n).sum() == IntStream.of(arr).skip(n + 1).sum())
                .findFirst().orElse(-1);
    }
}
