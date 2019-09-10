package com.yeamy.sql.statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

public class Select extends Searchable {
	private LinkedHashSet<Object> columns = new LinkedHashSet<>();
	private LinkedList<Join> joins;
	private ArrayList<Object> groupBy;
	private Clause where;
	private Having having;
	private String[] from;

	Object[] getColumns() {
		Object[] out = new Object[columns.size()];
		return columns.toArray(out);
	}

	public Select() {
	}

	public Select add(String column) {
		columns.add(column);
		return this;
	}

	public Select add(String table, String name) {
		columns.add(new Column(table, name));
		return this;
	}

	public Select add(AbsColumn column) {
		columns.add(column);
		return this;
	}

	public Select addAll(Collection<AbsColumn> columns) {
		this.columns.addAll(columns);
		return this;
	}

	public Select addAll(AbsColumn... column) {
		Collections.addAll(columns, column);
		return this;
	}

	public Select addAll(String... column) {
		Collections.addAll(columns, column);
		return this;
	}

	public Select from(String... from) {
		this.from = from;
		return this;
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

	public Clause getWhere() {
		return where;
	}

	public Select groupBy(String column) {
		if (column != null) {
			if (groupBy == null) {
				groupBy = new ArrayList<>();
			}
			groupBy.add(column);
		}
		return this;
	}

	public Select groupBy(String... columns) {
		if (columns != null) {
			if (groupBy == null) {
				groupBy = new ArrayList<>();
			}
			for (String column : columns) {
				groupBy.add(column);
			}
		}
		return this;
	}

	public Select groupBy(AbsColumn column) {
		if (column != null) {
			if (groupBy == null) {
				groupBy = new ArrayList<>();
			}
			groupBy.add(column);
		}
		return this;
	}

	public Select groupBy(AbsColumn... columns) {
		if (columns != null) {
			if (groupBy == null) {
				groupBy = new ArrayList<>();
			}
			Collections.addAll(groupBy, columns);
		}
		return this;
	}

	public Select groupBy(Collection<AbsColumn> columns) {
		if (columns != null) {
			if (groupBy == null) {
				groupBy = new ArrayList<>();
			}
			groupBy.addAll(columns);
		}
		return this;
	}

	public boolean removeGroupBy(String column) {
		return groupBy != null && groupBy.remove(column);
	}

	public boolean removeGroupBy(AbsColumn column) {
		return groupBy != null && groupBy.remove(column);
	}

	public void having(Having having) {
		this.having = having;
		addAll(having.getColumn());
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
		sort(sql);
		// limit
		limit(sql);
	}

	private void columns(StringBuilder sql) {
		boolean f = true;
		for (Object column : columns) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			if (column instanceof AbsColumn) {
				((AbsColumn) column).nameInColumn(sql);
			} else {
				SQLString.appendColumn(sql, column.toString());
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
					sql.append(", ");
				}
				SQLString.appendTable(sql, table);
			}
			return;
		}
		// table
		Map<Object, TableColumn> tables = new LinkedHashMap<>();
		for (Object li : columns) {// add all-table
			if (li instanceof TableColumn) {
				TableColumn column = (TableColumn) li;
				column.signTable(tables);
			}
		}
		if (joins != null) {
			for (Join join : joins) {// add join-table
				join.column.signTable(tables);
			}
			for (Join join : joins) {// remove join-table
				join.pattern.unSignTable(tables);
			}
		}
		// from
		boolean f = true;
		for (TableColumn table : tables.values()) {
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
			if (li instanceof AbsColumn) {
				((AbsColumn) li).shortName(sql);
			} else {
				SQLString.appendColumn(sql, li.toString());
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

	@Override
	public Union union(Select select) {
		return new Union(this).union(select);
	}

	@Override
	public Union union(String select) {
		return new Union(this).union(select);
	}

	@Override
	public Union unionAll(Select select) {
		return new Union(this).unionAll(select);
	}

	@Override
	public Union unionAll(String select) {
		return new Union(this).unionAll(select);
	}
}