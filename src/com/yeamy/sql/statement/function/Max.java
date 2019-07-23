package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Max extends AggregateColumn {

	public Max(String name) {
		super(name);
	}

	public Max(String table, String name) {
		super(table, name);
	}

	public Max(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public Max(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	@Override
	public String fun() {
		return "MAX";
	}

}