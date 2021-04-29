package com.lingmeng.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//因为使用@Scheduled 的定时任务虽然是异步执行的，但是，不同的定时任务之间并不是并行的！！！！！！！！
//在其中一个定时任务没有执行完之前，其他的定时任务即使是到了执行时间，也是不会执行的，它们会进行排队。
@Configuration
@EnableScheduling
public class TaskConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

     //spring容器关闭时,关掉定时任务
    @Bean(destroyMethod = "shutdown")
    public ExecutorService taskExecutor() {
        //指定线程池大小
        return Executors.newScheduledThreadPool(5);
    }
}
