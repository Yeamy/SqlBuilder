package yeamy.sql.statement;

import java.util.Map;

public class SQL {

	public static String insert(String table, Select select) {
		StringBuilder sql = new StringBuilder("INSERT INTO ");
		SQLString.appendTable(sql, table);
		Object[] columns = select.getColumns();
		if (columns.length == 0) {
			throw new NullPointerException("no column in select");
		} else if (columns.length > 1) {
			appendColumns(sql, columns);
		} else if ("*".equals(columns[0])) {
			sql.append(' ');
		} else if (columns[0] instanceof Column && "*".equals(((Column) columns[0]).name)) {
			sql.append(' ');
		} else {
			appendColumns(sql, columns);
		}
		select.toSQL(sql);
		return sql.toString();
	}

	private static void appendColumns(StringBuilder sql, Object[] columns) {
		sql.append('(');
		boolean f = true;
		for (Object column : columns) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			if (column instanceof String) {
				SQLString.appendColumn(sql, column.toString());
			} else {
				AbsColumn<?> c = (AbsColumn<?>) column;
				c.shortName(sql);
			}
		}
		sql.append(") ");
	}

	/**
	 * @return delete-sql
	 */
	public static String delete(String table, Clause where) {
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		SQLString.appendTable(sql, table);
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
	 * @see Select
	 */
	public static String select(String table, Clause where, int limit) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		SQLString.appendTable(sql, table);
		sql.append(" WHERE ");
		where.toSQL(sql);
		sql.append(" LIMIT ").append(limit);
		sql.append(';');
		return sql.toString();
	}

	public static String select(String table, Clause where, int limitOffset, int limit) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		SQLString.appendTable(sql, table);
		sql.append(" WHERE ");
		where.toSQL(sql);
		sql.append(" LIMIT ").append(limitOffset).append(',').append(limit);
		sql.append(';');
		return sql.toString();
	}

	public static String select(String table, Clause where) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		SQLString.appendTable(sql, table);
		sql.append(" WHERE ");
		where.toSQL(sql);
		sql.append(';');
		return sql.toString();
	}

	public static String dropTable(String table) {
		StringBuilder sql = new StringBuilder("DROP TABLE ");
		SQLString.appendTable(sql, table);
		sql.append(';');
		return sql.toString();
	}

	public static String truncateTable(String table) {
		StringBuilder sql = new StringBuilder("TRUNCATE TABLE ");
		SQLString.appendTable(sql, table);
		sql.append(';');
		return sql.toString();
	}

}
