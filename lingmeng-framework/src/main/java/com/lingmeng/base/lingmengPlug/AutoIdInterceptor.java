package com.lingmeng.base.lingmengPlug;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.reflections.ReflectionUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


 /**
  * @Author skin
  * @Date  2020/9/8
  * @Description mybatis  自增拦截器   其实也就是SqlSession的新增，删除，修改操作都会被拦截器拦截到
  **/
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class
        }),
})
@Component
public class AutoIdInterceptor implements Interceptor {

    /**
     * key值为class对象 value可以理解成是该类带有AutoId注解的属性，只不过对属性封装了一层。
     * 它是非常能够提高性能的处理器 它的作用就是不用每一次一个对象经来都要看下它的哪些属性带有AutoId注解
     * 毕竟类的反射在性能上并不友好。只要key包含该对象那就不需要检查它哪些属性带AutoId注解。
     */
    private Map<Class, List<Handler>> handlerMap = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //这里的invocation 就是我们的Executor 的update方法
        Object[] args = invocation.getArgs();
        //args数组对应对象就是上面@Signature注解中args对应的对应类型
        MappedStatement mappedStatement = (MappedStatement) args[0];
        //实体对象
        Object entity = args[1];
        if ("INSERT".equalsIgnoreCase(mappedStatement.getSqlCommandType().name())) {
            // 获取实体集合
            Set<Object> entitySet = getEntitySet(entity);
            // 批量设置id
            for (Object object : entitySet) {
                process(object);
            }
        }
        return invocation.proceed();
    }

    /**
     * object是需要插入的实体数据,它可能是对象,也可能是批量插入的对象。
     * 如果是单个对象,那么object就是当前对象
     * 如果是批量插入对象，那么object就是一个map集合,key值为"list",value为ArrayList集合对象
     */
    private Set<Object> getEntitySet(Object object) {
        //
        Set<Object> set = new HashSet<>();
        if (object instanceof Map) {
            //批量插入对象
            Collection values = (Collection) ((Map) object).get("list");
            System.out.println("values = " + values);
            for (Object value : values) {
                if (value instanceof Collection) {
                    set.addAll((Collection) value);
                } else {
                    set.add(value);
                }
            }
        } else {
            //单个插入对象
            set.add(object);
        }
        return set;
    }

    private void process(Object object) throws Throwable {
        //得到插入对象的的类
        Class handlerKey = object.getClass();
        //handlerList 是AutoId注解的属性
        List<Handler> handlerList = handlerMap.get(handlerKey);

        //TODO 性能优化点，如果有两个都是user对象同时,那么只需有个进行反射处理属性就好了,另一个只需执行下面的for循环
        SYNC:
        if (handlerList == null) {
            synchronized (this) {
                handlerList = handlerMap.get(handlerKey);
                //如果到这里map集合已经存在，则跳出到指定SYNC标签
                if (handlerList != null) {
                    break SYNC;
                }
                handlerMap.put(handlerKey, handlerList = new ArrayList<>());
                // 反射工具类 获取带有AutoId注解的所有属性字段
                Set<Field> allFields = ReflectionUtils.getAllFields(
                        object.getClass(),
                        (com.google.common.base.Predicate<? super Field>) input -> input != null && input.getAnnotation(AutoId.class) != null
                );
                for (Field field : allFields) {
                    AutoId annotation = field.getAnnotation(AutoId.class);
                    //1、添加UUID字符串作为主键
                    if (field.getType().isAssignableFrom(String.class)) {
                        if (annotation.value().equals(AutoId.IdType.UUID)) {
                            handlerList.add(new UUIDHandler(field));
                            //2、添加String类型雪花ID
                        } else if (annotation.value().equals(AutoId.IdType.SNOWFLAKE)) {
                            handlerList.add(new UniqueLongHexHandler(field));
                        }
                    } else if (field.getType().isAssignableFrom(Long.class)) {
                        //3、添加Long类型的雪花ID
                        if (annotation.value().equals(AutoId.IdType.SNOWFLAKE)) {
                            handlerList.add(new UniqueLongHandler(field));
                        }
                    }
                }
            }
        }
        for (Handler handler : handlerList) {
            handler.accept(object);
        }
    }

     /**
      * @Author skin
      * @Date  2020/9/9
      * @Description 静态内部类(可以被实例化)
      **/
    private static abstract class Handler {
        Field field;

        Handler(Field field) {
            this.field = field;
        }
        //具体实现子类使用
        abstract void handle(Field field, Object object) throws Throwable;

        private boolean checkField(Object object, Field field) throws IllegalAccessException {
            if (!field.isAccessible()) {
                //实际上setAccessible是启用和禁用访问安全检查的开关,并不是为true就能访问为false就不能访问
                field.setAccessible(true);
            }
            //如果该注解对应的属性已经被赋值，那么就不用通过雪花生成的ID
            return field.get(object) == null;
        }

        public void accept(Object o) throws Throwable {
            if (checkField(o, field)) {
                handle(field, o);
            }
        }
    }

    private static class UUIDHandler extends Handler {
        UUIDHandler(Field field) {
            super(field);
        }

        /**
         * 1、插入UUID主键
         */
        @Override
        void handle(Field field, Object object) throws Throwable {
            field.set(object, UUID.randomUUID().toString().replace("-", ""));
        }
    }

    private static class UniqueLongHandler extends Handler {
        UniqueLongHandler(Field field) {
            super(field);
        }

        /**
         * 2、插入Long类型雪花ID
         */
        @Override
        void handle(Field field, Object object) throws Throwable {
            field.set(object, SnowIdUtils.uniqueLong());
        }
    }

    private static class UniqueLongHexHandler extends Handler {
        UniqueLongHexHandler(Field field) {
            super(field);
        }

        /**
         * 3、插入String类型雪花ID
         */
        @Override
        void handle(Field field, Object object) throws Throwable {
            field.set(object, SnowIdUtils.uniqueLongHex());
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
