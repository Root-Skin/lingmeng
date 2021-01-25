package codeWar.codeWar0125;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年01月25日
 * @Description You live in the city of Cartesia where all roads are laid out in a perfect grid.
 * You arrived ten minutes too early to an appointment, so you decided to take the opportunity to go for a short walk.
 * The city provides its citizens with a Walk Generating App on their phones --
 * everytime you press the button it sends you an array of one-letter strings representing directions to walk (eg. ['n', 's', 'w', 'e']).
 * You always walk only a single block for each letter (direction) and you know it takes you one minute to traverse one city block, so create a function that will return true if the walk the app gives you will take you exactly ten minutes (you don't want to be early or late!) and will, of course, return you to your starting point. Return false otherwise.
 * <p>
 * Note: you will always receive a va
 */
public class TakeaTenMinuteWalk {
    public static void main(String[] args) {
        char[] walk = new char[]{'w','e','w','e','w','e','w','e','w','e','w','e'};
        System.out.println(isValid(walk));
    }

    public static boolean isValid(char[] walk) {
        // Insert brilliant code here

        ArrayList<String> mylist = new ArrayList<String>();

        String s = new String (walk);
        if(s.length()!=10){
            return false;
        }
        long count;
        Map<String, Long> collect = Arrays.stream(s.split(""))
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));

        if (collect.get("n") !=null && collect.get("s") !=null && collect.get("n") > collect.get("s")) {
            count = collect.get("n") - collect.get("s");
            for (int i = 0; i < count; i++) {
                mylist.add("n");
            }
        } else if (collect.get("n") !=null && collect.get("s") !=null && collect.get("n") < collect.get("s")) {
            count = collect.get("s") - collect.get("n");
            for (int i = 0; i < count; i++) {
                mylist.add("s");
            }
        }else if(collect.get("n") !=null && collect.get("s") == null){
            return false;
        } else if(collect.get("s") !=null && collect.get("n") == null){
            return false;
        }
        if (collect.get("e") !=null && collect.get("w") !=null && collect.get("e") > collect.get("w")) {
            count = collect.get("e") - collect.get("w");
            for (int i = 0; i < count; i++) {
                mylist.add("e");
            }
        } else if (collect.get("e") !=null && collect.get("w") !=null && collect.get("e") < collect.get("w")) {
            count = collect.get("w") - collect.get("e");
            for (int i = 0; i < count; i++) {
                mylist.add("w");
            }
        }else if(collect.get("e") !=null && collect.get("w") == null){
            return false;
        } else if(collect.get("w") !=null && collect.get("e") == null){
            return false;
        }

        if (mylist.size() > 0) {
            return false;
        } else {
            return true;
        }
    }
    public static boolean study(char[] walk) {
//        if (walk.length != 10) return false;
//
//        int x = 0, y = 0;
//        for (char c: walk) {
//            switch (c) {
//                case 'n': y++; break;
//                case 's': y--; break;
//                case 'w': x++; break;
//                case 'e': x--; break;
//            }
//        }
//        return x == 0 && y == 0;

//        if (walk.length != 10) {
//            return false;
//        }
//        int x = 0, y = 0;
//        for (int i = 0; i < 10; i++) {
//            switch (walk[i]) {
//                case 'n':
//                    y++;
//                    break;
//                case 'e':
//                    x++;
//                    break;
//                case 's':
//                    y--;
//                    break;
//                case 'w':
//                    x--;
//                    break;
//            }
//        }
//        return x == 0 && y == 0;


        String s = new String(walk);
        return s.chars().filter(p->p=='n').count()==s.chars().filter(p->p=='s').count()&&
                s.chars().filter(p->p=='e').count()==s.chars().filter(p->p=='w').count()&&s.chars().count()==10;
    }
}
