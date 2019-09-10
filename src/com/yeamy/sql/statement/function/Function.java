package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;

public abstract class Function extends Column {

	public Function(String name) {
		super(name);
	}

	public Function(String table, String name) {
		super(table, name);
	}

	public Function(Searchable table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	public abstract String fun();

	protected void params(StringBuilder sb) {
		super.toSQL(sb);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append(fun()).append('(');
		params(sb);
		sb.append(')');
	}

}
