package com.yeamy.sql.statement;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;

public interface SQLString {

	void toSQL(StringBuilder sb);

	static SQLString asValue(Object v) {
		return (sb) -> {
			sb.append(v);
		};
	}

	static String toValue(Object value) {
		StringBuilder sb = new StringBuilder();
		appendValue(sb, value);
		return sb.toString();
	}

	static void appendDatabase(StringBuilder sb, String database) {
		sb.append('`').append(database).append('`');
	}

	static void appendTable(StringBuilder sb, String table) {
		sb.append('`').append(table).append('`');
	}

	static void appendColumn(StringBuilder sb, String column) {
		if (Column.ALL.equals(column)) {
			sb.append("*");
		} else {
			sb.append('`').append(column).append('`');
		}
	}

	/**
	 * 检查数据安全，添加转义符
	 */
	static void appendValue(StringBuilder sb, Object value) {
		if (value == null) {
			sb.append("NULL");
		} else if (value instanceof Number) {
			sb.append(value);
		} else if (value instanceof Searchable) {
			Searchable<?> select = (Searchable<?>) value;
			sb.append('(');
			select.toSQL(sb);
			sb.append(')');
		} else if (value instanceof SQLString) {
			((SQLString) value).toSQL(sb);
		} else if (value instanceof java.sql.Date || value instanceof Time) {
			sb.append('\'');
			String out = value.toString();
			sb.append(out);
			sb.append('\'');
		} else if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = sdf.format((Date) value);
			appendValue(sb, value);
		} else if (value instanceof Iterable) {
			sb.append('(');
			for (Object i : (Iterable<?>) value) {
				sb.append(i).append(',');
			}
			sb.append(')');
		} else if (value.getClass().isArray()) {
			sb.append('(');
			if (value instanceof int[]) {
				for (int i : (int[]) value) {
					sb.append(i).append(',');
				}
			} else if (value instanceof Object[]) {
				for (Object i : (Object[]) value) {
					sb.append(i).append(',');
				}
			} else if (value instanceof long[]) {
				for (long i : (long[]) value) {
					sb.append(i).append(',');
				}
			} else if (value instanceof float[]) {
				for (float i : (float[]) value) {
					sb.append(i).append(',');
				}
			} else if (value instanceof double[]) {
				for (double i : (double[]) value) {
					sb.append(i).append(',');
				}
			} else if (value instanceof boolean[]) {
				for (boolean i : (boolean[]) value) {
					sb.append(i).append(',');
				}
			} else if (value instanceof short[]) {
				for (short i : (short[]) value) {
					sb.append(i).append(',');
				}
			} else if (value instanceof char[]) {
				for (char i : (char[]) value) {
					sb.append(i).append(',');
				}
			} else if (value instanceof byte[]) {
				for (byte i : (byte[]) value) {
					sb.append(i).append(',');
				}
			}
			sb.append(')');
		} else {
			// as String
			sb.append('\'');
			String out = value.toString().replace("'", "\\'");
			sb.append(out);
			sb.append('\'');
		}
	}
}