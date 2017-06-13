package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;

public class Format extends Column {
	public String format;

	public Format(String name, String format) {
		super(name);
		this.format = format;
	}

	public Format(String table, String name, String format) {
		super(table, name);
		this.format = format;
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("FORMAT(");
		nameInFunction(sb);
		sb.append(", ").append(format).append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

	public static class FormatNow extends Format {
		public FormatNow(String format) {
			super(null, format);
		}

		@Override
		public void nameInFunction(StringBuilder sb) {
			sb.append("NOW()");
		}

	}

}