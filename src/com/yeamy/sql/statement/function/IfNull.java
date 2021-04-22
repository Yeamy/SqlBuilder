package com.yeamy.sql.statement.function;

import java.util.Map;

import com.yeamy.sql.statement.SQLString;
import com.yeamy.sql.statement.TableColumn;

public class IfNull extends TableColumn<IfNull> {
	private Object t, f;

	public IfNull(Object t, Object f) {
		this(t, f, null);
	}

	public IfNull(Object t, Object f, String nameAlias) {
		this.t = t;
		this.f = f;
		this.nameAlias = nameAlias;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("IFNULL(");
		SQLString.appendValue(sb, t);
		sb.append(',');
		SQLString.appendValue(sb, f);
		sb.append(')');
	}

	@Override
	public void tableInFrom(StringBuilder sb) {
	}

	@Override
	public void signTable(Map<Object, TableColumn> tables) {
	}

	@Override
	public void unSignTable(Map<Object, TableColumn> tables) {
	}

}
