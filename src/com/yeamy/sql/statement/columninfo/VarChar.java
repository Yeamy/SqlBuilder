package com.yeamy.sql.statement.columninfo;

public class VarChar extends ColumnInfo<VarChar> {
	private int size;

	public VarChar(int size) {
		this.size = size;
	}

	@Override
	protected void dataType(StringBuilder sql) {
		sql.append("varchar").append('(').append(size).append(')');
	}

}
