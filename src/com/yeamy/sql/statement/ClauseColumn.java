package com.yeamy.sql.statement;

public class ClauseColumn extends Column {
	private Clause expr;

	public ClauseColumn(Clause expr) {
		this(expr, null);
	}

	public ClauseColumn(Clause expr, String nameAlias) {
		super(null);
		this.expr = expr;
		this.nameAlias = nameAlias;
	}

	@Override
	public void rawName(StringBuilder sb) {
		expr.toSQL(sb);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		if (nameAlias != null) {
			sb.append('(');
			rawName(sb);
			sb.append(") AS `").append(nameAlias).append('`');
		} else {
			expr.toSQL(sb);
		}
	}

	@Override
	public void tableInFrom(StringBuilder sb) {
		if (expr.column instanceof Column) {
			((Column) expr.column).tableInFrom(sb);
		} else {
			throw new NullPointerException("table is null");
		}
	}

	@Override
	public Object table() {
		if (tableAlias != null) {
			return tableAlias;
		}
		if (expr.column instanceof Column) {
			return ((Column) expr.column).table();
		}
		return null;
	}

}
