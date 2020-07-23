package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.Column;

public class DateAdd extends Column {
	private String expr;
	public DateType type;

	public DateAdd(String name, String expr, DateType type) {
		super(name);
		this.expr = expr;
		this.type = type;
	}

	public DateAdd(String table, String name, String expr, DateType type) {
		super(table, name);
		this.expr = expr;
		this.type = type;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("DATE_ADD(");
		super.toSQL(sb);
		sb.append(", INTERVAL ").append(expr).append(' ').append(type).append(')');
	}

}
