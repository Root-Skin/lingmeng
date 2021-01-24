package com.lingmeng.demo;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.lingmeng.anotation.PreventRepetitionAnnotation;
import com.lingmeng.base.RestReturn;
import com.lingmeng.cache.JedisUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/demo")
public class LockDemoController {



    @CreateCache
    private Cache<String, Long> cache;

    /**
     * 注解方式
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/anntionTest")
    @PreventRepetitionAnnotation
    public RestReturn add(HttpServletRequest request) {
        try {
            //模拟执行业务逻辑需要的时间
            Thread.sleep(5000);
            System.out.println("哇哈哈");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return RestReturn.ok();
    }


    /**
     * 锁方式 本地锁方式
     */
    @PostMapping("/localLockTest")
    @ResponseBody
    public RestReturn lockTest(HttpServletRequest request) {
        try {
            synchronized (this) {
                //模拟执行业务逻辑需要的时间
                Thread.sleep(5000);
                System.out.println("hhaa");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return RestReturn.ok();
    }

    /**
     * 分布式锁使用
     */
    @PostMapping("/remotLockTest")
    @ResponseBody
    public RestReturn remotLockTest(HttpServletRequest request, String tokenFlag, String requestFlag) {
        try {
            boolean lock = JedisUtil.tryGetDistributedLock(tokenFlag + requestFlag, requestFlag, 60000);
            System.out.println("lock:" + lock + "," + Thread.currentThread().getName());
            if (lock) {
                //加锁成功
                //执行方法
                //模拟执行业务逻辑需要的时间
                Thread.sleep(5000);
                //相应的业务代码
                //方法执行完之后进行解锁
                JedisUtil.releaseDistributedLock(tokenFlag + requestFlag, requestFlag);
                return RestReturn.ok("提交成功");
            } else {
                //锁已存在
                return RestReturn.error("不能重复提交！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return RestReturn.ok();
    }


    /**
     * 分布式锁使用 实例二
     */
    @PostMapping("/remotLockTest1")
    @ResponseBody
    public RestReturn remotLockTest1(HttpServletRequest request, String tokenFlag, String requestFlag) {

        boolean lockFlag= cache.tryLockAndRun("key" + tokenFlag + requestFlag, 6000, TimeUnit.SECONDS, () ->
                {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        if(lockFlag){
            return RestReturn.error("不能重复提交！");

        }
        return RestReturn.ok();
    }
}
