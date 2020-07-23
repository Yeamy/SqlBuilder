package com.yeamy.sql.statement;

public abstract class Searchable<T extends Searchable<T>> implements SQLString {
	private Sort orderBy;
	private int limitOffset = -1, limit = 0;

	/**
	 * @see {@link #Sort}
	 * @see {@link #Asc}
	 * @see {@link #Desc}
	 */
	@SuppressWarnings("unchecked")
	public T orderBy(Sort orderBy) {
		this.orderBy = orderBy;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T limit(int limit) {
		this.limit = limit;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T limit(int offset, int limit) {
		this.limitOffset = offset;
		this.limit = limit;
		return (T) this;
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