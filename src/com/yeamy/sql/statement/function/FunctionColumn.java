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

	public void nameInFunction(StringBuilder sb) {
		super.toSQL(sb);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append(fun()).append('(');
		nameInFunction(sb);
		sb.append(')');
	}

}
