package com.yeamy.sql.statement.function;

import java.util.ArrayList;

import com.yeamy.sql.statement.SQLString;
import com.yeamy.sql.statement.Select;

public class Union implements SQLString {
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

	public void union(String select) {
		list.add(new UnionLi(select, " UNION "));
	}

	public void union(Select select) {
		list.add(new UnionLi(select, " UNION "));
	}

	public void unionAll(Select select) {
		list.add(new UnionLi(select, " UNION ALL "));
	}

	public void unionAll(String select) {
		list.add(new UnionLi(select, " UNION ALL "));
	}

	@Override
	public void toSQL(StringBuilder sql) {
		for (UnionLi li : list) {
			if (li.logic != null) {
				sql.append(li.logic);
			}
			if (li.select instanceof SQLString) {
				((SQLString) li.select).toSQL(sql);
			} else {
				sql.append(li.select);
			}
		}
	}

}
