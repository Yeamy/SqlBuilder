package com.yeamy.sql.statement;

public abstract class Searchable implements SQLString {
	private Sort orderBy;
	private int limitOffset = -1, limit = 0;

	/**
	 * @see {@link #Sort}
	 * @see {@link #Asc}
	 * @see {@link #Desc}
	 */
	public Searchable orderBy(Sort orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public Searchable limit(int limit) {
		this.limit = limit;
		return this;
	}

	public Searchable limit(int offset, int limit) {
		this.limitOffset = offset;
		this.limit = limit;
		return this;
	}

	// order by
	protected void sort(StringBuilder sql) {
		if (orderBy != null) {
			orderBy.toSQL(sql);
		}
	}

	// limit
	protected void limit(StringBuilder sql) {
		if (limit > 0) {
			sql.append(" LIMIT ");
			if (limitOffset >= 0) {
				sql.append(limitOffset).append(',');
			}
			sql.append(limit);
		}
	}

	public abstract Union union(String select);

	public abstract Union union(Select select);

	public abstract Union unionAll(Select select);

	public abstract Union unionAll(String select);
}