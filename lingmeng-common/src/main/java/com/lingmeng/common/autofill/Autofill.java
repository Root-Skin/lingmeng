package com.lingmeng.common.autofill;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Autofill implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createdTime = getFieldValByName("createdTime", metaObject);
        if(createdTime==null){
            setFieldValByName("createdTime",new Date(),metaObject);
        }
        Object updatedTime = getFieldValByName("updatedTime", metaObject);
        if(createdTime==null){
            setFieldValByName("updatedTime",new Date(),metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updatedTime",new Date(),metaObject);
    }
}
