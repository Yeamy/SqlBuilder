package com.yeamy.sql.statement.function;

import java.util.LinkedList;

import com.yeamy.sql.statement.AbsColumn;
import com.yeamy.sql.statement.Clause;
import com.yeamy.sql.statement.SQLString;

public class ExprCase extends AbsColumn {
	private Object _else;
	private LinkedList<Object> kv = new LinkedList<>();

	public ExprCase(String nameAlias) {
		as(nameAlias);
	}

	public ExprCase when(Clause when, Object then) {
		kv.addLast(when);
		kv.addLast(then);
		return this;
	}

	public ExprCase elseAs(Object _else) {
		this._else = _else;
		return this;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("CASE ");
		boolean s = true;
		for (Object object : kv) {
			sb.append(s ? " WHEN " : " THEN ");
			SQLString.appendValue(sb, object);
		}
		if (_else != null) {
			sb.append(" ELSE ");
			SQLString.appendValue(sb, _else);
		}
		sb.append(" END");
	}

}
