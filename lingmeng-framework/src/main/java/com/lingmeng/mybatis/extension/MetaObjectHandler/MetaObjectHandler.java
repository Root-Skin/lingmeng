package com.lingmeng.mybatis.extension.MetaObjectHandler;


import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.Objects;

/**
 * @Author skin
 * @Date 2020/8/29
 * @Description 这是mp上带有的MetaObjectHandler(作自动填充功能)
 * MetaObject 含义是元数据对象
 **/

public interface MetaObjectHandler {

    /**
     * @Author skin
     * @Date 2020/8/29
     * @Description 自动填充哪些新字段
     **/
    void insertFill(MetaObject var1);

    /**
     * @Author skin
     * @Date 2020/8/29
     * @Description 自动更新哪个字段
     **/
    void updateFill(MetaObject var1);

    default MetaObjectHandler setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject) {
        if (Objects.nonNull(fieldVal)) {
            //此class是否有该属性的getter()和Setter方法
            if (metaObject.hasSetter(fieldName) && metaObject.hasGetter(fieldName)) {
                //有就设置值
                metaObject.setValue(fieldName, fieldVal);
                //分离代理对象
            } else if (metaObject.hasGetter("et")) {
                //获取代理对象中被代理的真实对象
                Object et = metaObject.getValue("et");
                if (et != null) {
                    //获取被代理对象的MetaObject,方便信息提取
                    MetaObject etMeta = SystemMetaObject.forObject(et);
                    if (etMeta.hasSetter(fieldName)) {
                        etMeta.setValue(fieldName, fieldVal);
                    }
                }
            }
        }
        return this;
    }

    default Object getFieldValByName(String fieldName, MetaObject metaObject) {
        if (metaObject.hasGetter(fieldName)) {
            return metaObject.getValue(fieldName);
        } else {
            return metaObject.hasGetter("et." + fieldName) ? metaObject.getValue("et." + fieldName) : null;
        }
    }

    default boolean openInsertFill() {
        return true;
    }

    default boolean openUpdateFill() {
        return true;
    }
}
