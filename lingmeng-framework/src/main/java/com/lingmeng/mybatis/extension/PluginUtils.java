package com.lingmeng.mybatis.extension;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.StringUtils;

import java.lang.reflect.Proxy;
import java.util.Properties;

public final class PluginUtils {
    public static final String DELEGATE_BOUNDSQL_SQL = "delegate.boundSql.sql";

    private PluginUtils() {
    }

    public static <T> T realTarget(Object target) {
        //判断是不是代理类
        if (Proxy.isProxyClass(target.getClass())) {
            //分离代理对象。由于会形成多次代理，从而方便提取信息
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        } else {
            return (T)target;
        }
    }

    public static String getProperty(Properties properties, String key) {
        String value = properties.getProperty(key);
        return StringUtils.isEmpty(value) ? null : value;
    }
}
