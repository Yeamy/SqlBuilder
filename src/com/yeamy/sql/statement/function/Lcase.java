package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;

public class Lcase extends Column {

	public Lcase(String name) {
		super(name);
	}

	public Lcase(String table, String name) {
		super(table, name);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("LCASE(");
		nameInFunction(sb);
		sb.append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

}