package com.yeamy.sql.statement.function;

import java.util.LinkedList;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.SQLString;
import com.yeamy.sql.statement.Searchable;

public class Case extends Column {
	private Object _else;
	private LinkedList<Object> kv = new LinkedList<>();

	public Case(String name) {
		this((String) null, name);
	}

	public Case(String table, String name) {
		super(table, name);
	}

	public Case(Searchable table, String name) {
		super(table, null, name);
	}
	
	public Case(Searchable column) {
		super(column);
	}

	public Case when(Object when, Object then) {
		kv.addLast(when);
		kv.addLast(then);
		return this;
	}

	public Case elseAs(Object _else) {
		this._else = _else;
		return this;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("CASE ");
		super.toSQL(sb);
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
