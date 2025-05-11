package com.yeamy.sql.statement;

public class Insert extends AbsInsert {
	private boolean ignore = false;
	private boolean onDuplicateKeyUpdate = false;

	public Insert(String table) {
		super(table);
	}

	public AbsInsert onDuplicateKeyUpdate() {
		this.onDuplicateKeyUpdate = true;
		return this;
	}

	public AbsInsert ignore() {
		this.ignore = true;
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		if (ignore) {
			sql.append("INSERT IGNORE INTO ");
		} else {
			sql.append("INSERT INTO ");
		}
		SQLString.appendTable(sql, table);
		sql.append(" (");
		for (String column : map.keySet()) {
			SQLString.appendColumn(sql, column);
			sql.append(", ");
		}
		sql.delete(sql.length() - 2, sql.length());
		sql.append(") VALUES (");
		for (Object value : map.values()) {
			sql.append(", ");
			SQLString.appendValue(sql, value);
		}
		sql.delete(sql.length() - 2, sql.length());
		if (onDuplicateKeyUpdate) {
			sql.append(") ON DUPLICATE KEY UPDATE ");
			for (String column : map.keySet()) {
				SQLString.appendColumn(sql, column);
				sql.append(" = VALUES(");
				SQLString.appendColumn(sql, column);
				sql.append("), ");
			}
			sql.delete(sql.length() - 2, sql.length());
		}
		sql.append(");");
	}

}
