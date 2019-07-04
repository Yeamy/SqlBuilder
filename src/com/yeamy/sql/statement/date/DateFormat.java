package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;

@Target(DataBase.MySQL)
public class DateFormat extends Column {
	public String format;

	/**
	 * @param format
	 *            <link>https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-format</link>
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
	public void nameInColumn(StringBuilder sb) {
		sb.append("DATE_FORMAT(");
		nameInFunction(sb);
		sb.append(", ").append(format).append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

	public static class FormatNow extends DateFormat {
		public FormatNow(String format) {
			super(null, format);
		}

		@Override
		public void nameInFunction(StringBuilder sb) {
			sb.append("NOW()");
		}
	}

}