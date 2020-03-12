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

	public static void appendTable(StringBuilder sb, String table) {
		sb.append('`').append(table).append('`');
	}

	public static void appendColumn(StringBuilder sb, String column) {
		sb.append('`').append(column).append('`');
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			value = sdf.format((Date) value);
			appendValue(sb, value);
		} else {
			// as String
			String out = value.toString();
			sb.append('\'');
			// replace all '
			int l = out.length();
			int start = 0;
			int end;
			while (true) {
				end = out.indexOf('\'', start);
				if (end == -1) {
					sb.append(out, start, l);
					break;
				}
				end += 1;
				sb.append(out, start, end);
				sb.append('\'');
				start = end;
				if (start >= l) {
					break;
				}
			}
			sb.append('\'');
		}
	}
}