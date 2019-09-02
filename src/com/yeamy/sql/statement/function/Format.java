package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Select;

public class Format extends FunctionColumn {
	public String format;

	public Format(String name, String format) {
		super(name);
		this.format = format;
	}

	public Format(String table, String name, String format) {
		super(table, name);
		this.format = format;
	}

	public Format(Select select, String tableAlias, String name, String format) {
		super(select, tableAlias, name);
		this.format = format;
	}

	@Override
	protected void params(StringBuilder sb) {
		super.toSQL(sb);
		sb.append(", ").append(format);
	}

	@Override
	public String fun() {
		return "FORMAT";
	}

}