package com.yeamy.sql.statement.function;

public class Avg extends AggregateColumn {

	public Avg(String name) {
		super("AVG", name);
	}

	public Avg(String table, String name) {
		super("AVG", table, name);
	}

}
