package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.AbsColumn;
import com.yeamy.sql.statement.SQLString;

public class IfNull extends AbsColumn<IfNull> {
	private Object col, whenNull;

	public IfNull(Object col, Object whenNull) {
		this(col, whenNull, null);
	}

	public IfNull(Object col, Object whenNull, String nameAlias) {
		this.col = col;
		this.whenNull = whenNull;
		this.nameAlias = nameAlias;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("IFNULL(");
		SQLString.appendValue(sb, col);
		sb.append(',');
		SQLString.appendValue(sb, whenNull);
		sb.append(')');
	}

}
