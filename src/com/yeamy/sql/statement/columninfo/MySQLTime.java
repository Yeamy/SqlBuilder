package com.yeamy.sql.statement.columninfo;

import com.yeamy.sql.statement.SQLString;

public abstract class MySQLTime extends ColumnInfo<MySQLTime> {
	public static final SQLString CURRENT_TIMESTAMP = new SQLString() {

		@Override
		public void toSQL(StringBuilder sb) {
			sb.append("CURRENT_TIMESTAMP");
		}

		@Override
		public String toString() {
			return "CURRENT_TIMESTAMP";
		}
	};

	public MySQLTime defaultCurrentTimestamp() {
		return defaultValue(CURRENT_TIMESTAMP);
	}

	public MySQLTime keepUpdate() {
		onUpdate = CURRENT_TIMESTAMP;
		return this;
	}

}
