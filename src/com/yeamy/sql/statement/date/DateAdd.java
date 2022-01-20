package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.AbsColumn;
import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;

public class DateAdd extends Column {
	private AbsColumn column;
	private String expr;
	public DateType type;

	public DateAdd(String name, int num, DateType type) {
		this(name, String.valueOf(num), type);
	}

	public DateAdd(String table, String name, int num, DateType type) {
		this(table, name, String.valueOf(num), type);
	}

	public DateAdd(Searchable column, int num, DateType type) {
		this(column, String.valueOf(num), type);
	}

	public DateAdd(AbsColumn column, int num, DateType type) {
		this(column, String.valueOf(num), type);
	}

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

	public DateAdd(Searchable column, String expr, DateType type) {
		super(column);
		this.expr = expr;
		this.type = type;
	}

	public DateAdd(AbsColumn column, String expr, DateType type) {
		super((String) null);
		this.column = column;
		this.expr = expr;
		this.type = type;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("DATE_ADD(");
		if (column != null) {
			column.toSQL(sb);
		} else {
			super.toSQL(sb);
		}
		sb.append(", INTERVAL ").append(expr).append(' ').append(type).append(')');
	}

}
