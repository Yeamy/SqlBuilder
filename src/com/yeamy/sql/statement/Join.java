package com.yeamy.sql.statement;

class Join implements SQLString {
	static final String INNER_JOIN = " INNER JOIN ";
	static final String LEFT_JOIN = " LEFT JOIN ";
	static final String RIGHT_JOIN = " RIGHT JOIN ";
	static final String FULL_JOIN = " FULL JOIN ";

	private String type;
	private Column column;
	private Column pattern;

	Join(String type, Column column, Column pattern) {
		this.type = type;
		this.column = column;
		this.pattern = pattern;
	}
	
	Column getPattern() {
		return pattern;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append(type);
		pattern.tableInFrom(sb);
		sb.append(" ON ");
		column.nameInWhere(sb);
		sb.append(" = ");
		pattern.nameInWhere(sb);
	}
}
