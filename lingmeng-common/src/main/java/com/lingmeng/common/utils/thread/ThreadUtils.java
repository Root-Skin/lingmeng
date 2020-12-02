package com.lingmeng.common.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {

    //创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    private static final ExecutorService es = Executors.newFixedThreadPool(10);

    public static void execute(Runnable runnable) {
        es.submit(runnable);
    }
}
