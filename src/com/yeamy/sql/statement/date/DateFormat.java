package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.SQLString;

public class DateFormat extends Column {
	public String format;

	/**
	 * @param format <link>https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-format</link>
	 */
	public DateFormat(String name, String format) {
		super(name);
		this.format = format;
	}

	public DateFormat(String table, String name, String format) {
		super(table, name);
		this.format = format;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("DATE_FORMAT(");
		super.toSQL(sb);
		sb.append(", ");
		SQLString.appendValue(sb, format);
		sb.append(')');
	}

	public static class FormatNow extends DateFormat {
		public FormatNow(String format) {
			super(null, format);
		}

		@Override
		public void toSQL(StringBuilder sb) {
			sb.append("DATE_FORMAT(NOW(), ");
			SQLString.appendValue(sb, format);
			sb.append(')');
		}

	}

}