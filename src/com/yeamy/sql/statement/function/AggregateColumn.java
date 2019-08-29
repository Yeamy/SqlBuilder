package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Select;

public abstract class AggregateColumn extends Column {

	public AggregateColumn(String name) {
		super(name);
	}

	public AggregateColumn(String table, String name) {
		super(table, name);
	}

	public AggregateColumn(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public AggregateColumn(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	public abstract String fun();

	public void nameInFunction(StringBuilder sb) {
		super.rawName(sb);
	}

	@Override
	public void rawName(StringBuilder sb) {
		sb.append(fun()).append('(');
		nameInFunction(sb);
		sb.append(')');
	}

}
