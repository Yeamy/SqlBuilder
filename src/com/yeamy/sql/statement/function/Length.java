package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Length extends AggregateColumn {

	public Length(String name) {
		super("LENGTH", name);
	}

	public Length(String table, String name) {
		super("LENGTH", table, name);
	}

	public Length(Select select, String tableAlias, String name) {
		super("LENGTH", select, tableAlias, name);
	}

}