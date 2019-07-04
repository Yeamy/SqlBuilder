package com.yeamy.sql.statement;

import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;

@Target(DataBase.MySQL)
public class CaseColumn extends Column {
	private Column src;
	private Object[] kv;

	public CaseColumn(Column src, Object... kv) {
		super(null);
		this.src = src;
		this.kv = kv;
	}

	public CaseColumn(String name, Object... kv) {
		this(null, name, kv);
	}

	public CaseColumn(String table, String name, Object... kv) {
		super(null);
		this.src = new Column(table, name);
		this.kv = kv;
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("CASE ");
		src.toSQL(sb);
		int l = kv.length / 2;
		for (int i = 0; i < l; i++) {
			sb.append(" WHEN ");
			SQLString.appendValue(sb, kv[i * 2]);
			sb.append(" THEN ");
			SQLString.appendValue(sb, kv[i * 2 + 1]);
		}
		if (kv.length % 2 == 1) {
			sb.append(" ELSE ");
			SQLString.appendValue(sb, kv[kv.length - 1]);
		}
		sb.append(" END");
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
		src.tableInFrom(sb);
	}

	@Override
	public String tableName() {
		if (tableAlias != null) {
			return tableAlias;
		}
		return src.tableName();
	}

}
