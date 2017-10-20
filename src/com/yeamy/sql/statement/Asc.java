package com.yeamy.sql.statement;

public class Asc extends Sort {
	public Asc(Column column) {
		super(column, ASC);
	}

	public Asc(String column) {
		super(new Column(column), ASC);
	}
}