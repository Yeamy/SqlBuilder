package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;

public class Sum extends Column {

	public Sum(String name) {
		super(name);
	}

	public Sum(String table, String name) {
		super(table, name);
	}

	public Sum(Searchable column) {
		super(column);
	}

	public Sum(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("SUM(");
		super.toSQL(sb);
		sb.append(')');
	}

}