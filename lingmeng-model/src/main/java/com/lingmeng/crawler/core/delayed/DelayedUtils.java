package com.lingmeng.crawler.core.delayed;

import com.lingmeng.crawler.core.callback.ConcurrentCallBack;
import com.lingmeng.crawler.core.callback.DelayedCallBack;
import lombok.Data;

public class DelayedUtils {



    /**
     * 数据锁
     */
    private  static final String LINGMENG_SYNCHRONIZED_TAG = "LINGMENG_SYNCHRONIZED_TAG";


    public static void delayed(long delayedTime) {
        try {
            Thread.sleep(delayedTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 延时(CrawlerCookie)方法
     *
     * @param callBack
     */
    public static Object delayed(DelayedCallBack callBack) {
        boolean flag = false;
        long sleepTime = callBack.sleepTime();
        long timeOut = callBack.timeOut();
        long currentTime = System.currentTimeMillis();
        Object obj = null;
        while (true) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long duration = System.currentTimeMillis() - currentTime;
            boolean isExist = callBack.isExist();
            obj = callBack.callBack(duration);
            if (isExist) {
                flag = true;
                // 持续时间达到超时时间自动退出
            } else if (duration > timeOut) {
                flag = true;
            }
            if (flag) {
                break;
            }
        }
        return obj;
    }


    /**
     * 并发过滤(锁住类对象)-->这里的加锁对象是this
     */
    public  static ConcurrentCallBack getConcurrentFilter(final long time) {
        synchronized (LINGMENG_SYNCHRONIZED_TAG) {
            //注意这里的final(每次进来都是同一个对象)
            final ConcurrentEntity concurrentEntity = new ConcurrentEntity();
            concurrentEntity.setTimeInterval(time);
            return new ConcurrentCallBack() {
                public boolean filter() {
                    boolean flag = false;
                    //数据初始化
                    long duration = System.currentTimeMillis() - concurrentEntity.getCurrentTime();
                    //判断持续时间是否大于固定传入的时间    //传入时间是5s
                    if (duration > time) {
                        concurrentEntity.setAvailable(true);
                        concurrentEntity.setCallCount(0);
                        concurrentEntity.setCurrentTime(System.currentTimeMillis());
                    }
                    long callCount = concurrentEntity.getCallCount();
                    concurrentEntity.setCallCount(++callCount);
                    //
                    if (callCount <= 1 && concurrentEntity.isAvailable()) {
                        flag = true;
                        concurrentEntity.setAvailable(false);
                    }
                    return flag;
                }
            };
        }
    }

    @Data
    static class ConcurrentEntity {
        /**
         * 当前时间
         */
        private long currentTime = System.currentTimeMillis();
        /**
         * 时间区间(10s)
         */
        private long timeInterval = 10000;

        /**
         * 是否可用
         */
        private boolean available = true;

        /**
         * 调用次数
         */
        private long callCount = 0;

    }
}
