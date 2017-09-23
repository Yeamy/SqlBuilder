package com.yeamy.sql.statement.function;

import java.util.ArrayList;

import com.yeamy.sql.statement.SQLString;
import com.yeamy.sql.statement.Select;
import com.yeamy.sql.statement.Sort;

public class Union implements SQLString {
	private Sort orderBy;
	private int limitOffset = 0, limit = 0;

	private class UnionLi {
		Object select;
		String logic;

		private UnionLi(Object select, String logic) {
			this.select = select;
			this.logic = logic;
		}
	}

	private ArrayList<UnionLi> list = new ArrayList<>();

	public Union(String select) {
		list.add(new UnionLi(select, null));
	}

	public Union(Select select) {
		list.add(new UnionLi(select, null));
	}

	public Union union(String select) {
		list.add(new UnionLi(select, " UNION "));
		return this;
	}

	public Union union(Select select) {
		list.add(new UnionLi(select, " UNION "));
		return this;
	}

	public Union unionAll(Select select) {
		list.add(new UnionLi(select, " UNION ALL "));
		return this;
	}

	public Union unionAll(String select) {
		list.add(new UnionLi(select, " UNION ALL "));
		return this;
	}

	/**
	 * @see {@link #Sort}
	 * @see {@link #Asc}
	 * @see {@link #Desc}
	 */
	public Union orderBy(Sort orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public Union limit(int limit) {
		this.limit = limit;
		return this;
	}

	public Union limit(int offset, int limit) {
		this.limitOffset = offset;
		this.limit = limit;
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		for (UnionLi li : list) {
			if (li.logic != null) {
				sql.append(li.logic);
			}
			if (li.select instanceof SQLString) {
				sql.append('(');
				((SQLString) li.select).toSQL(sql);
				sql.append(')');
			} else {
				sql.append('(').append(li.select).append(')');
			}
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

	@Override
	public String toString() {
		StringBuilder sql = new StringBuilder();
		toSQL(sql);
		return sql.toString();
	}

}
