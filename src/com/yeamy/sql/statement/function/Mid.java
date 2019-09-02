package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Mid extends FunctionColumn {
	private int start, length;

	public Mid(String name, int start, int length) {
		super(name);
		this.start = start;
		this.length = length;
	}

	public Mid(String table, String name, int start, int length) {
		super(table, name);
		this.start = start;
		this.length = length;
	}

	public Mid(Select select, String tableAlias, String name, int start, int length) {
		super(select, tableAlias, name);
		this.start = start;
		this.length = length;
	}

	public Mid(Union union, String tableAlias, String name, int start, int length) {
		super(union, tableAlias, name);
		this.start = start;
		this.length = length;
	}

	@Override
	protected void params(StringBuilder sb) {
		super.toSQL(sb);
		sb.append(',').append(start).append(',').append(length);
	}

	@Override
	public String fun() {
		return "MID";
	}

}