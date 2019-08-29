package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Sum extends FunctionColumn {

	public Sum(String name) {
		super(name);
	}

	public Sum(String table, String name) {
		super(table, name);
	}

	public Sum(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public Sum(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	@Override
	public String fun() {
		return "SUM";
	}

}