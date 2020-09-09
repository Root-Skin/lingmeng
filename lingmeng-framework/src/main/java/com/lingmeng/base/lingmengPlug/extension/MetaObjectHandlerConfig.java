package com.lingmeng.base.lingmengPlug.extension;


import com.lingmeng.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
  * @Author skin
  * @Date  2020/9/9
  * @Description 自动填充功能
  **/
@Component
@Slf4j
public class MetaObjectHandlerConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //MetaObject是Mybatis提供的一个用于方便、优雅访问对象属性的对象
        Object createdTime = metaObject.getValue("createdTime");
        Object createdBy = metaObject.getValue("createdBy");
        Object updatedTime = metaObject.getValue("updatedTime");
        Object updatedBy = metaObject.getValue("updatedBy");

        Object obj = UserUtil.get();
        UserVo userVo = null;
        if (obj != null) {
            try {
                userVo = (UserVo) obj;
            } catch (Exception e) {
            }
        }
        if (null == createdTime) {
            metaObject.setValue("createdTime", new Date());
        }
        if (null == updatedTime) {
            metaObject.setValue("updatedTime", new Date());
        }
//        if (null == createdBy) {
//            if (userVo != null) {
//                metaObject.setValue("createBy", userVo.getId());
//            } else {
//                if (UserUtil.getUserId() != null && !"".equals(UserUtil.getUserId())) {
//                    metaObject.setValue("createBy", UserUtil.getUserId());
//                } else {
//                    metaObject.setValue("createBy", "1");
//                }
//            }
//        }
//        if (null == updatedBy) {
//            if (userVo != null) {
//                metaObject.setValue("updateBy", userVo.getId());
//            } else {
//                if (UserUtil.getUserId() != null && !"".equals(UserUtil.getUserId())) {
//                    metaObject.setValue("updateBy", UserUtil.getUserId());
//                } else {
//                    metaObject.setValue("updateBy", "1");
//                }
//            }
//        }
    }

    @Override
    public void updateFill(MetaObject var1) {

    }
}
