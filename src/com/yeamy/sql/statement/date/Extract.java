package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;
import com.yeamy.sql.statement.function.Function;

@Target(DataBase.MySQL)
public class Extract extends Function {
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
	public String fun() {
		return "EXTRACT";
	}

	@Override
	protected void params(StringBuilder sb) {
		sb.append(type).append(" FROM ");
		super.params(sb);
	}

}
