package com.yeamy.sql.statement;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Sort implements SQLString {
	static final String ASC = "ASC";
	static final String DESC = "DESC";

	private LinkedHashMap<Column, String> sort = new LinkedHashMap<>();

	Sort(Column column, String sort) {
		this.sort.put(column, sort);
	}

	Sort(String column, String sort) {
		this.sort.put(new Column(column), sort);
	}

	public Sort asc(Column column) {
		sort.put(column, ASC);
		return this;
	}

	public Sort desc(Column column) {
		sort.put(column, DESC);
		return this;
	}

	public Sort asc(String column) {
		sort.put(new Column(column), ASC);
		return this;
	}

	public Sort desc(String column) {
		sort.put(new Column(column), DESC);
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		if (sort.size() == 0) {
			return;
		}
		sql.append(" ORDER BY ");
		boolean f = true;
		Set<Entry<Column, String>> set = sort.entrySet();
		for (Entry<Column, String> cell : set) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			cell.getKey().nameInWhere(sql);
			String sc = cell.getValue();
			if (sc != null) {
				sql.append(" ");
				sql.append(sc);
			}
		}
	}

}
