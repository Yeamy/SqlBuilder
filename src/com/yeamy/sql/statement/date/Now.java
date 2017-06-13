package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.Column;

/**
 * 2008-12-29 16:25:46
 */
public class Now extends Column {
	public static final Now now = new Now(null);

	public Now(String alias) {
		super(null);
		as(alias);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		if (nameAlias == null) {
			sb.append("NOW()");
		} else {
			sb.append("NOW() AS `").append(nameAlias).append('`');
		}
	}

	@Override
	public void nameInWhere(StringBuilder sb) {
		sb.append("NOW()");
	}
}
