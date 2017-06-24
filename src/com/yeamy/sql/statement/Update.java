package com.yeamy.sql.statement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Update implements SQLString {
	private final String table;
	private final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
	private Clause where;

	public Update(String table) {
		this.table = table;
	}

	public Update add(String column, Object value) {
		map.put(column, value);
		return this;
	}

	public Update increase(String column, int number) {
		map.put(column, new Modify(column, '+', number));
		return this;
	}

	public Update reduce(String column, int number) {
		number = number < 0 ? -number : number;
		map.put(column, new Modify(column, '-', number));
		return this;
	}

	private class Modify implements SQLString {
		private String column;
		private int number;
		private char sign;

		public Modify(String column, char sign, int number) {
			this.column = column;
			this.sign = sign;
			this.number = number;
		}

		@Override
		public void toSQL(StringBuilder sql) {
			sql.append('`').append(column).append('`').append(' ').append(sign).append(' ').append(number);
		}
	}

	public Update addAll(Map<String, Object> cv) {
		map.putAll(cv);
		return this;
	}

	public Update where(Clause where) {
		this.where = where;
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		sql.append("UPDATE `").append(table).append("` SET ");
		boolean f = true;
		for (Entry<String, Object> li : map.entrySet()) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			sql.append('`').append(li.getKey()).append("` = ");
			Object value = li.getValue();
			if (value instanceof SQLString) {
				((SQLString) value).toSQL(sql);
			} else {
				SQLString.appendValue(sql, li.getValue());
			}
		}
		sql.append(" WHERE ");
		where.toSQL(sql);
		sql.append(';');
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toSQL(sb);
		return sb.toString();
	}
}
