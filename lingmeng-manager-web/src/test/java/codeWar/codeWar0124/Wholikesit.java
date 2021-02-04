package codeWar01.codeWar0124;

import java.util.Arrays;
import java.util.List;

/**
 * @author skin
 * @createTime 2021年01月24日
 * @Description
 */
public class Wholikesit {

    public static void main(String[] args) {

        System.out.println(whoLikesIt("Alex", "Jacob", "Mark", "Max","Bob"));
    }

    public static String whoLikesIt(String... names) {
        //Do your magic here
        StringBuilder result  = new StringBuilder();
        List<String> nameList = Arrays.asList(names);

        String t1 ;
        String t2 ;
        String t3 ;
       switch (nameList.size()){
           case 0:
               result.append("no one ");
               result.append("likes this");
               break;
           case 1:
                t1 = nameList.get(0);
               result.append(t1+" ");
               result.append("likes this");
               break;
           case 2:
                t1 = nameList.get(0);
                t2 = nameList.get(1);
               result.append(t1+" "+ "and"+" ");
               result.append(t2+" ");
               result.append("like this");
               break;
           case 3:
                t1 = nameList.get(0);
                t2 = nameList.get(1);
                t3 = nameList.get(2);
               result.append(t1+","+" ");
               result.append(t2+" "+ "and"+" ");
               result.append(t3+" ");
               result.append("like this");
               break;
           default :
               t1 = nameList.get(0);
               t2 = nameList.get(1);
               int othersize = nameList.size()-2;
               result.append(t1+","+" ");
               result.append(t2+" "+ "and"+" ");
               result.append(othersize+" ");
               result.append("others"+" ");
               result.append("like this");
               break;
       }

        return result.toString();
    }
    public static String study(String... names) {
        switch (names.length) {
            case 0: return "no one likes this";
            case 1: return String.format("%s likes this", names[0]);
            case 2: return String.format("%s and %s like this", names[0], names[1]);
            case 3: return String.format("%s, %s and %s like this", names[0], names[1], names[2]);
            default: return String.format("%s, %s and %d others like this", names[0], names[1], names.length - 2);
        }
    }
}
