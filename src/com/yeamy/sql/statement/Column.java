package com.yeamy.sql.statement;

import java.util.Collection;

import com.yeamy.sql.statement.function.Union;

public class Column implements SQLString {
	public static final String ALL = "*";
	public final String table;
	public final SQLString select;
	public final String name;
	public String tableAlias;
	public String nameAlias;

	public Column(String name) {
		this.select = null;
		this.table = null;
		this.name = name;
	}

	public Column(String table, String name) {
		this.select = null;
		this.table = table;
		this.name = name;
	}

	public Column(Select select, String tableAlias, String name) {
		this.select = select;
		this.table = null;
		this.tableAlias = tableAlias;
		this.name = name;
	}

	public Column(Union union, String tableAlias, String name) {
		this.select = union;
		this.table = null;
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

	public Column as(String nameAlias) {
		this.nameAlias = nameAlias;
		return this;
	}

	/**
	 * table as tableAlias
	 */
	public void tableInFrom(StringBuilder sb) {
		if (select != null) {
			sb.append('(');
			select.toSQL(sb);
			sb.append(')');
		} else {
			sb.append('`').append(table).append('`');
		}
		if (tableAlias != null) {
			sb.append(" AS `").append(tableAlias).append('`');
		}
	}

	/**
	 * table.name
	 */
	public void nameInFunction(StringBuilder sb) {
		if (name == null) {
			return;
		}
		if (tableAlias != null) {
			sb.append('`').append(tableAlias).append('`').append('.');
		} else if (table != null) {
			sb.append('`').append(table).append('`').append('.');
		}
		if ("*".equals(name)) {
			sb.append('*');
		} else {
			sb.append('`').append(name).append('`');
		}
	}

	/**
	 * tableAlias.name AS alias -> table.name AS alias
	 */
	public void nameInColumn(StringBuilder sb) {
		if (name == null) {
			return;
		}
		if (tableAlias != null) {
			sb.append('`').append(tableAlias).append('`').append('.');
		} else if (table != null) {
			sb.append('`').append(table).append('`').append('.');
		}
		if ("*".equals(name)) {
			sb.append('*');
		} else {
			sb.append('`').append(name).append('`');
			if (nameAlias != null) {
				sb.append(" AS `").append(nameAlias).append('`');
			}
		}
	}

	public String tableName() {
		if (tableAlias != null) {
			return tableAlias;
		}
		return table;
	}

	/**
	 * columnAlias -> tableAlias.name -> table.name
	 */
	public void nameInWhere(StringBuilder sb) {
		if (name == null) {
			return;
		}
		if (tableAlias != null) {
			sb.append('`').append(tableAlias).append('`').append('.');
		} else if (table != null) {
			sb.append('`').append(table).append('`').append('.');
		}
		if ("*".equals(name)) {
			sb.append('*');
		} else {
			sb.append('`').append(name).append('`');
		}
	}

	@Override
	public void toSQL(StringBuilder sb) {
		nameInWhere(sb);
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

	private boolean compare(String a, String b) {
		if (a == null) {
			if (b == null) {
				return true;
			}
		} else if (a.equals(b)) {
			return true;
		}
		return false;
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