package com.yeamy.sql.statement.function;

import java.util.Map;

import com.yeamy.sql.statement.AbsColumn;
import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.SQLString;
import com.yeamy.sql.statement.TableColumn;

@SuppressWarnings("rawtypes")
public class If extends TableColumn<If> {
	private AbsColumn<?> column;
	private Object t, f;

	public If(AbsColumn<?> column, Object t, Object f) {
		this(column, t, f, null);
	}

	public If(String table, String column, Object t, Object f) {
		this(table, column, t, f, null);
	}

	public If(AbsColumn<?> column, Object t, Object f, String nameAlias) {
		this.column = column;
		this.t = t;
		this.f = f;
		this.nameAlias = nameAlias;
	}

	public If(String table, String column, Object t, Object f, String nameAlias) {
		this.column = new Column(table, column);
		this.t = t;
		this.f = f;
		this.nameAlias = nameAlias;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("IF(");
		column.toSQL(sb);
		sb.append(',');
		SQLString.appendValue(sb, t);
		sb.append(',');
		SQLString.appendValue(sb, f);
		sb.append(')');
	}

	@Override
	public void tableInFrom(StringBuilder sb) {
		if (column instanceof TableColumn) {
			((TableColumn<?>) column).tableInFrom(sb);
		}
	}

	@Override
	public void signTable(Map<Object, TableColumn> tables) {
		if (column instanceof TableColumn) {
			((TableColumn<?>) column).signTable(tables);
		}
	}

	@Override
	public void unSignTable(Map<Object, TableColumn> tables) {
		if (column instanceof TableColumn) {
			((TableColumn<?>) column).unSignTable(tables);
		}
	}

}
