package codeWar.codeWar0125;

import java.util.stream.IntStream;

/**
 * @author skin
 * @createTime 2021年01月25日
 * @Description Write a function that accepts an array of 10 integers (between 0 and 9),
 * that returns a string of those numbers in the form of a phone number.
 * The returned format must be correct in order to complete this challenge.
 * Don't forget the space after the closing parentheses!
 */
public class CreatePhoneNumber {
    public static void main(String[] args) {

        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        System.out.println(createPhoneNumber(numbers));
    }

    public static String createPhoneNumber(int[] numbers) {
        // returns "(123) 456-7890"
        // Your code here!
        System.out.println(IntStream.of(numbers).boxed().toArray().toString());
        StringBuilder result = new StringBuilder();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < numbers.length; i++) {
            sb.append(numbers[i]);
        }
        String s = sb.toString();
        result.append("(");
        result.append(s, 0, 3);
        result.append(")");
        result.append(" ");
        result.append(s, 3, 6);
        result.append("-");
        result.append(s, 6, 10);
        return result.toString();
    }
    public static String study(int[] numbers) {
//        return String.format("(%d%d%d) %d%d%d-%d%d%d%d",numbers[0],numbers[1],numbers[2],numbers[3],numbers[4],numbers[5],numbers[6],numbers[7],numbers[8],numbers[9]);
        return String.format("(%d%d%d) %d%d%d-%d%d%d%d", IntStream.of(numbers).boxed().toArray());
    }
}
