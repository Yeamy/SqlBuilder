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
	public void nameInColumn(StringBuilder sb) {
		if (nameAlias != null) {
			sb.append('(');
			expr.toSQL(sb);
			sb.append(") AS `").append(nameAlias).append('`');
		} else {
			expr.toSQL(sb);
		}
	}

	@Override
	public void toSQL(StringBuilder sb) {
		nameInColumn(sb);
	}

	@Override
	public void nameInFunction(StringBuilder sb) {
	}

	@Override
	public void nameInWhere(StringBuilder sb) {
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
	public String tableName() {
		if (tableAlias != null) {
			return tableAlias;
		}
		if (expr.column instanceof Column) {
			return ((Column) expr.column).tableName();
		}
		return null;
	}

}