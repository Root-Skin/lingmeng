package Multithreading;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author skin
 * @createTime 2021年03月31日
 * @Description  多线程练习
 */
@Slf4j
public class Multithreading1 {

    public static void main(String[] args) {
        ThreadFactory namedThread = new ThreadFactoryBuilder().setNameFormat("skin" + "_%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5,8,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10240),namedThread,new ThreadPoolExecutor.AbortPolicy());
        for(int i = 0 ; i < 100 ; i += 5){
            int finalI = i;
            pool.execute(() -> {
                for(int j=0;j<5;j++){
                    System.out.println(Thread.currentThread().getName()+j);
                }

            });
        }
    }



}
