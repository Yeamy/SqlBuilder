package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;
import com.yeamy.sql.statement.function.Function;

@Target(DataBase.MySQL)
public class DateSub extends Function {
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

	@Override
	public String fun() {
		return "DATE_SUB";
	}

	@Override
	protected void params(StringBuilder sb) {
		super.params(sb);
		sb.append(", INTERVAL ").append(expr).append(' ').append(type);
	}

}
