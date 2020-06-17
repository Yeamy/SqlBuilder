package com.yeamy.sql.statement.columninfo;

public class TimeStamp extends MySQLTime {

	@Override
	protected void dataType(StringBuilder sql) {
		sql.append("timestamp");
	}

}
