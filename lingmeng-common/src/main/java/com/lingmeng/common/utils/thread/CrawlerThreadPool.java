package com.lingmeng.common.utils.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Slf4j
public class CrawlerThreadPool {


    //volatile 实现全局 同步
    private static volatile CrawlerThreadPool crawlerThreadPool = null;

    /**
     * 线程池最大连接数 IO密集型 2n+1
     */
    //得到JVM可以用来工作的核心数(随时在进行变动)
    private static final int threadNum = Runtime.getRuntime().availableProcessors();
    /**
     * 创建一个阻塞队列
     */
    private static final ArrayBlockingQueue queue = new ArrayBlockingQueue<Runnable>(10000);


    private ThreadPoolExecutor pool;


    private CrawlerThreadPool() {

        pool = new ThreadPoolExecutor(1, threadNum,
                60L, TimeUnit.SECONDS, queue) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                log.info("线程池开始执行任务，threadName:{},线程池堆积数量：{}", t.getName(), queue.size());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                log.info("线程池开始执行完成");
                if (null != t) {
                    log.error(t.getLocalizedMessage());
                }
            }

        };
    }

    private CrawlerThreadPool(String threadName) {
        //线程工厂
        ThreadFactory namedThread = new ThreadFactoryBuilder().setNameFormat("async_thread_pool_%d").build();
        pool = new ThreadPoolExecutor(1, threadNum,
                60L, TimeUnit.SECONDS, queue, namedThread) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                log.info("线程池开始执行任务，threadName:{},线程池堆积数量：{}", t.getName(), queue.size());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                log.info("线程池开始执行完成");
                if (null != t) {
                    log.error(t.getLocalizedMessage());
                }
            }
        };
    }


    /**
     * 提交一个线程
     *
     * @param runnable
     */
//    public static void submit(Runnable runnable) {
//        log.info("线程池添加任务,线程池堆积任务数量：{},最大线程数:{}", queue.size(), threadNum);
//        executorService.execute(runnable);
//    }

    /**
     * 提交任务
     *
     * @param runnable
     */
    public void exec(Runnable runnable) {
        pool.execute(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error(String.valueOf(e));
            }
        });
    }

    /**
     * 获取异步线程对象
     *
     * @return
     */
    public static CrawlerThreadPool getInstance(String threadName) {
        if (null == crawlerThreadPool) {
            synchronized (CrawlerThreadPool.class) {
                if (null == crawlerThreadPool) {
                    crawlerThreadPool = new CrawlerThreadPool(threadName);
                }
            }
        }
        return crawlerThreadPool;
    }

    public static CrawlerThreadPool getInstance() {
        if (null == crawlerThreadPool) {
            synchronized (CrawlerThreadPool.class) {
                if (null == crawlerThreadPool) {
                    crawlerThreadPool = new CrawlerThreadPool();
                }
            }
        }
        return crawlerThreadPool;
    }


}
