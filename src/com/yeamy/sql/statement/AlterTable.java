package com.yeamy.sql.statement;

import java.util.ArrayList;

import com.yeamy.sql.statement.columninfo.ColumnInfo;

public class AlterTable implements SQLString {
	private String table;
	private ArrayList<SQLString> columns = new ArrayList<>();

	public AlterTable(String table) {
		this.table = table;
	}

	// column

	public AlterTable add(String column, ColumnInfo<?> dataType) {
		this.columns.add(new SQLString() {

			@Override
			public void toSQL(StringBuilder sql) {
				sql.append("ADD ");
				SQLString.appendColumn(sql, column);
				sql.append(' ');
				dataType.toSQL(sql);
			}
		});
		return this;
	}

	public AlterTable drop(String column) {
		columns.add(new SQLString() {

			@Override
			public void toSQL(StringBuilder sql) {
				sql.append("DROP COLUMN ");
				SQLString.appendColumn(sql, column);
			}
		});
		return this;
	}

	public AlterTable modify(String column, ColumnInfo<?> dataType) {
		this.columns.add(new SQLString() {

			@Override
			public void toSQL(StringBuilder sql) {
				sql.append("MODIFY COLUMN ");
				SQLString.appendColumn(sql, column);
				sql.append(' ');
				dataType.toSQL(sql);
			}
		});
		return this;
	}

	// unique

	public AlterTable addUniqueWithName(String name, String... columns) {
		this.columns.add(new SQLString() {

			@Override
			public void toSQL(StringBuilder sql) {
				sql.append("ADD");
				if (name != null) {
					sql.append(" CONSTRAINT ");
					SQLString.appendColumn(sql, name);
				}
				sql.append(" UNIQUE(");
				boolean first = true;
				for (String li : columns) {
					if (first) {
						first = false;
					} else {
						sql.append(',');
					}
					SQLString.appendColumn(sql, li);
				}
			}
		});
		return this;
	}

	public AlterTable addUnique(String... columns) {
		return addUniqueWithName(null, columns);
	}

	public AlterTable dropUnique(String column) {
		columns.add(new SQLString() {

			@Override
			public void toSQL(StringBuilder sql) {
				sql.append("DROP INDEX ");
				SQLString.appendColumn(sql, column);
			}
		});
		return this;
	}

	// default

	public AlterTable setDefault(String column, Object _default) {
		columns.add(new SQLString() {

			@Override
			public void toSQL(StringBuilder sql) {
				sql.append("ALTER ");
				SQLString.appendColumn(sql, column);
				sql.append(" SET DEFAULT ");
				SQLString.appendValue(sql, _default);
			}
		});
		return this;
	}

	public AlterTable dropDefault(String column) {
		columns.add(new SQLString() {

			@Override
			public void toSQL(StringBuilder sql) {
				sql.append("ALTER ");
				SQLString.appendColumn(sql, column);
				sql.append(" DROP DEFAULT");
			}
		});
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		sql.append("ALTER TABLE ");
		SQLString.appendTable(sql, table);
		sql.append(' ');
		boolean first = true;
		for (SQLString li : columns) {
			if (first) {
				first = false;
			} else {
				sql.append(',');
			}
			li.toSQL(sql);
		}
	}

	@Override
	public String toString() {
		StringBuilder sql = new StringBuilder();
		toSQL(sql);
		sql.append(';');
		return sql.toString();
	}

}
