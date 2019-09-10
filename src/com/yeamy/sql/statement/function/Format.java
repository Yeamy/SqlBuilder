package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.SQLString;
import com.yeamy.sql.statement.Searchable;

public class Format extends Function {
	public String format;

	public Format(String name, String format) {
		super(name);
		this.format = format;
	}

	public Format(String table, String name, String format) {
		super(table, name);
		this.format = format;
	}

	public Format(Searchable table, String tableAlias, String name, String format) {
		super(table, tableAlias, name);
		this.format = format;
	}

	@Override
	protected void params(StringBuilder sb) {
		super.params(sb);
		sb.append(", ");
		SQLString.appendValue(sb, format);
	}

	@Override
	public String fun() {
		return "FORMAT";
	}

}