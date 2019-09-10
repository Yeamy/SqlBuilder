package com.yeamy.sql.statement;

public abstract class AbsColumn implements SQLString {
	protected String nameAlias;

	public AbsColumn as(String nameAlias) {
		this.nameAlias = nameAlias;
		return this;
	}

	/**
	 * tableAlias.name AS alias -> table.name AS alias
	 */
	public void nameInColumn(StringBuilder sql) {
		toSQL(sql);
		if (nameAlias != null) {
			sql.append(" AS ");
			SQLString.appendColumn(sql, nameAlias);
		}
	}

	/**
	 * group by / order by
	 */
	public void shortName(StringBuilder sql) {
		if (nameAlias != null) {
			SQLString.appendColumn(sql, nameAlias);
		} else {
			toSQL(sql);
		}
	}

}