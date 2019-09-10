package com.yeamy.sql.statement;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Sort implements SQLString {
	static final String ASC = "ASC";
	static final String DESC = "DESC";

	private LinkedHashMap<Object, String> sort = new LinkedHashMap<>();

	Sort(AbsColumn column, String sort) {
		this.sort.put(column, sort);
	}

	Sort(String column, String sort) {
		this.sort.put(column, sort);
	}

	public Sort asc(AbsColumn column) {
		sort.put(column, ASC);
		return this;
	}

	public Sort desc(AbsColumn column) {
		sort.put(column, DESC);
		return this;
	}

	public Sort asc(String column) {
		sort.put(column, ASC);
		return this;
	}

	public Sort desc(String column) {
		sort.put(column, DESC);
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		if (sort.size() == 0) {
			return;
		}
		sql.append(" ORDER BY ");
		boolean f = true;
		Set<Entry<Object, String>> set = sort.entrySet();
		for (Entry<Object, String> cell : set) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			Object column = cell.getKey();
			if (column instanceof AbsColumn) {
				AbsColumn c = (AbsColumn) column;
				c.shortName(sql);
			} else {
				SQLString.appendColumn(sql, column.toString());
			}
			String sc = cell.getValue();
			if (sc != null) {
				sql.append(" ");
				sql.append(sc);
			}
		}
	}

}
