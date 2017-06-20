package com.yeamy.sql.statement;

public class LeftJoin extends Join {

	public LeftJoin(Column column, Column pattern) {
		super(" LEFT JOIN ", column, pattern);
	}

}