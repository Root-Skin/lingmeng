package codeWar02.codeWar0218;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author skin
 * @createTime 2021年02月18日
 * @Description
 */
public class Snail {
    static boolean flag = false;

    public static void main(String[] args) {
        int[][] array
                = {
//                {1, 2, 3, 1},
//                {4, 5, 6, 4},
//                {7, 8, 9, 7},
//                {7, 8, 9, 7}

                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
//                {1}

//                {1,2},
//                {3,4}

//
//                {49, 313, 423, 546, 277, 694, 999, 92, 391},
//                {325, 428, 912, 225, 662, 920, 493, 891, 668},
//                {133, 652, 727, 701, 860, 46, 688, 128, 483},
//                {950, 526, 205, 28, 564, 480, 982, 761, 641},
//                {626, 122, 504, 667, 266, 378, 162, 632, 903},
//                {148, 940, 108, 819, 648, 643, 156, 432, 975},
//                {279, 145, 854, 809, 133, 820, 695, 532, 696},
//                {814, 686, 997, 843, 61, 931, 914, 297, 52},
//                {367, 481, 657, 634, 221, 164, 907, 473, 938}

        };
        System.out.println(Arrays.toString(snail(array)));
    }

    public static int[] snail(int[][] array) {
        // enjoy
        if (array.length == 1) {
            return flatten(array);
        }
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < array[0].length; i++) {
            result.add(array[0][i]);
        }
        for (int j = 1; j < array[0].length - 1; j++) {
            result.add(array[j][array[0].length - 1]);
        }
        for (int i = array[0].length - 1; i >= 0; i--) {
            result.add(array[array[0].length - 1][i]);
        }

        if (array.length > 3) {
            int[][] newArray = new int[array[0].length - 2][array[0].length - 2];
            for (int i = array[0].length - 2; i > 0; i--) {
                result.add(array[i][0]);
                for (int j = 0; j < array[0].length - 2; j++) {
                    newArray[i - 1][j] = array[i][j + 1];
                }
            }
            int[] temp = result.stream().mapToInt(Integer::valueOf).toArray();
            flag = true;
            return getMergeArray(temp, snail(newArray));
        } else {
            for (int i = array[0].length - 2; i > 0; i--) {
                result.add(array[i][0]);
                if (array.length == 3) {
                    result.add(array[array[0].length - 2][1]);
                }
            }
            int[] temp = result.stream().mapToInt(Integer::valueOf).toArray();

            return temp;
        }
    }

    private static int[] getMergeArray(int[] al, int[] bl) {
        int[] a = al;
        int[] b = bl;
        int[] c = new int[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static int[] flatten(int[][] data) {
        List<Integer> toReturn = new ArrayList<Integer>();
        for (int[] sublist : Arrays.asList(data)) {
            for (int elem : sublist) {
                toReturn.add(elem);
            }
        }
        return toReturn.stream().mapToInt(Integer::valueOf).toArray();
    }


    public static int[] study(int[][] array) {
        if (array[0].length == 0) return new int[0];
        int n = array.length;
        int[] answer = new int[n * n];
        int index = 0;
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - i; j++) answer[index++] = array[i][j];
            for (int j = i + 1; j < n - i; j++) answer[index++] = array[j][n - i - 1];
            for (int j = i + 1; j < n - i; j++) answer[index++] = array[n - i - 1][n - j - 1];
            for (int j = i + 1; j < n - i - 1; j++) answer[index++] = array[n - j - 1][i];
        }
        if (n % 2 != 0) answer[index++] = array[n / 2][n / 2];
        return answer;
    }
}
