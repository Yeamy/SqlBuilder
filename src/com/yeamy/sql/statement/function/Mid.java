package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;

public class Mid extends Column {
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

	public Mid(Searchable<?> column, int start, int length) {
		super(column);
		this.start = start;
		this.length = length;
	}

	public Mid(Searchable<?> table, String tableAlias, String name, int start, int length) {
		super(table, tableAlias, name);
		this.start = start;
		this.length = length;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("MID(");
		super.toSQL(sb);
		sb.append(',').append(start).append(',').append(length).append(')');
	}
}