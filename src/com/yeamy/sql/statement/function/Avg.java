package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Avg extends AggregateColumn {

	public Avg(String name) {
		super(name);
	}

	public Avg(String table, String name) {
		super(table, name);
	}

	public Avg(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public Avg(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	@Override
	public String fun() {
		return "AVG";
	}

}
