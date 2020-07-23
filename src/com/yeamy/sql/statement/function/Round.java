package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;

public class Round extends Column {
	public int decimals;

	public Round(String name, int decimals) {
		super(name);
		this.decimals = decimals;
	}

	public Round(String table, String name, int decimals) {
		super(table, name);
		this.decimals = decimals;
	}

	public Round(Searchable table, String tableAlias, String name, int decimals) {
		super(table, tableAlias, name);
		this.decimals = decimals;
	}


	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("ROUND(");
		super.toSQL(sb);
		sb.append(", ").append(decimals);
		sb.append(')');
	}

}