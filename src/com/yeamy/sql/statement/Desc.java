package com.yeamy.sql.statement;

public class Desc extends Sort {
	public Desc(Column column) {
		super(column, DESC);
	}
}