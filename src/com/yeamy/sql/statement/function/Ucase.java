package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;

public class Ucase extends Column {

	public Ucase(String name) {
		super(name);
	}

	public Ucase(String table, String name) {
		super(table, name);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("UCASE(");
		super.nameInFunction(sb);
		sb.append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

}