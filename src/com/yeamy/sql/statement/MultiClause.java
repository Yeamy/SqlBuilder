package com.yeamy.sql.statement;

public class MultiClause extends Clause {
	private Clause clause;

	public MultiClause(Clause clause) {
		this.clause = clause;
	}

	@Override
	public MultiClause and(Clause clause) {
		super.and(clause);
		return this;
	}

	@Override
	public MultiClause or(Clause clause) {
		super.or(clause);
		return this;
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