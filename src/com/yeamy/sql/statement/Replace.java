package com.yeamy.sql.statement;

import java.util.LinkedHashMap;
import java.util.Map;

public class Replace implements SQLString {
    private final String table;
    private final LinkedHashMap<String, Object> map = new LinkedHashMap<>();

    public Replace(String table) {
        this.table = table;
    }

    public Replace add(String column, Object value) {
        map.put(column, value);
        return this;
    }

    public Replace addAll(Map<String, Object> cv) {
        map.putAll(cv);
        return this;
    }

    @Override
    public void toSQL(StringBuilder sql) {
        sql.append("REPLACE INTO ");
        SQLString.appendTable(sql, table);
        sql.append(" (");
        for (String column : map.keySet()) {
            SQLString.appendColumn(sql, column);
            sql.append(", ");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(") VALUES (");
        for (Object value : map.values()) {
            SQLString.appendValue(sql, value);
            sql.append(", ");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(");");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toSQL(sb);
        return sb.toString();
    }
}
