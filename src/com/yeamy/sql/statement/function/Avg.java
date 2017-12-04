package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Avg extends AggregateColumn {

	public Avg(String name) {
		super("AVG", name);
	}

	public Avg(String table, String name) {
		super("AVG", table, name);
	}

	public Avg(Select select, String tableAlias, String name) {
		super("AVG", select, tableAlias, name);
	}

}
