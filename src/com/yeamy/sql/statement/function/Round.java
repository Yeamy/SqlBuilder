package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Searchable;

public class Round extends Function {
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
	protected void params(StringBuilder sb) {
		super.params(sb);
		sb.append(", ").append(decimals);
	}

	@Override
	public String fun() {
		return "ROUND";
	}

}