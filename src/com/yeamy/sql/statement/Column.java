package com.yeamy.sql.statement;

import java.util.Collection;
import java.util.Map;

public class Column extends TableColumn {
	public static final String ALL = "*";
	public final Object table;
	public final String name;
	public String tableAlias;

	public Column(String name) {
		this.table = null;
		this.name = name;
	}

	public Column(String table, String name) {
		this.table = table;
		this.name = name;
	}

	public Column(Searchable table, String tableAlias, String name) {
		this.table = table;
		this.tableAlias = tableAlias;
		this.name = name;
	}

	public Column as(String tableAlias, String nameAlias) {
		this.tableAlias = tableAlias;
		this.nameAlias = nameAlias;
		return this;
	}

	public Column tableAs(String tableAlias) {
		this.tableAlias = tableAlias;
		return this;
	}

	/**
	 * table as tableAlias
	 */
	@Override
	public void tableInFrom(StringBuilder sb) {
		if (table == null) {
			return;
		} else if (table instanceof Searchable) {
			sb.append('(');
			((Searchable) table).toSQL(sb);
			sb.append(')');
		} else {
			sb.append('`').append(table).append('`');
		}
		if (tableAlias != null) {
			sb.append(" AS `").append(tableAlias).append('`');
		}
	}

	@Override
	public void signTable(Map<Object, TableColumn> tables) {
		if (tableAlias != null) {
			tables.put(tableAlias, this);
		} else if (table != null) {
			tables.put(table, this);
		}
	}

	@Override
	public void unSignTable(Map<Object, TableColumn> tables) {
		if (tableAlias != null) {
			tables.remove(tableAlias);
		} else if (table != null) {
			tables.remove(table);
		}
	}

	@Override
	public void toSQL(StringBuilder sb) {
		if (name == null) {
			throw new NullPointerException("column name is null");
		}
		if (tableAlias != null) {
			sb.append('`').append(tableAlias).append('`').append('.');
		} else if (table != null && table instanceof String) {
			sb.append('`').append(table).append('`').append('.');
		}
		if ("*".equals(name)) {
			sb.append('*');
		} else {
			sb.append('`').append(name).append('`');
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Column) {
			Column t = (Column) obj;
			return compare(name, t.name)//
					&& compare(table, t.table)//
					&& compare(nameAlias, t.nameAlias)//
					&& compare(tableAlias, t.tableAlias);
		}
		return super.equals(obj);
	}

	private boolean compare(Object a, Object b) {
		if (a == null) {
			return b == null;
		}
		return a.equals(b);
	}

	public Clause isNotNull() {
		return Clause.isNotNull(this);
	}

	public Clause isNull() {
		return Clause.isNull(this);
	}

	public Clause equalsWith(Object obj) {
		return Clause.equal(this, obj);
	}

	public Clause greaterEqual(Object pattern) {
		return Clause.greaterEqual(this, pattern);
	}

	public Clause greaterThan(Object pattern) {
		return Clause.greaterThan(this, pattern);
	}

	public Clause lessEqual(Object pattern) {
		return Clause.lessEqual(this, pattern);
	}

	public Clause lessThan(Object pattern) {
		return Clause.lessThan(this, pattern);
	}

	public Clause in(Collection<?> pattern) {
		return Clause.in(this, pattern);
	}

	public Clause in(Object... pattern) {
		return Clause.in(this, pattern);
	}

	public Clause in(int... array) {
		return Clause.in(this, array);
	}

	public Clause in(Select pattern) {
		return Clause.in(this, pattern);
	}

	public Clause like(String pattern) {
		return Clause.like(this, pattern);
	}

	public Clause contains(String pattern) {
		return Clause.contains(this, pattern);
	}

	public Clause startWith(String pattern) {
		return Clause.startWith(this, pattern);
	}

	public Clause endWith(String pattern) {
		return Clause.endWith(this, pattern);
	}
}