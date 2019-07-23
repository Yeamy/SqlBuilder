package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Min extends AggregateColumn {

	public Min(String name) {
		super(name);
	}

	public Min(String table, String name) {
		super(table, name);
	}

	public Min(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public Min(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	@Override
	public String fun() {
		return "MIN";
	}
}