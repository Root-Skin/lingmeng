package codeWar02.codeWar0218;

import java.util.TreeSet;

/**
 * @author skin
 * @createTime 2021年02月18日
 * @Description
 */
public class RangeExtraction {


    public static void main(String[] args) {
        int[] arr = new int[]{4, 16, 17, 20, 22, 25, 28, 31};
        System.out.println(rangeExtraction(arr));
    }

    public static String rangeExtraction(int[] arr) {
        TreeSet<Integer> temp = new TreeSet<Integer>();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] + 1 == arr[i + 1] && i != arr.length - 2) {
                temp.add(arr[i]);
                temp.add(arr[i + 1]);
            } else if (arr[i] + 1 == arr[i + 1] && i == arr.length - 2) {
                temp.add(arr[i]);
                temp.add(arr[i + 1]);
                if (temp.size() >= 3) {
                    temp.first();
                    result.append(temp.first());
                    result.append("-");
                    result.append(temp.last());
                } else {
                    result.append(temp.first());
                    result.append(",");
                    result.append(temp.last());
                }

            } else if (arr[i] + 1 != arr[i + 1] && temp.isEmpty() && i != arr.length - 2) {
                result.append(arr[i]);
                result.append(",");
            } else if (arr[i] + 1 != arr[i + 1] && temp.isEmpty() && i == arr.length - 2) {
                result.append(arr[i]);
                result.append(",");
                result.append(arr[i + 1]);
            } else if (arr[i] + 1 != arr[i + 1] && !temp.isEmpty() && i != arr.length - 2) {
                if (temp.size() >= 3) {
                    temp.first();
                    result.append(temp.first());
                    result.append("-");
                    result.append(temp.last());
                    result.append(",");
                } else {
                    result.append(temp.first());
                    result.append(",");
                    result.append(temp.last());
                    result.append(",");
                }
                temp.clear();
            } else if (arr[i] + 1 != arr[i + 1] && !temp.isEmpty() && i == arr.length - 2) {
                if (temp.size() >= 3) {
                    temp.first();
                    result.append(temp.first());
                    result.append("-");
                    result.append(temp.last());
                    result.append(",");
                    result.append(arr[i + 1]);
                } else {
                    result.append(temp.first());
                    result.append(",");
                    result.append(temp.last());
                    result.append(",");
                    result.append(arr[i + 1]);
                }
                temp.clear();
            }
        }
        return result.toString();
    }

    static String study(final int[] array) {
//        final StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < array.length; i++) {
//            int rangeStart = array[i];
//            sb.append(rangeStart);
//            for (int j = i + 1; j <= array.length; j++) {
//                if (j == array.length || array[j] != rangeStart + (j - i)) {
//                    if (j - i >= 3) {
//                        sb.append('-').append(array[j - 1]);
//                        i = j - 1;
//                    }
//                    sb.append(',');
//                    break;
//                }
//            }
//        }
//        return sb.deleteCharAt(sb.length() - 1).toString();


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            int j = i;
            while (j < array.length - 1 && array[j] + 1 == array[j + 1]) j++;
            if (i + 1 < j) {
                i = j;
                sb.append("-");
                sb.append(array[i]);
            }
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();

    }


}
