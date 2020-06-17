package com.yeamy.sql.statement.columninfo;

public abstract class MySQLNumber extends ColumnInfo {

	public MySQLNumber autoIncrement() {
		return autoIncrement(1);
	}

	public MySQLNumber autoIncrement(int num) {
		increment = num;
		return this;
	}

}
