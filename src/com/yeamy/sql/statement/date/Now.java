package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.AbsColumn;

/**
 * 2008-12-29 16:25:46
 */
public class Now extends AbsColumn<Now> {
	public static final Now now = new Now(null);

	public Now(String alias) {
		as(alias);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("NOW()");
	}

}
