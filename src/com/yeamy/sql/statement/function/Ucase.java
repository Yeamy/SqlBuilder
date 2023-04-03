package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;

public class Ucase extends Column {

	public Ucase(String name) {
		super(name);
	}

	public Ucase(String table, String name) {
		super(table, name);
	}

	public Ucase(Searchable<?> column) {
		super(column);
	}

	public Ucase(Searchable<?> table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("UCASE(");
		super.toSQL(sb);
		sb.append(')');
	}

}