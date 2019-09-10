package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Searchable;

public class Sum extends Function {

	public Sum(String name) {
		super(name);
	}

	public Sum(String table, String name) {
		super(table, name);
	}

	public Sum(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public String fun() {
		return "SUM";
	}

}