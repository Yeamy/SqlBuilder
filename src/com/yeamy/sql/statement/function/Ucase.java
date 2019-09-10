package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Searchable;

public class Ucase extends Function {

	public Ucase(String name) {
		super(name);
	}

	public Ucase(String table, String name) {
		super(table, name);
	}

	public Ucase(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public String fun() {
		return "UCASE";
	}

}