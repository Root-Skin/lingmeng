package codeWar01.codeWar0120;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年01月20日
 * @Description
 * In this little assignment you are given a string of space separated numbers,
 * and have to return the highest and lowest number.
 *
 * All numbers are valid Int32, no need to validate them.
 * There will always be at least one number in the input string.
 * Output string must be two numbers separated by a single space, and highest number is first.
 */
public class HighestandLowest {
    public static void main(String[] args) {

        String s = "8 3 -5 42 -1 0 0 -9 4 7 4 -4";

        System.out.println(highAndLow(s));
    }

    public static String highAndLow(String numbers) {

        List<String> stringList = Arrays.asList(numbers.split(" "));
        List<Integer> numberList = stringList.stream().map(Integer::parseInt).collect(Collectors.toList());

        // Code here or
        int temp;
        for(int i=0;i<numberList.size();i++){
            for(int j=0;j<numberList.size()-i-1;j++){
                if(numberList.get(j)>numberList.get(j+1)){
                    temp =numberList.get(j);
                    numberList.set(j,numberList.get(j+1));
                    numberList.set(j+1,temp);
                }
            }
        }
        System.out.println(numberList);
        int min = numberList.get(0);
        int max = numberList.get(numberList.size()-1);
        StringBuilder sb =new StringBuilder();
        sb.append(max);
        sb.append(" ");
        sb.append(min);

        return sb.toString();
    }

    public static String study(String numbers) {

//        int min = Arrays.stream(numbers.split(" "))
//                .mapToInt(i -> Integer.parseInt(i))
//                .min()
//                .getAsInt();
//
//        int max = Arrays.stream(numbers.split(" "))
//                .mapToInt(i -> Integer.parseInt(i))
//                .max()
//                .getAsInt();
//
//        return String.format("%d %d", max, min);


//        int[] ints = Stream.of(numbers.split("\\s")).mapToInt(Integer::parseInt).sorted().toArray();
//
//        return ints[ints.length -1] + " " + ints[0];


//        IntSummaryStatistics summary = Arrays
//                .stream(numbers.split(" "))
//                .collect(Collectors.summarizingInt(n -> Integer.parseInt(n)));
//        return String.format("%d %d", summary.getMax(), summary.getMin());


        IntSummaryStatistics stats = Arrays.stream(numbers.split("\\s")).
                mapToInt(Integer::parseInt).summaryStatistics();

        return String.format("%d %d", stats.getMax(), stats.getMin());


    }
}
