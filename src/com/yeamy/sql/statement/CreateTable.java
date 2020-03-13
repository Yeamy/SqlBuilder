package com.yeamy.sql.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.yeamy.sql.statement.datatype.DataType;

public class CreateTable implements SQLString {
	private String table;
	private LinkedHashMap<String, DataType> columns = new LinkedHashMap<>();
	private ArrayList<Unique> uniques;
	private boolean ifNotExists;

	public CreateTable(String table) {
		this.table = table;
	}

	public CreateTable add(String column, DataType type) {
		columns.put(column, type);
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
		SQLString.appendTable(sql, table);
		sql.append('(');
		// columns
		boolean first = true;
		for (Entry<String, DataType> entry : columns.entrySet()) {
			if (first) {
				first = false;
			} else {
				sql.append(',');
			}
			sql.append(entry.getKey()).append(' ');
			entry.getValue().toSQL(sql);
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
