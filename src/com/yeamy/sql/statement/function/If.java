package com.yeamy.sql.statement.function;

import java.util.Map;

import com.yeamy.sql.statement.Clause;
import com.yeamy.sql.statement.SQLString;
import com.yeamy.sql.statement.TableColumn;

public class If extends TableColumn {
	private Clause expr;
	private Object t, f;

	public If(Clause expr, Object t, Object f) {
		this(expr, t, f, null);
	}

	public If(Clause expr, Object t, Object f, String nameAlias) {
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
		expr.tableInFrom(sb);
	}

	@Override
	public void signTable(Map<Object, TableColumn> tables) {
		expr.signTable(tables);
	}

	@Override
	public void unSignTable(Map<Object, TableColumn> tables) {
		expr.unSignTable(tables);
	}

}
