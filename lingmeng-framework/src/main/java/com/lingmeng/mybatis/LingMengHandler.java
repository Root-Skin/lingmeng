package com.lingmeng.mybatis;

import net.sf.jsqlparser.expression.Expression;

import java.util.Map;

public interface LingMengHandler {

     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description 租户ID获取
      **/
    Expression getTenantId();

     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description 租户字段名获取
      **/
    String getTenantIdColumn();

     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description    根据表明判断是否需要过滤
      **/
    boolean doTableFilter(String var1);

     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description    判断是否是系统表
      **/
    boolean isSystemTable(String var1);

     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description 新增的时候 添加默认字段和对应的默认值
      **/
    Map<String, Expression> getInsertFields();
}
