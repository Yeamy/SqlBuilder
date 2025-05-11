package com.yeamy.sql.statement;

public class Desc extends Sort {
	public Desc(AbsColumn<?> column) {
		super(column, DESC);
	}

	public Desc(String column) {
		super(column, DESC);
	}

	public Desc(String table, String column, String sort) {
		super(table, column, sort);
	}
}