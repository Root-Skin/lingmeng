package com.lingmeng.mybatis.extension;

import org.apache.ibatis.reflection.MetaObject;

public interface ISqlParserFilter {
    boolean doFilter(MetaObject var1);
}
