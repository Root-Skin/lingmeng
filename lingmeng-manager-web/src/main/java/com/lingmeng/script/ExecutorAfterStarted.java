package com.lingmeng.script;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingmeng.anotation.Execute;
import com.lingmeng.anotation.Priority;
import com.lingmeng.common.config.PackageConfig;
import com.lingmeng.common.utils.spring.SpringContextHolder;
import com.lingmeng.dao.log.SysExecutedMethodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author skin
 * @Date 2021/1/10
 * @Description 项目启动后的执行器
 **/
@Component
@Slf4j
public class ExecutorAfterStarted implements ApplicationListener<ContextRefreshedEvent>, MethodLifeCycle {
    @Autowired
    private SpringContextHolder springContextHolder;
    @Autowired
    PackageConfig packageConfig;
    @Autowired
    private SysExecutedMethodsMapper sysExecutedMethodsMapper;


    private Map<String, SysExecutedMethods> executedMethods;
    private Set<String> classes;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final String time = sdf.format(new Date());
    private Map<String, Queue<InvokMethod>> methodPriorityQueues;
    private ExecutedPostProcessor firstProcessor;
    private MethodLifeCycle lifeCycle;


    /**
     * @Author skin
     * @Date 2021/1/10
     * @Description //默认数据库,含有一个优先级队列
     **/
    private void initQueue() {
        methodPriorityQueues = new HashMap<>(1);
        Queue<InvokMethod> methodPriorityQueue = new PriorityQueue<>();
        methodPriorityQueues.put("default", methodPriorityQueue);

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("启动执行");
        init();
        dealMethods();
        execMethods(methodPriorityQueues.get("default"));
    }

    /**
     * 初始化
     */
    private void init() {
        initQueue();
        //默认处理器
        firstProcessor = new DefaultExecutedPostProcessor();
        executedMethods = new HashMap<>();
        classes = new HashSet<>();
        //设置默认生命周期
        lifeCycle = new DefaultMethodLifeCycle();
        //扫描需要自动执行的脚本
        doScanner(packageConfig.getSqlScriptPackage());
    }

    /**
     * @Author skin
     * @Date 2021/1/11
     * @Description 根据优先级排序扫描出来的方法
     **/
    private void dealMethods() {
        try {
            if (classes.size() > 0) {
                for (String classStr : classes) {
                    Class c = Class.forName(classStr);
                    Method[] methods = c.getDeclaredMethods();
                    for (Method m : methods) {
                        final Execute executeAnnotation = m.getAnnotation(Execute.class);
                        if (m.isAnnotationPresent(Execute.class) &&
                                ObjectUtils.isNotEmpty(executeAnnotation)) {

                            String[] timeArray = executeAnnotation.timeArray();
                            String timeValue = executeAnnotation.time();
                            if (StringUtils.isNotEmpty(timeValue) || ObjectUtils.isNotEmpty(timeArray)) {
                                Set<String> execTimes = new HashSet<>();
                                if (ObjectUtils.isNotEmpty(timeArray)) {
                                    execTimes.addAll(Arrays.asList(timeArray));
                                }
                                if (StringUtils.isNotEmpty(timeValue)) {
                                    execTimes.add(timeValue);
                                }

                                //执行时间在当天,并且没有执行完成
                                if (execTimes.contains(time) && !isExecuted2(m, c, execTimes)) {
                                    //前置处理
                                     firstProcessor.postProcessBeforeInitialization(m, c);
                                    int order = 0;
                                    //todo  测试一下没有ObjectUtils.isNotEmpty(m.getAnnotation(Priority.class)
                                    if (m.isAnnotationPresent(Priority.class) && ObjectUtils.isNotEmpty(m.getAnnotation(Priority.class))) {
                                        order = m.getAnnotation(Priority.class).value();
                                    }
                                    methodPriorityQueues.get("default").add(new InvokMethod().setKey(generateId(c, m)).setMethod(m).setOrder(order).setAClass(c));
                                }
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * @Author skin
     * @Date 2021/1/11
     * @Description 执行优先级队列方法
     **/
    private void execMethods(Queue<InvokMethod> methodPriorityQueue) {
        while (methodPriorityQueue.size() > 0) {
            InvokMethod invokMethod = methodPriorityQueue.poll();
            Class c = invokMethod.getAClass();
            Method m = invokMethod.getMethod();

            SysExecutedMethods sysExecutedMethods = dealSysExecutedMethods(c, m);

            //待执行
            SysExecutedMethods reSysExecutedMethods = this.waiting(sysExecutedMethodsMapper, sysExecutedMethods);
            try {
                //执行中
                this.invoking(sysExecutedMethodsMapper, reSysExecutedMethods);
                m.invoke(springContextHolder.getBean(c),null);
                //后置处理
                firstProcessor.postProcessAfterInitialization(m, c);
                //执行完成
                this.over(sysExecutedMethodsMapper, reSysExecutedMethods);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("执行过程出错",e);
                //执行执行异常
                this.exception(sysExecutedMethodsMapper, reSysExecutedMethods);
            }
        }
    }

    private SysExecutedMethods dealSysExecutedMethods(Class c, Method m) {
        SysExecutedMethods sysExecutedMethods = new SysExecutedMethods();
        sysExecutedMethods.setMethodName(generateId(c, m))
                .setMethodTime(time)
                .setMethodParams(dealParams(m.getAnnotation(Execute.class).params()));
        return sysExecutedMethods;
    }

    /**
     * @Author skin
     * @Date 2021/1/11
     * @Description 处理注解上的时间参数
     **/
    private String dealParams(String[] params) {
        StringBuffer sb = new StringBuffer();
        sb.append(" [ ");
        for (String s : params) {
            sb.append(s).append(",");
        }
        sb.append(" ] ");

        return sb.toString();
    }


    /**
     * @Author skin
     * @Date 2021/1/11
     * @Description 判断方法是否已经执行
     **/
    private boolean isExecuted2(Method m, Class c, Set<String> execTimes) {
        if (CollectionUtils.isEmpty(execTimes)) {
            return true;
        }
        List<SysExecutedMethods> sysExecutedMethods = sysExecutedMethodsMapper.selectList(new QueryWrapper<SysExecutedMethods>().in("method_time", execTimes).eq("method_name", generateId(c, m)).and(wrapper -> wrapper.eq("status", LifeCycleEnum.OVER)));
        return sysExecutedMethods.size() > 0;
    }


    //扫描sql脚本包(得到具体类名)
    private void doScanner(String basePackage) {
        ClassPathScanningCandidateComponentProvider p = new ClassPathScanningCandidateComponentProvider(true);
        Set<BeanDefinition> candidates = p.findCandidateComponents(basePackage);
        for (BeanDefinition candidate : candidates) {
            String className = null;
            try {
                className = Class.forName(candidate.getBeanClassName()).getName();
                log.info("Executor scan class contains:" + className);
                classes.add(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateId(Class c, Method m) {
        return c.getName() + " : " + m.getName();
    }

    @Override
    public SysExecutedMethods waiting(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods) {
        return lifeCycle.waiting(sysExecutedMethodsMapper, methods);
    }

    @Override
    public void invoking(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods) {
        lifeCycle.invoking(sysExecutedMethodsMapper, methods);
    }

    @Override
    public void exception(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods) {
        lifeCycle.exception(sysExecutedMethodsMapper, methods);
    }

    @Override
    public void over(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods) {
        lifeCycle.over(sysExecutedMethodsMapper, methods);
    }
}
