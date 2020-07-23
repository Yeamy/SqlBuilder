package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;

public class Min extends Column {

	public Min(String name) {
		super(name);
	}

	public Min(String table, String name) {
		super(table, name);
	}

	public Min(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("MIN(");
		super.toSQL(sb);
		sb.append(')');
	}
}