package com.yeamy.sql.statement;

import java.util.Map;

public class SQL {

	public static String insert(String table, Select select) {
		StringBuilder sql = new StringBuilder("INSERT INTO `").append(table).append('`');
		Column[] columns = select.getColumns();
		if (columns.length == 1 && "*".equals(columns[0].name)) {
			sql.append(' ');
		} else {
			sql.append('(');
			boolean f = true;
			for (Column column : columns) {
				if (f) {
					f = false;
				} else {
					sql.append(", ");
				}
				String name = column.nameAlias != null ? column.nameAlias : column.name;
				sql.append('`').append(name).append('`');
			}
			sql.append(") ");
		}
		select.toSQL(sql);
		return sql.toString();
	}

	/**
	 * @return delete-sql
	 */
	public static String delete(String table, Clause where) {
		StringBuilder sql = new StringBuilder("DELETE FROM `").append(table).append('`');
		sql.append(" WHERE ");
		where.toSQL(sql);
		sql.append(';');
		return sql.toString();
	}

	public static String update(String table, Map<String, Object> map, Clause where) {
		return new Update(table).addAll(map).where(where).toString();
	}

	/**
	 * select all from table limt x
	 *
	 * @see {@link Select}
	 */
	public static String select(String table, Clause where, int limit) {
		StringBuilder sql = new StringBuilder("SELECT * FROM `").append(table).append('`');
		sql.append(" WHERE ");
		where.toSQL(sql);
		sql.append(" LIMIT ").append(limit);
		sql.append(';');
		return sql.toString();
	}

	public static String select(String table, Clause where, int limitOffset, int limit) {
		StringBuilder sql = new StringBuilder("SELECT * FROM `").append(table).append('`');
		sql.append(" WHERE ");
		where.toSQL(sql);
		sql.append(" LIMIT ").append(limitOffset).append(',').append(limit);
		sql.append(';');
		return sql.toString();
	}

	public static String select(String table, Clause where) {
		StringBuilder sql = new StringBuilder("SELECT * FROM `").append(table).append('`');
		sql.append(" WHERE ");
		where.toSQL(sql);
		sql.append(';');
		return sql.toString();
	}

}
