package codeWar02.codeWar0218;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skin
 * @createTime 2021年02月18日
 * @Description
 */
public class Snail {
    public static void main(String[] args) {
        int[][] array
                = {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        System.out.println(array);
    }
    public static int[] snail(int[][] array) {
        // enjoy
        List<Integer> result   = new ArrayList<>();
        for(int i=0;i<array[0].length;i++){
            result.add(array[0][i]);
        }
        for(int j=1;j<array[0].length-1;j++){
            result.add(array[j][array[0].length]);
        }
        for(int i=0;i<array[0].length;i++){
            result.add(array[0][i]);
        }

    }
}
