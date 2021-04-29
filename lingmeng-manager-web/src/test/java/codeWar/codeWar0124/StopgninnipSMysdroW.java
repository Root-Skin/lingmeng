package codeWar01.codeWar0124;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年01月24日
 * @Description
 */
public class StopgninnipSMysdroW {
    public static void main(String[] args) {

        String sentence = "Hey wollef sroirraw sroirraw";
        System.out.println(spinWords(sentence));
    }

    public static String spinWords(String sentence) {
        //TODO: Code stuff here
        StringBuilder result = new StringBuilder();
        String[] s = sentence.split(" ");
        for (String i : s) {
            if (i.length() >= 5) {
                String j = String.valueOf(new StringBuffer(i).reverse());

                if (sentence.indexOf(i) == sentence.length() - i.length()) {
                    result.append(j);
                } else {
                    result.append(j).append(" ");
                }

            } else {
                if (sentence.indexOf(i) == sentence.length() - i.length()) {
                    result.append(i);
                } else {
                    result.append(i).append(" ");
                }
            }
            if (sentence.length() == i.length()) {
                sentence = sentence;
            } else {
                sentence = sentence.substring(i.length() + 1, sentence.length());
            }
        }
        return result.toString();
    }


    public String study(String sentence) {
//        String[] words = sentence.split(" ");
//        for (int i = 0; i < words.length; i++) {
//            if (words[i].length() >= 5) {
//                words[i] = new StringBuilder(words[i]).reverse().toString();
//            }
//        }
//        return String.join(" ", words);

        return Arrays.stream(sentence.split(" "))
                .map(i -> i.length() > 4 ? new StringBuilder(i).reverse().toString() : i)
                .collect(Collectors.joining(" "));
    }
}
