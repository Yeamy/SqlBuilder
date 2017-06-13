package com.yeamy.sql.statement.function;

public class Min extends AggregateColumn {

	public Min(String name) {
		super("MIN", name);
	}

	public Min(String table, String name) {
		super("MIN", table, name);
	}

}