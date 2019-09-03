package com.yeamy.sql.statement;

import java.util.Map;

public class SQL {

	public static String insert(String table, Select select) {
		StringBuilder sql = new StringBuilder("INSERT INTO `").append(table).append('`');
		Object[] columns = select.getColumns();
		Object c1 = columns[0];
		if (columns.length == 0) {
			throw new NullPointerException("no column in select");
		} else if (columns.length > 1) {
			appendInsert(sql, columns);
		} else if (c1 instanceof String && "*".equals(c1)) {
			sql.append(' ');
		} else if ("*".equals(((Column) c1).name)) {
			sql.append(' ');
		} else {
			appendInsert(sql, columns);
		}
		select.toSQL(sql);
		return sql.toString();
	}

	private static void appendInsert(StringBuilder sql, Object[] columns) {
		sql.append('(');
		boolean f = true;
		for (Object column : columns) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			String name;
			if (column instanceof String) {
				name = column.toString();
			} else {
				Column c = (Column) column;
				name = c.nameAlias != null ? c.nameAlias : c.name;
			}
			sql.append('`').append(name).append('`');
		}
		sql.append(") ");
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
