package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Min extends AggregateColumn {

	public Min(String name) {
		super("MIN", name);
	}

	public Min(String table, String name) {
		super("MIN", table, name);
	}

	public Min(Select select, String tableAlias, String name) {
		super("MIN", select, tableAlias, name);
	}

}