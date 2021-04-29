package codeWar01.codeWar0120;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author skin
 * @createTime 2021年01月20日
 * @Description
 */
public class DescendingOrder {
    public static void main(String[] args) {

        System.out.println(sortDesc(42145));

        System.out.println(study(42145));

    }

    public static int sortDesc(final int num) {
        //Your code
        int temp;
        int count=0;
        String s = String.valueOf(num);
        for(char chars: s.toCharArray()){
            s = s.replace(String.valueOf(chars), chars + ",");
        }
        s = s.replace(",," , ",");

        String[] split = s.split(",");
        List<String> strings = Arrays.asList(split);
        List<Integer> numberList= new ArrayList<>();
        for (String string : strings) {
            numberList.add(Integer.parseInt(string));
        }
        for(int i=0;i<numberList.size();i++){
            for(int j=0;j<numberList.size()-i-1;j++){
                if(numberList.get(j)<numberList.get(j+1)){
                    temp =numberList.get(j);
                    numberList.set(j,numberList.get(j+1));
                    numberList.set(j+1,temp);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberList.size(); i++) {
            sb.append(numberList.get(i));
        }
        return Integer.parseInt(sb.toString());
    }

    public static int study(final int num) {
        //Code goes here!
//        return Integer.parseInt(String.valueOf(num)
//                .chars()
//                .mapToObj(i -> String.valueOf(Character.getNumericValue(i)))
//                .sorted(Comparator.reverseOrder())
//                .collect(Collectors.joining()));



//        String[] numbers = String.valueOf(num).split("");
//        List<String> collect = Arrays.stream(numbers).mapToInt(Integer::valueOf).boxed().map(String::valueOf).collect(Collectors.toList());
//        Collections.reverse(collect);
//        return Integer.valueOf(collect.stream().collect(Collectors.joining()));


        String[] array = String.valueOf(num).split("");
        Arrays.sort(array, Collections.reverseOrder());
        return Integer.valueOf(String.join("", array));
    }
}
