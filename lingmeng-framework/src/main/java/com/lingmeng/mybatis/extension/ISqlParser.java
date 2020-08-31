package com.lingmeng.mybatis.extension;

import org.apache.ibatis.reflection.MetaObject;

public interface ISqlParser {
    SqlInfo parser(MetaObject var1, String var2) throws Exception;
}
