package com.yeamy.sql.statement;

import java.util.Map;

public class SQL {

	public static String insert(String table, Select select, String... targetColumn) {
		StringBuilder sql = new StringBuilder("INSERT INTO `").append(table).append('`');
		boolean f = true;
		for (String column : targetColumn) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			sql.append('`').append(column).append('`');
		}
		sql.append(' ');
		select.toSQL(sql);
		return sql.toString();
	}

	public static String insert(String table, String select, String... targetColumn) {
		StringBuilder sql = new StringBuilder("INSERT INTO `").append(table).append('`');
		boolean f = true;
		for (String column : targetColumn) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			sql.append('`').append(column).append('`');
		}
		sql.append(' ');
		sql.append(select);
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
