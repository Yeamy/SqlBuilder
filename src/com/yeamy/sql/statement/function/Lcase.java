package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Searchable;

public class Lcase extends Function {

	public Lcase(String name) {
		super(name);
	}

	public Lcase(String table, String name) {
		super(table, name);
	}

	public Lcase(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public String fun() {
		return "LCASE";
	}

}