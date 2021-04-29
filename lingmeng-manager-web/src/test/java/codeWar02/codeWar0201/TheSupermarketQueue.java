package codeWar02.codeWar0201;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * @author skin
 * @createTime 2021年02月01日
 * @Description There is a queue for the self-checkout tills at the supermarket.
 * Your task is write a function to calculate the total time required for all the customers to check out!
 * <p>
 * customers: an array of positive integers representing the queue. Each integer represents a customer, and its value is the amount of time they require to check out.
 * n: a positive integer, the number of checkout tills.
 * <p>
 * The function should return an integer, the total time required.
 */

@Slf4j
public class TheSupermarketQueue {
    public static void main(String[] args) {

//        PriorityQueue<Integer> q = new PriorityQueue<>();
//        for (int i = 0; i < 3; i++)
//            q.add(0);
//
//        System.out.println(q);
//        q.add(q.remove()+1);
//        System.out.println(q);
        int[] customers = new int[]{ 2, 2, 3, 3, 4, 4};
        int n = 2;
        System.out.println(solveSuperMarketQueue(customers, n));
    }

    public static int solveSuperMarketQueue(int[] customers, int n) {
        //N维数组
//        int[] result = new int[n];
//        for(int i = 0; i < customers.length; i++){
//            result[0] += customers[i];
//            System.out.println(result);
//            //获取最小 以便于下次计算
//            Arrays.sort(result);
//        }
//        return result[n-1];


        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i = 0; i < n; i++)
            q.add(0);
        for (int t : customers){
            System.out.println(q);
            q.add(q.remove() + t);
        }
        return Collections.max(q);

    }

}
