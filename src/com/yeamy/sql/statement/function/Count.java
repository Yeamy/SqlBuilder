package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Count extends FunctionColumn {

	public Count(String name) {
		super(name);
	}

	public Count(String table, String name) {
		super(table, name);
	}

	public Count(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public Count(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	@Override
	public String fun() {
		return "COUNT";
	}

}
