package com.yeamy.sql.statement.datatype;

import com.yeamy.sql.statement.SQLString;

public class DataType implements SQLString {
	public static final Object NO_DEFAULT = "NO_DEFAULT";
	public static final Object CURRENT_TIMESTAMP = new SQLString() {

		@Override
		public void toSQL(StringBuilder sb) {
			sb.append("CURRENT_TIMESTAMP");
		}

		@Override
		public String toString() {
			return "CURRENT_TIMESTAMP";
		}
	};

	private String type;
	private int size;
	private boolean primary, increment, notNull;
	private Object _default = NO_DEFAULT;

	public DataType(String type, int size) {
		this.type = type;
		this.size = size;
	}

	public DataType(String type) {
		this(type, -1);
	}

	public DataType primaryKey() {
		primary = true;
		return this;
	}

	public DataType autoIncrement() {
		increment = true;
		return this;
	}

	public DataType notNull() {
		notNull = true;
		return this;
	}

	public void setDefault(Object _default) {
		this._default = _default;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		sql.append(type);
		if (size > -1) {
			sql.append('(').append(size).append(')');
		}
		if (primary) {
			sql.append(" PRIMARY KEY");
		}
		if (increment) {// mysql
			sql.append(" AUTO_INCREMENT");
		}
		if (notNull) {
			sql.append(" NOT NULL");
		}
		if (_default != NO_DEFAULT) {// mysql
			sql.append(" DEFAULT ");
			SQLString.appendValue(sql, _default);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toSQL(sb);
		return sb.toString();
	}
}