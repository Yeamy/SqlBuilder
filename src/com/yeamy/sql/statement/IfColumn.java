package com.yeamy.sql.statement;

import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;

@Target(DataBase.MySQL)
public class IfColumn extends Column {
	private Clause expr;
	private Object t, f;

	public IfColumn(Clause expr, Object t, Object f) {
		this(expr, t, f, null);
	}

	public IfColumn(Clause expr, Object t, Object f, String nameAlias) {
		super(null);
		this.expr = expr;
		this.t = t;
		this.f = f;
		this.nameAlias = nameAlias;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("IF(");
		expr.toSQL(sb);
		sb.append(',');
		SQLString.appendValue(sb, t);
		sb.append(',');
		SQLString.appendValue(sb, f);
		sb.append(')');
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
	public String tableSign() {
		if (expr.column instanceof Column) {
			return ((Column) expr.column).tableSign();
		}
		return null;
	}

}
