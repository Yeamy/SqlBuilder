package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Length extends FunctionColumn {

	public Length(String name) {
		super(name);
	}

	public Length(String table, String name) {
		super(table, name);
	}

	public Length(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public Length(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	@Override
	public String fun() {
		return "LENGTH";
	}

}