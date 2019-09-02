package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Select;

public abstract class FunctionColumn extends Column {

	public FunctionColumn(String name) {
		super(name);
	}

	public FunctionColumn(String table, String name) {
		super(table, name);
	}

	public FunctionColumn(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public FunctionColumn(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	public abstract String fun();

	private void functionWithParams(StringBuilder sb) {
		sb.append(fun()).append('(');
		params(sb);
		sb.append(')');
	}

	protected void params(StringBuilder sb) {
		super.toSQL(sb);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		functionWithParams(sb);
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

	@Override
	public void toSQL(StringBuilder sb) {
		if (nameAlias != null) {
			sb.append('`').append(nameAlias).append('`');
		} else {
			functionWithParams(sb);
		}
	}

}
