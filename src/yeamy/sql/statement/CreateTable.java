package yeamy.sql.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import yeamy.sql.statement.columninfo.ColumnInfo;

public class CreateTable implements SQLString {
	private String database;
	private String table;
	private LinkedHashMap<String, ColumnInfo<?>> columns = new LinkedHashMap<>();
	private ArrayList<Unique> uniques;
	private boolean ifNotExists;

	public CreateTable(String database, String table) {
		this.database = database;
		this.table = table;
	}

	public CreateTable(String table) {
		this.table = table;
	}

	public CreateTable add(String column, ColumnInfo<?> info) {
		columns.put(column, info);
		return this;
	}

	public CreateTable uniqueWithName(String name, String... columns) {
		if (uniques == null) {
			uniques = new ArrayList<>();
		}
		this.uniques.add(new Unique(name, columns));
		return this;
	}

	public CreateTable unique(String... columns) {
		return uniqueWithName(null, columns);
	}

	public CreateTable ifNotExists() {
		this.ifNotExists = true;
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		sql.append("CREATE TABLE ");
		if (ifNotExists) {
			sql.append("IF NOT EXISTS ");
		}
		if (database != null) {
			SQLString.appendDatabase(sql, database);
			sql.append('.');
		}
		SQLString.appendTable(sql, table);
		sql.append('(');
		// columnse
		ArrayList<String> primary = new ArrayList<>();
		boolean first = true;
		for (Entry<String, ColumnInfo<?>> entry : columns.entrySet()) {
			if (first) {
				first = false;
			} else {
				sql.append(',');
			}
			String column = entry.getKey();
			ColumnInfo<?> info = entry.getValue();
			sql.append(column).append(' ');
			info.toSQL(sql);
			if (info.isPrimary()) {
				primary.add(column);
			}
		}
		if (primary.size() > 0) {
			sql.append(", PRIMARY KEY (");
			for (String column : primary) {
				SQLString.appendColumn(sql, column);
			}
			sql.append(')');
		}
		// uniques
		if (uniques != null) {
			sql.append(" UNIQUE(");
			first = true;
			for (Unique unique : uniques) {
				if (first) {
					first = false;
				} else {
					sql.append(',');
				}
				unique.toSQL(sql);
			}
		}
		sql.append(')');
	}

	@Override
	public String toString() {
		StringBuilder sql = new StringBuilder();
		toSQL(sql);
		sql.append(';');
		return sql.toString();
	}

	private static class Unique implements SQLString {
		private String name;
		private ArrayList<String> columns = new ArrayList<>();

		public Unique(String name, String... columns) {
			this.name = name;
			Collections.addAll(this.columns, columns);
		}

		@Override
		public void toSQL(StringBuilder sql) {
			if (name == null) {
				sql.append("CONSTRAINT ");
				SQLString.appendColumn(sql, name);
				sql.append(' ');
			}
			sql.append("UNIQUE(");
			boolean first = true;
			for (String column : columns) {
				if (first) {
					first = false;
				} else {
					sql.append(',');
				}
				SQLString.appendColumn(sql, column);
			}
			sql.append(')');
		}

	}

}
