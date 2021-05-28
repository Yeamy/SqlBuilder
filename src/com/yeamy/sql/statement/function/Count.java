package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;

public class Count extends Column {

	public Count(String name) {
		super(name);
	}

	public Count(String table, String name) {
		super(table, name);
	}

	public Count(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	public Count(Searchable column) {
		super(column);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("COUNT(");
		super.toSQL(sb);
		sb.append(')');
	}

}
