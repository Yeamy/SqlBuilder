package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Searchable;

public class Count extends Function {

	public Count(String name) {
		super(name);
	}

	public Count(String table, String name) {
		super(table, name);
	}

	public Count(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public String fun() {
		return "COUNT";
	}

}
