package com.yeamy.sql.statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import com.yeamy.sql.statement.function.Having;

/**
 * <b>warning: </b> {@link Column#table} != null
 */
public class Select implements SQLString {
	private LinkedHashSet<Object> columns = new LinkedHashSet<>();
	private LinkedList<Join> joins;
	private ArrayList<Object> groupBy;
	private Clause where;
	private Having having;
	private Sort orderBy;
	private int limitOffset = 0, limit = 0;
	private String[] from;

	public Object[] getColumns() {
		Object[] out = new Object[columns.size()];
		return columns.toArray(out);
	}

	public Select() {
	}

	public Select addColumn(String column) {
		columns.add(column);
		return this;
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

	public void from(String... from) {
		this.from = from;
	}

	private void initJoins() {
		if (joins == null) {
			joins = new LinkedList<>();
		}
	}

	public Select join(Join join) {
		initJoins();
		joins.add(join);
		return this;
	}

	public Select innerJoin(Column src, Column pattern) {
		initJoins();
		joins.add(new InnerJoin(src, pattern));
		return this;
	}

	public Select leftJoin(Column src, Column pattern) {
		initJoins();
		joins.add(new LeftJoin(src, pattern));
		return this;
	}

	public Select rightJoin(Column src, Column pattern) {
		initJoins();
		joins.add(new RightJoin(src, pattern));
		return this;
	}

	public Select fullJoin(Column src, Column pattern) {
		initJoins();
		joins.add(new FullJoin(src, pattern));
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

	public Select groupBy(String column) {
		if (groupBy == null) {
			groupBy = new ArrayList<>();
		}
		groupBy.add(column);
		return this;
	}

	public Select groupBy(String... columns) {
		if (groupBy == null) {
			groupBy = new ArrayList<>();
		}
		for (String column : columns) {
			groupBy.add(column);
		}
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

	public boolean removeGroupBy(Column column) {
		return groupBy != null && groupBy.remove(column);
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

	public Select limit(int offset, int limit) {
		this.limitOffset = offset;
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
			sql.append(" LIMIT ");
			if (limitOffset > 0) {
				sql.append(limitOffset).append(',');
			}
			sql.append(limit);
		}
	}

	private void columns(StringBuilder sql) {
		boolean f = true;
		for (Object column : columns) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			if (column instanceof Column) {
				((Column) column).nameInColumn(sql);
			} else {
				sql.append('`').append(column).append('`');
			}
		}
	}

	private void from(StringBuilder sql) {
		if (from != null) {
			boolean f = true;
			for (String table : from) {
				if (f) {
					f = false;
					sql.append(" FROM ");
				} else {
					sql.append(", `").append(table).append('`');
				}
			}
			return;
		}
		// table
		LinkedHashMap<String, Column> tables = new LinkedHashMap<>();
		for (Object li : columns) {// add all-table
			if (li instanceof Column) {
				Column column = (Column) li;
				String table = column.tableSign();
				if (table != null) {
					tables.put(table, column);
				}
			}
		}
		if (joins != null) {
			for (Join join : joins) {// add join-table
				Column src = join.column;
				tables.put(src.tableSign(), src);
			}
			for (Join join : joins) {// remove join-table
				tables.remove(join.pattern.tableSign());
			}
		}
		// from
		boolean f = true;
		for (Column table : tables.values()) {
			if (f) {
				f = false;
				sql.append(" FROM ");
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
		for (Object li : groupBy) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			if (li instanceof Column) {
				((Column) li).toSQL(sql);
			} else {
				sql.append('`').append(li).append('`');
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sql = new StringBuilder();
		toSQL(sql);
		sql.append(';');
		return sql.toString();
	}

}