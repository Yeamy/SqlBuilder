package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Searchable;

public class Max extends Function {

	public Max(String name) {
		super(name);
	}

	public Max(String table, String name) {
		super(table, name);
	}

	public Max(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public String fun() {
		return "MAX";
	}

}