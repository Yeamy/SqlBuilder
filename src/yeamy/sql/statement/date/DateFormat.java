package yeamy.sql.statement.date;

import java.util.Date;

import yeamy.sql.statement.AbsColumn;
import yeamy.sql.statement.Column;
import yeamy.sql.statement.SQLString;
import yeamy.sql.statement.Searchable;

public class DateFormat extends Column {
	public String format;
	public AbsColumn<?> column;

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

	public DateFormat(Searchable<?> column, String format) {
		super(column);
		this.format = format;
	}

	public DateFormat(AbsColumn<?> column, String format) {
		super((String) null);
		this.column = column;
		this.format = format;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("DATE_FORMAT(");
		if (column != null) {
			column.toSQL(sb);
		} else {
			super.toSQL(sb);
		}
		sb.append(", ");
		SQLString.appendValue(sb, format);
		sb.append(')');
	}

	public static DateFormat formatNow(String format) {
		return new DateFormat(Now.now, format);
	}

	public static DateFormat formatDate(String time, String format) {
		DateFormatDate d = new DateFormatDate(time, format);
		d.time = time;
		return d;
	}

	public static DateFormat formatDate(Date time, String format) {
		return new DateFormatDate(time, format);
	}

	private static class DateFormatDate extends DateFormat {
		private Object time;

		public DateFormatDate(Object time, String format) {
			super((String) null, format);
			this.time = time;
		}

		@Override
		public void toSQL(StringBuilder sb) {
			sb.append("DATE_FORMAT(");
			SQLString.appendValue(sb, time);
			sb.append(", ");
			SQLString.appendValue(sb, format);
			sb.append(')');
		}

	}

}