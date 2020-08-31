package com.lingmeng.mybatis;


import net.sf.jsqlparser.expression.Expression;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LingMengHandlerImpl implements LingMengHandler {
    @Override
    public Expression getTenantId() {
        return null;
    }

    @Override
    public String getTenantIdColumn() {
        return null;
    }

    @Override
    public boolean doTableFilter(String var1) {
        return false;
    }

    @Override
    public boolean isSystemTable(String var1) {
        return false;
    }

    @Override
    public Map<String, Expression> getInsertFields() {
        Date  now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(now);
        Map<String, Expression> map = new HashMap<>();

        return null;
    }
}
