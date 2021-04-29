package codeWar01.codeWar0121;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年01月21日
 * @Description Jaden Smith, the son of Will Smith, is the star of films such as The Karate Kid (2010) and After Earth (2013).
 * Jaden is also known for some of his philosophy that he delivers via Twitter.
 * When writing on Twitter, he is known for almost always capitalizing every word.
 * For simplicity, you'll have to capitalize each word, check out how contractions are expected to be in the example below.
 * <p>
 * Your task is to convert strings to how they would be written by Jaden Smith.
 * The strings are actual quotes from Jaden Smith, but they are not capitalized in the same way he originally typed them.
 */
public class JadenCasingStrings {
    public static void main(String[] args) {

        String phrase = "";
        System.out.println(toJadenCase(phrase));
    }

    public static String toJadenCase(String phrase) {
        // TODO put your code below this comment
        if (phrase != null && phrase != "") {
            return Arrays.stream(phrase.split(" "))
                    .map(i -> i.replaceAll(i.substring(0, 1), i.substring(0, 1).toUpperCase())).collect(Collectors.joining(" "));
        }
        return null;
    }

    public static String study(String phrase) {
        if (phrase == null || phrase.equals("")) return null;

        char[] array = phrase.toCharArray();

        for (int x = 0; x < array.length; x++) {
            if (x == 0 || array[x - 1] == ' ') {
                array[x] = Character.toUpperCase(array[x]);
            }
        }

        return new String(array);
    }
}

