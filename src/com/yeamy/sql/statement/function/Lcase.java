package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Lcase extends AggregateColumn {

	public Lcase(String name) {
		super(name);
	}

	public Lcase(String table, String name) {
		super(table, name);
	}

	public Lcase(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public Lcase(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	@Override
	public String fun() {
		return "LCASE";
	}

}