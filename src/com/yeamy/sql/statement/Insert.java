package com.yeamy.sql.statement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Insert implements SQLString {
	private final String table;
	private final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
	private boolean ifNotExists = false;

	public Insert(String table) {
		this.table = table;
	}

	public Insert add(String column, Object value) {
		map.put(column, value);
		return this;
	}

	public Insert addAll(Map<String, Object> cv) {
		map.putAll(cv);
		return this;
	}

	public Insert ifNotExists(boolean b) {
		ifNotExists = b;
		return this;
	}

	public Insert ifNotExists() {
		return ifNotExists(true);
	}

	@Override
	public void toSQL(StringBuilder sql) {
		int l = map.size();
		String[] columns = new String[l];
		Object[] values = new Object[l];
		l = 0;
		for (Entry<String, Object> li : map.entrySet()) {
			columns[l] = li.getKey();
			values[l] = li.getValue();
			l++;
		}
		sql.append("INSERT INTO `");
		sql.append(table).append("` (");
		boolean f = true;
		for (String column : columns) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			sql.append('`').append(column).append('`');
		}
		sql.append(") VALUES (");
		f = true;
		for (Object value : values) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			SQLString.appendValue(sql, value);
		}
		if (ifNotExists) {
			sql.append(") IF NOT EXISTS;");
		} else {
			sql.append(");");
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toSQL(sb);
		return sb.toString();
	}
}
