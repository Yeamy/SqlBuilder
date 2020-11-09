package com.yeamy.sql.statement;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface SQLString {

	void toSQL(StringBuilder sb);

	public static SQLString asValue(Object v) {
		return (sb) -> {
			sb.append(v);
		};
	}

	public static String toString(Object value) {
		StringBuilder sb = new StringBuilder();
		appendValue(sb, value);
		return sb.toString();
	}

	public static void appendDatabase(StringBuilder sb, String database) {
		sb.append('`').append(database).append('`');
	}

	public static void appendTable(StringBuilder sb, String table) {
		sb.append('`').append(table).append('`');
	}

	public static void appendColumn(StringBuilder sb, String column) {
		if (Column.ALL.equals(column)) {
			sb.append("*");
		} else {
			sb.append('`').append(column).append('`');
		}
	}

	/**
	 * 检查数据安全，添加转义符
	 */
	public static void appendValue(StringBuilder sb, Object value) {
		if (value == null) {
			sb.append("NULL");
		} else if (value instanceof Number) {
			sb.append(value);
		} else if (value instanceof Searchable) {
			Searchable select = (Searchable) value;
			sb.append('(');
			select.toSQL(sb);
			sb.append(')');
		} else if (value instanceof SQLString) {
			((SQLString) value).toSQL(sb);
		} else if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = sdf.format((Date) value);
			appendValue(sb, value);
		} else {
			// as String
			sb.append('\'');
			String out = value.toString().replace("\'", "\\\'");
			sb.append(out);
			sb.append('\'');
		}
	}
}