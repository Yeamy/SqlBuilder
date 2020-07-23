package com.yeamy.sql.statement;

public class FullJoin extends Join<FullJoin> {

	public FullJoin(Column column, Column pattern) {
		super(" FULL JOIN ", column, pattern);
	}

}