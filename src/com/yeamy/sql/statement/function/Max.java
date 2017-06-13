package com.yeamy.sql.statement.function;

public class Max extends AggregateColumn {

	public Max(String name) {
		super("MAX", name);
	}

	public Max(String table, String name) {
		super("MAX", table, name);
	}

}