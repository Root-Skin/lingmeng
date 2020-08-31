package com.lingmeng.mybatis.extension.MetaObjectHandler;

import com.lingmeng.util.UserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MetaObjectHandlerConfig implements  MetaObjectHandler {
    @Override
    //todo  这里是插入数据
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName("createdTime", metaObject);
        Object createBy = getFieldValByName("createdBy", metaObject);
        Object updatedTime = getFieldValByName("updatedTime", metaObject);
        Object updatedBy = getFieldValByName("updatedBy", metaObject);

        Object obj = UserUtil.get();


    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
