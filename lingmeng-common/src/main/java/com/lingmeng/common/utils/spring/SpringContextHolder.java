package com.lingmeng.common.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

 /**
  * @Author skin
  * @Date  2021/1/7
  * @Description Spring的ApplicationContext的持有者,可以用静态方法的方式获取spring容器中的bean
  **/
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    //Spring容器会检测容器中的所有Bean，如果发现某个Bean实现了ApplicationContextAware接口，Spring容器会在创建该Bean之后，
     // 自动调用该Bean的setApplicationContextAware()方法，调用该方法时，
     // 会将容器本身作为参数传给该方法——该方法中的实现部分将Spring传入的参数（容器本身）
     // 赋给该类对象的applicationContext实例变量，因此接下来可以通过该applicationContext实例变量来访问容器本身。

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(applicationContext);
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

     //通过name获取 Bean.
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

     //通过class获取Bean.
    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    private static void assertApplicationContext() {
        if (SpringContextHolder.applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }

}
