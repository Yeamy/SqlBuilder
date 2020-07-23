package com.yeamy.sql.statement;

import java.util.Map;

public abstract class TableColumn<T extends AbsColumn<T>> extends AbsColumn<T> {

	/**
	 * table as tableAlias
	 */
	public abstract void tableInFrom(StringBuilder sb);

	public abstract void signTable(Map<Object, TableColumn> tables);

	public abstract void unSignTable(Map<Object, TableColumn> tables);
}