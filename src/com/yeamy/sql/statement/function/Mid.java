package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Select;

public class Mid extends Column {
	private int start, length;

	public Mid(String name, int start, int length) {
		super(name);
		this.start = start;
		this.length = length;
	}

	public Mid(String table, String name, int start, int length) {
		super(table, name);
		this.start = start;
		this.length = length;
	}

	public Mid(Select select, String name, int start, int length) {
		super(select, name);
		this.start = start;
		this.length = length;
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("MID(");
		nameInFunction(sb);
		sb.append(',').append(start).append(',').append(length).append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

	@Override
	public void nameInWhere(StringBuilder sb) {
		sb.append('`').append(nameAlias).append('`');
	}

}