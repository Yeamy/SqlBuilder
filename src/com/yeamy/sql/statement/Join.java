package com.yeamy.sql.statement;

public abstract class Join<T extends Join<T>> implements SQLString {
	private Clause clause;

	final String type;
	final Column column;
	final Column pattern;

	Join(String type, Column column, Column pattern) {
		this.type = type;
		this.column = column;
		this.pattern = pattern;
		clause = Clause.equal(column, pattern);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append(type);
		pattern.tableInFrom(sb);
		sb.append(" ON ");
		clause.toSQL(sb);
	}

	@SuppressWarnings("unchecked")
	public T and(Clause clause) {
		this.clause.and(clause);
		return (T) this;
	}
}