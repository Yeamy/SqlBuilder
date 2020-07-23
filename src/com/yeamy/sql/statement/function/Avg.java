package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;

public class Avg extends Column {

	public Avg(String name) {
		super(name);
	}

	public Avg(String table, String name) {
		super(table, name);
	}

	public Avg(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("AVG(");
		super.toSQL(sb);
		sb.append(')');
	}

}
