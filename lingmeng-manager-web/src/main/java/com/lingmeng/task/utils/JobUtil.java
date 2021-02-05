package com.lingmeng.task.utils;

import com.lingmeng.common.config.PackageConfig;
import com.lingmeng.common.utils.spring.SpringContextHolder;
import com.lingmeng.dao.task.TaskMapper;
import com.lingmeng.timed.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

 /**
  * @Author skin
  * @Date  2021/1/7
  * @Description
  **/
 @RestController
@Slf4j
public class JobUtil {

    @Autowired
    private PackageConfig packageConfig;

    @Autowired
    private TaskMapper taskMapper;
     @Autowired
     private SpringContextHolder springContextHolder;

     /**
      * @Author skin
      * @Date  2021/1/7
      * @Description 启动时校验每天执行的自动任务是否正常，若未执行则执行一次
      **/
     @GetMapping("/listScan")
    public void scanPack() throws ParseException {
        //在某一个指定的包内，使用指定的包含过滤器和排斥过滤器扫描匹配的bean组件定义。
        ClassPathScanningCandidateComponentProvider p = new ClassPathScanningCandidateComponentProvider(true);
        Set<BeanDefinition> candidates = p.findCandidateComponents(packageConfig.getTaskJobPackage());

        Date current = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tomorrow = dateFormat.parse(dateFormat.format(calendar.getTime()));

        for (BeanDefinition candidate : candidates) {
            Class<?> clazz;
            try {
                //获取bean对象className <bean class="">
                clazz = Class.forName(candidate.getBeanClassName());
            } catch (ClassNotFoundException ignored) {
                continue;
            }
            Method[] methods = clazz.getMethods();
            CronSequenceGenerator cronSequenceGenerator;
            for (Method method : methods) {
                if (method.isAnnotationPresent(Scheduled.class)){
                    //它们解决的问题是如何根据任务的上一次执行时间，计算出符合cron表达式的下一次执行时间
                    cronSequenceGenerator = new CronSequenceGenerator(method.getAnnotation(Scheduled.class).cron());
                    //如果下次执行时间在今天24点之后，说明今天已经执行过了。需要检查是否执行
                    //tomorrow 只有年月日
                    //current 含有时分秒(java中Date是含有时分秒的)
                    System.out.println(tomorrow);
                    System.out.println(cronSequenceGenerator.next(current));
                    if (tomorrow.before(cronSequenceGenerator.next(current))) {
                        Task record = taskMapper.selectById(clazz.getSimpleName()+"."+method.getName());
                        if (ObjectUtils.isEmpty(record)||(ObjectUtils.isNotEmpty(record)&&(record.getLastStartTime().before(dateFormat.parse(dateFormat.format(current)))))) {
                            //如果没有查到，说明都没有执行，全部需要触发
                            try {
                                log.info("校验到未执行的自动任务：{},开始执行",clazz.getSimpleName()+"."+method.getName());
                                method.invoke(springContextHolder.getBean(clazz), null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
