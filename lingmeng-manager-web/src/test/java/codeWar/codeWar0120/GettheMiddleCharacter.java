package codeWar01.codeWar0120;

/**
 * @author skin
 * @createTime 2021年01月20日
 * @Description
 * You are going to be given a word.
 * Your job is to return the middle character of the word.
 * If the word's length is odd, return the middle character.
 * If the word's length is even, return the middle 2 characters.
 *
 * #Input
 *
 * A word (string) of length 0 < str < 1000 (In javascript you may get slightly more than 1000 in some test cases due to an error in the test cases). You do not need to test for this. This is only here to tell you that you do not need to worry about your solution timing out.
 *
 * #Output
 *
 * The middle character(s) of the word represented as a string.
 */

public class GettheMiddleCharacter {
    public static void main(String[] args) {

        String word = "midle";

        System.out.println( getMiddle(word));
    }

    public static String getMiddle(String word) {
        //Code goes here!
        String result ;
        if(word.length()>0 && word.length()%2==0){
            result = word.substring(word.length() / 2 - 1, word.length() / 2 + 1);
        }else{
            result = word.substring(word.length()/2,word.length()/2+1);
        }

        return result;
    }

    public static String study(String word) {
        //Code goes here!
        int length = word.length();
        return (length % 2 != 0) ?  String.valueOf(word.charAt(length / 2)) : word.substring(length / 2 - 1, length / 2 + 1);
    }
}
