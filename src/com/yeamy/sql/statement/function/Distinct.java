package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.Searchable;
import com.yeamy.sql.statement.Select;
import com.yeamy.sql.statement.Union;

public class Distinct extends Column {

	public Distinct(String name) {
		super(name);
	}

	public Distinct(String table, String name) {
		super(table, name);
	}

	public Distinct(Searchable<?> column) {
		super(column);
	}

	public Distinct(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public Distinct(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("DISTINCT ");
		super.nameInColumn(sb);
	}
}
