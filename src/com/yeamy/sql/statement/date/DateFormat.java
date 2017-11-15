package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.Column;

public class DateFormat extends Column {
	public String format;

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