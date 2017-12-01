package com.yeamy.sql.statement;

public class InnerJoin extends Join {

	public InnerJoin(Column column, Column pattern) {
		super(" INNER JOIN ", column, pattern);
	}

	public InnerJoin and(Clause clause) {
		return (InnerJoin) super.and(clause);
	}

}