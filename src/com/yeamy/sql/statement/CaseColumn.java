package com.yeamy.sql.statement;

import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;
import com.yeamy.sql.statement.function.Union;

@Target(DataBase.MySQL)
public class CaseColumn extends Column {
	private Object[] kv;

	public CaseColumn(String name, Object... kv) {
		this((String) null, name, kv);
	}

	public CaseColumn(String table, String name, Object... kv) {
		super(table, name);
		this.kv = kv;
	}

	public CaseColumn(Select table, String name, Object... kv) {
		super(table, null, name);
		this.kv = kv;
	}

	public CaseColumn(Union table, String name, Object... kv) {
		super(table, null, name);
		this.kv = kv;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("CASE ");
		super.toSQL(sb);
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

}
