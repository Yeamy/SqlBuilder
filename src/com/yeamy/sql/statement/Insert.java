package com.yeamy.sql.statement;

import java.util.LinkedHashMap;
import java.util.Map;

public class Insert implements SQLString {
	private final String table;
	private final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
	private Clause where;

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

	public Insert where(Clause where) {
		this.where = where;
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		sql.append("INSERT INTO ");
		SQLString.appendTable(sql, table);
		sql.append(" (");
		for (String column : map.keySet()) {
			sql.append(", ");
			SQLString.appendColumn(sql, column);
		}
		sql.delete(sql.length() - 2, sql.length());
		if (where == null) {
			toSqlDefault(sql);
		} else {
			toSqlWhere(sql);
		}
		sql.append(");");
	}

	private void toSqlWhere(StringBuilder sql) {
		sql.append(") SELECT ");
		for (Map.Entry<String, Object> li : map.entrySet()) {
			SQLString.appendValue(sql, li.getValue());
			sql.append(" AS ");
			SQLString.appendColumn(sql, li.getKey());
			sql.append(", ");
		}
		sql.delete(sql.length() - 2, sql.length());
		sql.append(" FROM DUAL WHERE ");
		where.toSQL(sql);
	}

	private void toSqlDefault(StringBuilder sql) {
		sql.append(") VALUES (");
		for (Object value : map.values()) {
			sql.append(", ");
			SQLString.appendValue(sql, value);
		}
		sql.delete(sql.length() - 2, sql.length());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toSQL(sb);
		return sb.toString();
	}
}
