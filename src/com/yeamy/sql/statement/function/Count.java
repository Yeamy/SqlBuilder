package com.yeamy.sql.statement.function;

public class Count extends AggregateColumn {

	public Count(String name) {
		super("COUNT", name);
	}

	public Count(String table, String name) {
		super("COUNT", table, name);
	}

}
