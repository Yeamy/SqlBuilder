package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Searchable;

public class Length extends Function {

	public Length(String name) {
		super(name);
	}

	public Length(String table, String name) {
		super(table, name);
	}

	public Length(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public String fun() {
		return "LENGTH";
	}

}