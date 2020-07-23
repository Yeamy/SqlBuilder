package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.Column;

public class Extract extends Column {
	public DateType type;

	public Extract(String name, DateType type) {
		super(name);
		this.type = type;
	}

	public Extract(String table, String name, DateType type) {
		super(table, name);
		this.type = type;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("EXTRACT(").append(type).append(" FROM ");
		super.toSQL(sb);
		sb.append(')');
	}

}
