package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Count extends AggregateColumn {

	public Count(String name) {
		super("COUNT", name);
	}

	public Count(String table, String name) {
		super("COUNT", table, name);
	}

	public Count(Select select, String tableAlias, String name) {
		super("COUNT", select, tableAlias, name);
	}

}
