package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.function.Function;

public class DateAdd extends Function {
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
	public String fun() {
		return "DATE_ADD";
	}

	@Override
	protected void params(StringBuilder sb) {
		super.params(sb);
		sb.append(", INTERVAL ").append(expr).append(' ').append(type);
	}

}
