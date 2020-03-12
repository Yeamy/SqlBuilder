package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.SQLString;
import com.yeamy.sql.statement.function.Function;

public class DateFormat extends Function {
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
	public String fun() {
		return "DATE_FORMAT";
	}

	@Override
	protected void params(StringBuilder sb) {
		super.params(sb);
		sb.append(", ");
		SQLString.appendValue(sb, format);
	}

	public static class FormatNow extends DateFormat {
		public FormatNow(String format) {
			super(null, format);
		}

		@Override
		protected void params(StringBuilder sb) {
			sb.append("NOW(), ").append(format);
		}

	}

}