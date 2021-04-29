package codeWar02.codeWar0201;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skin
 * @createTime 2021年02月01日
 * @Description The new "Avengers" movie has just been released! There are a lot of people at the cinema box office standing in a huge line. Each of them has a single 100, 50 or 25 dollar bill. An "Avengers" ticket costs 25 dollars.
 * <p>
 * Vasya is currently working as a clerk. He wants to sell a ticket to every single person in this line.
 * <p>
 * Can Vasya sell a ticket to every person and give change if he initially has no money and sells the tickets strictly in the order people queue?
 * <p>
 * Return YES, if Vasya can sell a ticket to every person and give change with the bills he has at hand at that moment. Otherwise return NO.
 */
public class VasyaClerk {
    public static void main(String[] args) {
        int[] peopleInLine = new int[]{25, 25, 50, 100, 50, 25};
        System.out.println(Tickets(peopleInLine));
    }

    public static String Tickets(int[] peopleInLine) {
        //Your code is here...
        Map temp25 = new HashMap<>();
        Map temp50 = new HashMap<>();
        temp50.put("50", 0);
        if (peopleInLine[0] != 25) {
            return "NO";
        } else {
            temp25.put("25", 1);
        }
        for (int i = 1; i < peopleInLine.length; i++) {
            if (peopleInLine[i] == 25) {
                if (i == peopleInLine.length - 1) {
                    return "YES";
                } else {
                    int o = (int) temp25.get("25") + 1;
                    temp25.put("25", o);
                }
                continue;
            } else if (peopleInLine[i] == 50) {
                if ((int) temp25.get("25") >= 1 && i == peopleInLine.length - 1) {
                    return "YES";
                } else if ((int) temp25.get("25") >= 1 && i != peopleInLine.length - 1) {

                    int o = (int) temp50.get("50") + 1;
                    temp50.put("50", o);
                    int j = (int) temp25.get("25") - 1;
                    temp25.put("25", j);

                    continue;
                } else if ((int) temp25.get("25") < 1) {  //这里没有考虑到
                    return "NO";
                }
            } else if (peopleInLine[i] == 100) {
                if ((int) temp50.get("50") >= 1 && (int) temp25.get("25") >= 1 && i == peopleInLine.length - 1) {
                    return "YES";
                } else if ( (int) temp25.get("25") >=3 && i == peopleInLine.length - 1) {
                    //75是由3张25组成
                    return "YES";
                }
                else if ((int) temp50.get("50") >= 1 && (int) temp25.get("25") >= 1 && i != peopleInLine.length - 1) {
                    int o = (int) temp50.get("50") - 1;
                    temp50.put("50", o);
                    int j = (int) temp25.get("25") - 1;
                    temp25.put("25", j);
                    continue;
                } else if ((int) temp25.get("25") >= 3 && i != peopleInLine.length - 1) {
                    //剩下75可以由3张25组成
                    int j = (int) temp25.get("25") - 3;
                    temp25.put("25", j);
                    continue;
                } else if (((int) temp50.get("50") < 1 && (int) temp25.get("25") < 1) || (int) temp25.get("25") < 3) {
                    //这里没有考虑到
                    //2.剩下75也可以由3张25组成
                    return "NO";
                }
            }
        }
        return "NO";
    }


    public static String study(int[] peopleInLine){
        int bill25 = 0, bill50 = 0;
        for (int payment : peopleInLine){
            if(payment==25){
                bill25++;
            } else if(payment==50){
                bill25--;
                bill50++;
            } else if(payment==100){
                if(bill50>0){
                    bill50--;
                    bill25--;
                } else{
                    bill25-=3;
                }
            }
            if(bill25<0 || bill50 <0){
                return "NO";
            }
        }
        return "YES";
    }
}

