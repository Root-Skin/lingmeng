package com.lingmeng.mybatis;

import lombok.Data;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;


import java.util.List;

@Data
public class LingMengSqlParser extends  AbstractLingMengSqlParser {
    private LingMengHandler lingMengHandler;


    @Override
    public void processInsert(Insert insert) {
        if (!this.lingMengHandler.doTableFilter(insert.getTable().getName())) {
            insert.getColumns().add(new Column(this.lingMengHandler.getTenantIdColumn()));
            if (insert.getSelect() != null) {
                this.processPlainSelect((PlainSelect)insert.getSelect().getSelectBody(), true);
            } else {
                //判断是否是批量插入实体
                if (insert.getItemsList() == null) {
                    try {
                        throw new Exception("Failed to process multiple-table update, please exclude the tableName or statementId");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                ItemsList itemsList = insert.getItemsList();
                if (itemsList instanceof MultiExpressionList) {
                    ((MultiExpressionList)itemsList).getExprList().forEach((el) -> {
                        el.getExpressions().add(this.lingMengHandler.getTenantId());
                    });
                } else {
                    ((ExpressionList)insert.getItemsList()).getExpressions().add(this.lingMengHandler.getTenantId());
                }
            }

        }
    }

    @Override
    public void processDelete(Delete update) {
        List<Table> tableList = update.getTables();
        if(null != tableList && tableList.size() < 2){
            throw new RuntimeException("Failed to process multiple-table update, please exclude the statementId");
        }
        Table table = (Table)tableList.get(0);
        if (!this.lingMengHandler.doTableFilter(table.getName())) {
            update.setWhere(this.andExpression(table, update.getWhere()));
        }
    }


    @Override
    public void processUpdate(Update update) {
        Table table = update.getTable();
        if (!this.lingMengHandler.doTableFilter(table.getName())) {
            update.setWhere(this.andExpression(table, update.getWhere()));
        }
    }

    @Override
    public void processSelectBody(SelectBody selectBody) {
        //是不是普通的select
        if (selectBody instanceof PlainSelect) {
            this.processPlainSelect((PlainSelect)selectBody);
        //是否包含with
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem)selectBody;
            if (withItem.getSelectBody() != null) {
                //得到select的语句
                this.processSelectBody(withItem.getSelectBody());
            }
        } else {
            //表示select  union select 这样的语句
            SetOperationList operationList = (SetOperationList)selectBody;
            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                //方法引用(this::processSelectBody)
                operationList.getSelects().forEach(this::processSelectBody);
            }
        }
    }

    protected void processPlainSelect(PlainSelect plainSelect) {
        //是否添加租户列
        this.processPlainSelect(plainSelect, false);
    }

    protected void processPlainSelect(PlainSelect plainSelect, boolean addColumn) {
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table) {
            Table fromTable = (Table)fromItem;
            //根据表明判断是否需要过滤
            if (lingMengHandler.doTableFilter(fromTable.getName())) {
                //判断是否是系统表 //todo
//                if (lingMengHandler.isSystemTable(fromTable.getName())) {
//                    Expression expression = builderExpression(plainSelect.getWhere(), fromTable, fields);
//                    if (expression != null) {
//                        plainSelect.setWhere(expression);
//                    }
//                }
                return;
            }
            plainSelect.setWhere(this.builderExpression(plainSelect.getWhere(), fromTable));
            if (addColumn) {
                plainSelect.getSelectItems().add(new SelectExpressionItem(new Column(this.lingMengHandler.getTenantIdColumn())));
            }
        } else {
            this.processFromItem(fromItem);
        }

        List<Join> joins = plainSelect.getJoins();
        if (joins != null && joins.size() > 0) {
            joins.forEach((j) -> {
                this.processJoin(j);
                this.processFromItem(j.getRightItem());
            });
        }

    }
     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description    处理条件
      **/
    protected Expression builderExpression(Expression expression, Table table) {
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(this.getAliasColumn(table));
        equalsTo.setRightExpression(this.lingMengHandler.getTenantId());
        //加入判断防止条件为空时生成 "and null" 导致查询结果为空
        if (expression == null) {
            return equalsTo;
        } else {
            if (expression instanceof BinaryExpression) {
                BinaryExpression binaryExpression = (BinaryExpression)expression;
                if (binaryExpression.getLeftExpression() instanceof FromItem) {
                    this.processFromItem((FromItem)binaryExpression.getLeftExpression());
                }

                if (binaryExpression.getRightExpression() instanceof FromItem) {
                    this.processFromItem((FromItem)binaryExpression.getRightExpression());
                }
            }

            return new AndExpression(equalsTo, expression);
        }
    }
     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description 字段别名设置 (tableName.tenantId 或 tableAlias.tenantId)
      **/
    protected Column getAliasColumn(Table table) {
        StringBuilder column = new StringBuilder();
        if (null == table.getAlias()) {
            column.append(table.getName());
        } else {
            column.append(table.getAlias().getName());
        }

        column.append(".");
        //默认添加租户列
        column.append(this.lingMengHandler.getTenantIdColumn());
        return new Column(column.toString());
    }


     /**
      * @Author skin
      * @Date  2020/8/26
      * @Description 处理子查询
      **/
    protected void processFromItem(FromItem fromItem) {
        //子查询是join类型
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin)fromItem;
            if (subJoin.getJoinList() != null) {
                subJoin.getJoinList().forEach(this::processJoin);
            }
            if (subJoin.getLeft() != null) {
                this.processFromItem(subJoin.getLeft());
            }
         //子查询是select类型的处理
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect)fromItem;
            if (subSelect.getSelectBody() != null) {
                this.processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {
            this.logger.debug("Perform a subquery, if you do not give us feedback");
        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect)fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    this.processSelectBody(subSelect.getSelectBody());
                }
            }
        }

    }
    protected void processJoin(Join join) {
        if (join.getRightItem() instanceof Table) {
            Table fromTable = (Table)join.getRightItem();
            if (this.lingMengHandler.doTableFilter(fromTable.getName())) {
                return;
            }
            join.setOnExpression(this.builderExpression(join.getOnExpression(), fromTable));
        }
    }


    protected BinaryExpression andExpression(Table table, Expression where) {
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(this.getAliasColumn(table));
        equalsTo.setRightExpression(this.lingMengHandler.getTenantId());
        return (BinaryExpression)(null != where ? new AndExpression(equalsTo, where) : equalsTo);
    }

}
