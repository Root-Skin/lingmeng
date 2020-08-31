package com.lingmeng.mybatis.extension;

import lombok.Data;

@Data
public class SqlInfo {
    private String sql;
    private boolean orderBy = true;

    public SqlInfo() {
    }
    public static SqlInfo newInstance() {
        return new SqlInfo();
    }
    public SqlInfo setSql(String sql) {
        this.sql = sql;
        return this;
    }



    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SqlInfo)) {
            return false;
        } else {
            SqlInfo other = (SqlInfo) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$sql = this.getSql();
                Object other$sql = other.getSql();
                if (this$sql == null) {
                    if (other$sql == null) {
                        return this.isOrderBy() == other.isOrderBy();
                    }
                } else if (this$sql.equals(other$sql)) {
                    return this.isOrderBy() == other.isOrderBy();
                }
                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof SqlInfo;
    }

    public int hashCode() {
        int result = 1;
        Object $sql = this.getSql();
        result = result * 59 + ($sql == null ? 43 : $sql.hashCode());
        result = result * 59 + (this.isOrderBy() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "SqlInfo(sql=" + this.getSql() + ", orderBy=" + this.isOrderBy() + ")";
    }
}
