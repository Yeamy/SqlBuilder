package com.yeamy.sql.statement;

public class Column implements SQLString {
	public static final String ALL = "*";
	public final String table;
	public final String name;
	public String tableAlias;
	public String nameAlias;

	public Column(String name) {
		this.table = null;
		this.name = name;
	}

	public Column(String table, String name) {
		this.table = table;
		this.name = name;
	}

	public Column as(String tableAlias, String nameAlias) {
		this.tableAlias = tableAlias;
		this.nameAlias = nameAlias;
		return this;
	}

	public Column as(String nameAlias) {
		this.nameAlias = nameAlias;
		return this;
	}

	/**
	 * table as tableAlias
	 */
	public void tableInFrom(StringBuilder sb) {
		sb.append('`').append(table).append('`');
		if (tableAlias != null) {
			sb.append(" AS `").append(tableAlias).append('`');
		}
	}

	/**
	 * table.name
	 */
	public void nameInFunction(StringBuilder sb) {
		if (name == null) {
			return;
		}
		if (tableAlias != null) {
			sb.append('`').append(tableAlias).append('`').append('.');
		} else if (table != null) {
			sb.append('`').append(table).append('`').append('.');
		}
		if ("*".equals(name)) {
			sb.append('*');
		} else {
			sb.append('`').append(name).append('`');
		}
	}

	/**
	 * tableAlias.name AS alias -> table.name AS alias
	 */
	public void nameInColumn(StringBuilder sb) {
		if (name == null) {
			return;
		}
		if (tableAlias != null) {
			sb.append('`').append(tableAlias).append('`').append('.');
		} else if (table != null) {
			sb.append('`').append(table).append('`').append('.');
		}
		if ("*".equals(name)) {
			sb.append('*');
		} else {
			sb.append('`').append(name).append('`');
			if (nameAlias != null) {
				sb.append(" AS `").append(nameAlias).append('`');
			}
		}
	}

	/**
	 * columnAlias -> tableAlias.name -> table.name
	 */
	public void nameInWhere(StringBuilder sb) {
		if (name == null) {
			return;
		}
		if (nameAlias != null) {
			sb.append('`').append(nameAlias).append('`');
			return;
		}
		if (tableAlias != null) {
			sb.append('`').append(tableAlias).append('`').append('.');
		} else if (table != null) {
			sb.append('`').append(table).append('`').append('.');
		}
		if ("*".equals(name)) {
			sb.append('*');
		} else {
			sb.append('`').append(name).append('`');
		}
	}

	@Override
	public void toSQL(StringBuilder sb) {
		nameInWhere(sb);
	}

}