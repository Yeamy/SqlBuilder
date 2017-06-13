package com.yeamy.sql.statement;

public interface SQLString {

	void toSQL(StringBuilder sb);

	public static SQLString asValue(Object v) {
		return (sb) -> {
			sb.append(v);
		};
	}

	/**
	 * 检查数据安全，添加转义符
	 */
	public static void appendValue(StringBuilder sb, Object value) {
		if (value instanceof Number) {
			sb.append(value);
			return;
		} else if (value instanceof SQLString) {
			((SQLString) value).toSQL(sb);
			return;
		}
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