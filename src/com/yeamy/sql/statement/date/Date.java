package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.AbsColumn;
import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.SQLString;
import com.yeamy.sql.statement.Searchable;

public class Date extends Column {
	public AbsColumn<?> column;

	/**
	 * @param format <link>https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-format</link>
	 */
	public Date(String name) {
		super(name);
	}

	public Date(String table, String name) {
		super(table, name);
	}

	public Date(Searchable<?> column) {
		super(column);
	}

	public Date(AbsColumn<?> column) {
		super((String) null);
		this.column = column;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("DATE(");
		if (column != null) {
			column.toSQL(sb);
		} else {
			super.toSQL(sb);
		}
		sb.append(')');
	}

	public static Date formatNow() {
		return new Date(Now.now);
	}

	public static Date formatDate(String time) {
		FormatDate d = new FormatDate(time);
		d.time = time;
		return d;
	}

	public static Date formatDate(java.util.Date time) {
		return new FormatDate(time);
	}

	private static class FormatDate extends Date {
		private Object time;

		public FormatDate(Object time) {
			super((String) null);
			this.time = time;
		}

		@Override
		public void toSQL(StringBuilder sb) {
			sb.append("DATE(");
			SQLString.appendValue(sb, time);
			sb.append(')');
		}

	}

}