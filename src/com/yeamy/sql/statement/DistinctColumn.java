package com.yeamy.sql.statement;

import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;
import com.yeamy.sql.statement.function.Union;

@Target(DataBase.MySQL)
public class DistinctColumn extends Column {

	public DistinctColumn(String name) {
		super(name);
	}

	public DistinctColumn(String table, String name) {
		super(table, name);
	}

	public DistinctColumn(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	public DistinctColumn(Union union, String tableAlias, String name) {
		super(union, tableAlias, name);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("DISTINCT ");
		super.nameInColumn(sb);
	}
}
