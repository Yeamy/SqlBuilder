package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.AbsColumn;
import com.yeamy.sql.statement.SQLString;

public class Virtual extends AbsColumn {
	private Object value;

	public Virtual(Object value, String nameAlias) {
		this.value = value;
		this.nameAlias = nameAlias;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		SQLString.appendValue(sb, value);
	}

}
