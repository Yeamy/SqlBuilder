package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Ucase extends AggregateColumn {

	public Ucase(String name) {
		super(name);
	}

	public Ucase(String table, String name) {
		super(table, name);
	}

	public Ucase(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public Ucase(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	@Override
	public String fun() {
		return "UCASE";
	}

}