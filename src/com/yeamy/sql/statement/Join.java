package com.yeamy.sql.statement;

abstract class Join implements SQLString {
	private MultiClause clause;

	final String type;
	final Column column;
	final Column pattern;

	Join(String type, Column column, Column pattern) {
		this.type = type;
		this.column = column;
		this.pattern = pattern;
		clause = new MultiClause(Clause.equal(column, pattern));
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append(type);
		pattern.tableInFrom(sb);
		sb.append(" ON ");
		clause.toSQL(sb);
	}

	public Join and(Clause clause) {
		this.clause.and(clause);
		return this;
	}
}