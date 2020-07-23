package com.yeamy.sql.statement;

public class MultiClause extends Clause {
	private Clause clause;

	public MultiClause(Clause clause) {
		this.clause = clause;
	}

	@Override
	public void subSQL(StringBuilder sql) {
		if (clause.isMulti()) {
			sql.append('(');
			clause.toSQL(sql);
			sql.append(')');
		} else {
			clause.toSQL(sql);
		}
	}

}