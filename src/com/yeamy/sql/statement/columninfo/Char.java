package com.yeamy.sql.statement.columninfo;

public class Char extends ColumnInfo<Char> {
	private int size;

	public Char(int size) {
		this.size = size;
	}

	@Override
	protected void dataType(StringBuilder sql) {
		sql.append("char").append('(').append(size).append(')');
	}

}
