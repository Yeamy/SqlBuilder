package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Round extends FunctionColumn {
	public int decimals;

	public Round(String name, int decimals) {
		super(name);
		this.decimals = decimals;
	}

	public Round(String table, String name, int decimals) {
		super(table, name);
		this.decimals = decimals;
	}

	public Round(Select select, String tableAlias, String name, int decimals) {
		super(select, tableAlias, name);
		this.decimals = decimals;
	}

	public Round(Union union, String tableAlias, String name, int decimals) {
		super(union, tableAlias, name);
		this.decimals = decimals;
	}

	@Override
	public void nameInFunction(StringBuilder sb) {
		super.rawName(sb);
		sb.append(", ").append(decimals);
	}

	@Override
	public String fun() {
		return "ROUND";
	}

}