package com.yeamy.sql.statement;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbsInsert implements SQLString {
    protected final String table;
    protected final LinkedHashMap<String, Object> map = new LinkedHashMap<>();

    protected AbsInsert(String table) {
        this.table = table;
    }

    public AbsInsert add(String column, Object value) {
        map.put(column, value);
        return this;
    }

    public AbsInsert addAll(Map<String, Object> cv) {
        map.putAll(cv);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toSQL(sb);
        return sb.toString();
    }
}
