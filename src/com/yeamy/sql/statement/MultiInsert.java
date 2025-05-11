package com.yeamy.sql.statement;

import java.util.ArrayList;

public class MultiInsert implements SQLString {
    private final String table;
    private boolean onDuplicateKeyUpdate = false;
    private String[] columns;
    private final ArrayList<Object[]> values = new ArrayList<>();

    public MultiInsert(String table) {
        this.table = table;
    }

    public MultiInsert column(String... columns) {
        this.columns = columns;
        return this;
    }

    public MultiInsert values(Object... value) {
        values.add(value);
        return this;
    }

    public MultiInsert onDuplicateKeyUpdate() {
        this.onDuplicateKeyUpdate = true;
        return this;
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public void toSQL(StringBuilder sql) {
        sql.append("INSERT INTO ");
        SQLString.appendTable(sql, table);
        sql.append(" (");
        for (String column : columns) {
            SQLString.appendColumn(sql, column);
            sql.append(", ");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(") VALUES ");
        for (Object[] line : values) {
            sql.append('(');
            for (Object value : line) {
                SQLString.appendValue(sql, value);
                sql.append(", ");
            }
            sql.insert(sql.length() - 2, ')');
        }
        sql.delete(sql.length() - 2, sql.length());
        if (onDuplicateKeyUpdate) {
            sql.append(") ON DUPLICATE KEY UPDATE ");
            for (String column : columns) {
                SQLString.appendColumn(sql, column);
                sql.append(" = VALUES(");
                SQLString.appendColumn(sql, column);
                sql.append("), ");
            }
            sql.delete(sql.length() - 2, sql.length());
        }
        sql.append(");");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toSQL(sb);
        return sb.toString();
    }
}
