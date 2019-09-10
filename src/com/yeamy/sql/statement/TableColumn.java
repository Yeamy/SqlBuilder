package com.yeamy.sql.statement;

import java.util.Map;

public abstract class TableColumn extends AbsColumn {
	public String tableAlias;

	public TableColumn as(String tableAlias, String nameAlias) {
		this.tableAlias = tableAlias;
		this.nameAlias = nameAlias;
		return this;
	}

	public TableColumn tableAs(String tableAlias) {
		this.tableAlias = tableAlias;
		return this;
	}

	/**
	 * table as tableAlias
	 */
	public abstract void tableInFrom(StringBuilder sb);

	public abstract void signTable(Map<Object, TableColumn> tables);

	public abstract void unSignTable(Map<Object, TableColumn> tables);
}