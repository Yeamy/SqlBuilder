package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Searchable;

public class Min extends Function {

	public Min(String name) {
		super(name);
	}

	public Min(String table, String name) {
		super(table, name);
	}

	public Min(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public String fun() {
		return "MIN";
	}
}