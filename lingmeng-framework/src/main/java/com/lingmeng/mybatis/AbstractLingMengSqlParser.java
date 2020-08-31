package com.lingmeng.mybatis;

import com.lingmeng.mybatis.extension.ISqlParser;
import com.lingmeng.mybatis.extension.SqlInfo;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Iterator;

/**
 * @Author skin
 * @Date 2020/8/26
 * @Description 抽象来实现接口中的一部分主功能(parse), 然后进行扩展
 **/

public abstract class AbstractLingMengSqlParser implements ISqlParser {

    protected final Log logger = LogFactory.getLog(this.getClass());

    //todo  这里没有构造方法
    @Override
    public SqlInfo parser(MetaObject metaObject, String sql) throws Exception {
        try {
            this.logger.debug("Original SQL: " + sql);
            StringBuilder sqlStringBuilder = new StringBuilder();
            Statements statements = CCJSqlParserUtil.parseStatements(sql);
            int i = 0;
            Iterator var6 = statements.getStatements().iterator();

            while (var6.hasNext()) {
                Statement statement = (Statement) var6.next();
                if (null != statement) {
                    if (i++ > 0) {
                        sqlStringBuilder.append(';');
                    }
                    sqlStringBuilder.append(this.processParser(statement).getSql());
                }
            }
            if (sqlStringBuilder.length() > 0) {
                return SqlInfo.newInstance().setSql(sqlStringBuilder.toString());
            }
        } catch (JSQLParserException var8) {
            throw new Exception("Failed to process, please exclude the tableName or statementId");
        }

        return null;
    }

    public SqlInfo processParser(Statement statement) {
        if (statement instanceof Insert) {
            this.processInsert((Insert) statement);
        } else if (statement instanceof Select) {
            this.processSelectBody(((Select) statement).getSelectBody());
        } else if (statement instanceof Update) {
            this.processUpdate((Update) statement);
        } else if (statement instanceof Delete) {
            this.processDelete((Delete) statement);
        }
        this.logger.debug("parser sql: " + statement.toString());
        return SqlInfo.newInstance().setSql(statement.toString());
    }

     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description 处理插入
      **/
    public abstract void processInsert(Insert var1);

     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description 处理删除
      **/
    public abstract void processDelete(Delete var1);

     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description 处理更新
      **/
    public abstract void processUpdate(Update var1);

     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description 处理select
      **/
    public abstract void processSelectBody(SelectBody var1);

}
