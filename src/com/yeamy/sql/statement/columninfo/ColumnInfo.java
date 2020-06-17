package com.yeamy.sql.statement.columninfo;

import com.yeamy.sql.statement.SQLString;

public abstract class ColumnInfo implements SQLString {
	public static final Object NO_DEFAULT = "NO_DEFAULT";

	private boolean primary;
	private boolean notNull;
	private Object _default = NO_DEFAULT;
	protected int increment;
	protected Object onUpdate;

	public boolean isPrimary() {
		return primary;
	}

	public ColumnInfo primaryKey() {
		primary = true;
		return notNull();
	}

	public ColumnInfo notNull() {
		notNull = true;
		return this;
	}

	public ColumnInfo defaultValue(Object _default) {
		this._default = _default;
		return this;
	}

	protected abstract void dataType(StringBuilder sql);

	@Override
	public void toSQL(StringBuilder sql) {
		dataType(sql);
		if (notNull) {
			sql.append(" NOT NULL");
		}
		if (increment > 0) {// mysql
			sql.append(" AUTO_INCREMENT");
			if (increment > 1) {
				sql.append(" =").append(increment);
			}
		}
		if (_default != NO_DEFAULT) {// mysql
			sql.append(" DEFAULT ");
			SQLString.appendValue(sql, _default);
		}
		if (onUpdate != null) {
			sql.append(" ON UPDATE");
			SQLString.appendValue(sql, onUpdate);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toSQL(sb);
		return sb.toString();
	}
}