package yeamy.sql.statement.date;

import yeamy.sql.statement.Column;
import yeamy.sql.statement.Searchable;

public class DateSub extends Column {
	private String expr;
	public DateType type;

	public DateSub(String name, String expr, DateType type) {
		super(name);
		this.expr = expr;
		this.type = type;
	}

	public DateSub(String table, String name, String expr, DateType type) {
		super(table, name);
		this.expr = expr;
		this.type = type;
	}

	public DateSub(Searchable<?> column, String expr, DateType type) {
		super(column);
		this.expr = expr;
		this.type = type;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("DATE_SUB(");
		super.toSQL(sb);
		sb.append(", INTERVAL ").append(expr).append(' ').append(type).append(')');
	}

}
