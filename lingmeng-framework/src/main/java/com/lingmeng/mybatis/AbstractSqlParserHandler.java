package com.lingmeng.mybatis;


import com.lingmeng.mybatis.extension.ISqlParser;
import com.lingmeng.mybatis.extension.ISqlParserFilter;
import com.lingmeng.mybatis.extension.PluginUtils;
import com.lingmeng.mybatis.extension.SqlInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.executor.statement.CallableStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.List;

@Data
@Accessors(chain = true)
public abstract class AbstractSqlParserHandler {
    //这个就是定义的解析器的接口，只需要将定义的解析器是实现类加如集合中，就会进行解析
    private List<ISqlParser> sqlParserList;
    private ISqlParserFilter sqlParserFilter;

    /**
     * 拦截 SQL 解析执行
     */
    protected void sqlParser(MetaObject metaObject) {
        if (null != metaObject) {
            Object originalObject = metaObject.getOriginalObject();
            StatementHandler statementHandler = PluginUtils.realTarget(originalObject);
            metaObject = SystemMetaObject.forObject(statementHandler);

            //1、这一句就是SqlParserFilter的作用、如果SqlParserFilter不等于空、并且执行SqlParserFilter接口的doFilter
            // 方法的到的结果值为 true 的话就不往下执行了
            if (null != this.sqlParserFilter && this.sqlParserFilter.doFilter(metaObject)) {
                return;
            }
            //2、这个是用于@SqlParser注解的、如果@SqlParser注解值为 true 则不往下执行、这个是放在Mapper接口上的
            // @SqlParser(filter = true) 跳过该方法解析
//            if (SqlParserHelper.getSqlParserInfo(metaObject)) {
//                return;
//            }

            // SQL 解析
            if (CollectionUtils.isNotEmpty(this.sqlParserList)) {
                // 好像不用判断也行,为了保险起见,还是加上吧.
                statementHandler = metaObject.hasGetter("delegate") ? (StatementHandler) metaObject.getValue("delegate") : statementHandler;
                //CallableStatementHandler是预编译sql的接口
                //不是预编译的接口
                if (!(statementHandler instanceof CallableStatementHandler)) {
                    // 标记是否修改过 SQL
                    boolean sqlChangedFlag = false;
                    String originalSql = (String) metaObject.getValue(PluginUtils.DELEGATE_BOUNDSQL_SQL);
                    //这里就是解析器的解析过程，遍历了所有的解析器，依次对sql进行重构
                    for (ISqlParser sqlParser : this.sqlParserList) {
                        //接口中的parser方法实现了重构的过程
                        SqlInfo sqlInfo = null;
                        try {
                            sqlInfo = sqlParser.parser(metaObject, originalSql);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (null != sqlInfo) {
                            originalSql = sqlInfo.getSql();
                            sqlChangedFlag = true;
                        }
                    }
                    if (sqlChangedFlag) {
                        metaObject.setValue(PluginUtils.DELEGATE_BOUNDSQL_SQL, originalSql);
                    }
                }
            }
        }
    }
}