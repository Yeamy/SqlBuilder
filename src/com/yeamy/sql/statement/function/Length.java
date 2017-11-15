package com.yeamy.sql.statement.function;

public class Length extends AggregateColumn {

	public Length(String name) {
		super("LENGTH", name);
	}

	public Length(String table, String name) {
		super("LENGTH", table, name);
	}

}