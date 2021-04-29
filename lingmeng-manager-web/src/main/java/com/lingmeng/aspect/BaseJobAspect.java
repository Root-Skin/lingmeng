package com.lingmeng.aspect;

import com.lingmeng.timed.Task;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @Author skin
 * @Date 2021/1/7
 * @Description 记录定时任务切面
 **/
@Aspect
@Component
public class BaseJobAspect {


//    环绕通知（round）：可以控制目标方法是否执行
    //设置环绕的切点(jobs包下任何类的任何方法名)
    @Around("execution(* com.lingmeng.task.jobs.*.*(..))")
    public void aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //目标方法所属类的简单类名
        String moduleName = joinPoint.getTarget().getClass().getSimpleName();
        //获取目标方法名
        String methodName = joinPoint.getSignature().getName();
        Task task = new Task();
        task.setId(moduleName + "." + methodName);
        task.setLastStartTime(Calendar.getInstance().getTime());
        boolean flag = true;
        Throwable temp = null;
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            temp = throwable;
            flag = false;
            task.setLastFailMsg(throwable.getMessage());
            if (throwable.getMessage().length() > 1000) {
                task.setLastFailMsg(throwable.getMessage().substring(0, 1000));
            }
        }
        //try catch后继续执行
        //默认成功
        task.setLastStartStatus(flag);
        task.setClassName(moduleName);
        task.insertOrUpdate();
        if (temp != null) {
            throw temp;
        }
    }
}
