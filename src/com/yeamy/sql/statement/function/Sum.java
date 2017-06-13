package com.yeamy.sql.statement.function;

public class Sum extends AggregateColumn {

	public Sum(String name) {
		super("SUM", name);
	}

	public Sum(String table, String name) {
		super("SUM", table, name);
	}

}