package codeWar.codeWar0124;

import java.util.Arrays;
import java.util.List;

/**
 * @author skin
 * @createTime 2021年01月24日
 * @Description
 * The goal of this exercise is to convert a string to a new string where each character
 * in the new string is "(" if that character appears only once in the original string,
 * or ")" if that character appears more than once in the original string.
 * Ignore capitalization when determining if a character is a duplicate.
 */
public class DuplicateEncoder {
    public static void main(String[] args) {

        String word = "Prespecialized";
        System.out.println(encode(word));
    }

    static String encode(String word){



        List<String> strings = Arrays.asList(word.toLowerCase().split(""));
        List<String> temp = Arrays.asList(word.toLowerCase().split(""));
//        word.toLowerCase()
//        for(String s:strings){
//
//        }

//        return result.toString();
       String s = "wahaha";

       //黄涛woyaozhizhaochogntu开发1
        //xx开发的()woyaozhi
        // zhaochogntu
       //黄涛(unix)开发1
        // 开发2
        //冲突2

        //测试提交pr

        return "";
    }
}
