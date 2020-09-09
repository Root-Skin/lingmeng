package com.lingmeng.base.lingmengPlug.extension;


import org.apache.ibatis.reflection.MetaObject;

public interface MetaObjectHandler {
    void insertFill(MetaObject var1);

    void updateFill(MetaObject var1);

}
