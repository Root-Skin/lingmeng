package com.lingmeng.mybatis;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Author skin
 * @Date 2020/8/25
 * @Description mybatis的自定义拦截器
 * 1.StatementHandler 拦截Sql语法构建的处理
 **/
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MySqlInterceptor extends AbstractSqlParserHandler implements Interceptor {


    private final static String REGEX = "^\\s*[Ss][Ee][Ll][Ee][Cc][Tt].*$";

    //Invocation 只带StatementHandler中prepare的调用
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        // BoundSql类中有一个sql属性，即为待执行的sql语句
        BoundSql boundSql = handler.getBoundSql();
        String sql = boundSql.getSql();
        if (sql.matches(REGEX)) {
            // delegate是RoutingStatementHandler通过mapper映射文件中设置的statementType来指定具体的StatementHandler
            Object delegate = getFieldValue(handler, "delegate");
            // rowBounds,即为Mybais 原生的Sql 分页参数,由于Rowbounds 在BaseStateHandler中所以我们需要去找父类
            RowBounds rowBounds = (RowBounds) getFieldValue(delegate, "rowBounds");
            // 如果rowBound不为空，且rowBounds的起始位置不为0，则代表我们需要进行分页处理
            if (rowBounds != null) {
                // assemSql(...)完成对sql语句的装配及rowBounds的重置操作
                setFieldValue(boundSql, "sql", assemSql(sql, rowBounds));
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private Object getFieldValue(Object object, String fieldName) {
        Field field = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                if (field != null) {
                    //取消权限检查
                    field.setAccessible(true);
                    break;
                }

            } catch (NoSuchFieldException e) {
                //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
            }
        }
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setFieldValue(Object object, String fieldName, Object value) {
        Field field = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    try {
                        //将该对象上的此field设置为value值
                        field.set(object, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }

            } catch (NoSuchFieldException e) {
                //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
            }
        }
    }

     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description    重构物理分页
      **/
    public String assemSql(String oldSql, RowBounds rowBounds) throws Exception {
        String sql = oldSql + " limit " + rowBounds.getOffset() + "," + rowBounds.getLimit();
        // 这两步是必须的，因为在前面置换好sql语句以后，实际的结果集就是我们想要的,所以offset和limit必须重置为初始值
//        setFieldValue(rowBounds, "offset", RowBounds.NO_ROW_OFFSET);
//        setFieldValue(rowBounds, "limit", RowBounds.NO_ROW_LIMIT);
        return sql;
    }
}
