package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;
import com.yeamy.sql.statement.function.FunctionColumn;

@Target(DataBase.MySQL)
public class DateFormat extends FunctionColumn {
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
		sb.append(", '").append(format).append('\'');
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