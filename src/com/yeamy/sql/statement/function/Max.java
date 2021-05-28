package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;

public class Max extends Column {

	public Max(String name) {
		super(name);
	}

	public Max(String table, String name) {
		super(table, name);
	}

	public Max(Searchable column) {
		super(column);
	}

	public Max(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("MAX(");
		super.toSQL(sb);
		sb.append(')');
	}

}