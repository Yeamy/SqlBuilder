package com.yeamy.sql.statement.columninfo;

public class Text extends ColumnInfo {

	@Override
	protected void dataType(StringBuilder sql) {
		sql.append("text");
	}

}
