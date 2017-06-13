package com.yeamy.sql.statement;

import static com.yeamy.sql.statement.Join.FULL_JOIN;
import static com.yeamy.sql.statement.Join.INNER_JOIN;
import static com.yeamy.sql.statement.Join.LEFT_JOIN;
import static com.yeamy.sql.statement.Join.RIGHT_JOIN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import com.yeamy.sql.statement.function.Having;

/**
 * <b>warning: </b> {@link Column#table} != null
 */
public class Select implements SQLString {
	private LinkedHashSet<Column> columns = new LinkedHashSet<>();
	private ArrayList<Join> joins;
	private ArrayList<Column> groupBy;
	private Clause where;
	private Having having;
	private Sort orderBy;
	private int limit = 0;

	public Select() {
	}

	public Select addColumn(Column column) {
		columns.add(column);
		return this;
	}

	public Select addColumn(Collection<Column> columns) {
		this.columns.addAll(columns);
		return this;
	}

	public Select addColumn(Column... column) {
		Collections.addAll(columns, column);
		return this;
	}

	public Select innerJoin(Column src, Column pattern) {
		if (joins == null) {
			joins = new ArrayList<>();
		}
		joins.add(new Join(INNER_JOIN, src, pattern));
		return this;
	}

	public Select leftJoin(Column src, Column pattern) {
		if (joins == null) {
			joins = new ArrayList<>();
		}
		joins.add(new Join(LEFT_JOIN, src, pattern));
		return this;
	}

	public Select rightJoin(Column src, Column pattern) {
		if (joins == null) {
			joins = new ArrayList<>();
		}
		joins.add(new Join(RIGHT_JOIN, src, pattern));
		return this;
	}

	public Select fullJoin(Column src, Column pattern) {
		if (joins == null) {
			joins = new ArrayList<>();
		}
		joins.add(new Join(FULL_JOIN, src, pattern));
		return this;
	}

	/**
	 * @see {@link #Clause}
	 * @see {@link #MultiClause}
	 */
	public Select where(Clause clause) {
		this.where = clause;
		return this;
	}

	public Select groupBy(Column column) {
		if (groupBy == null) {
			groupBy = new ArrayList<>();
		}
		groupBy.add(column);
		return this;
	}

	public Select groupBy(Column... columns) {
		if (groupBy == null) {
			groupBy = new ArrayList<>();
		}
		Collections.addAll(groupBy, columns);
		return this;
	}

	public Select groupBy(Collection<Column> columns) {
		if (groupBy == null) {
			groupBy = new ArrayList<>();
		}
		groupBy.addAll(columns);
		return this;
	}

	public void having(Having having) {
		this.having = having;
		addColumn(having.getColumn());
	}

	/**
	 * @see {@link #Sort}
	 * @see {@link #Asc}
	 * @see {@link #Desc}
	 */
	public Select orderBy(Sort orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public Select limit(int limit) {
		this.limit = limit;
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		sql.append("SELECT ");
		// columns
		columns(sql);
		from(sql);
		// join on
		if (joins != null) {
			for (Join join : joins) {
				join.toSQL(sql);
			}
		}
		// where
		if (where != null) {
			sql.append(" WHERE ");
			where.toSQL(sql);
		}
		// group by
		groupBy(sql);
		// having
		if (having != null) {
			having.toSQL(sql);
		}
		// order by
		if (orderBy != null) {
			orderBy.toSQL(sql);
		}
		// limit
		if (limit > 0) {
			sql.append(" LIMIT ").append(limit);
		}
		sql.append(';');
	}

	private void columns(StringBuilder sql) {
		boolean f = true;
		for (Column column : columns) {
			if (column.name == null) {
				continue;
			}
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			column.nameInColumn(sql);
		}
	}

	private void from(StringBuilder sql) {
		// table
		LinkedHashMap<String, Column> tables = new LinkedHashMap<>();
		for (Column column : columns) {// add all-table
			String table = column.table;
			if (table != null) {
				tables.put(column.table, column);
			}
		}
		if (joins != null) {
			for (Join join : joins) {// remove join-table
				tables.remove(join.getPattern().table);
			}
		}
		// from
		sql.append(" FROM ");
		boolean f = true;
		for (Column table : tables.values()) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			table.tableInFrom(sql);
		}
	}

	private void groupBy(StringBuilder sql) {
		if (groupBy == null) {
			return;
		}
		sql.append(" GROUP BY ");
		boolean f = true;
		for (Column li : groupBy) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			li.nameInWhere(sql);
		}
	}

	@Override
	public String toString() {
		StringBuilder sql = new StringBuilder();
		toSQL(sql);
		return sql.toString();
	}

}