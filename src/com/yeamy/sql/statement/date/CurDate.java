package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;

/**
 * 2008-12-29
 */
@Target(DataBase.MySQL)
public class CurDate extends Column {
	public static final CurDate curDate = new CurDate(null);

	public CurDate(String alias) {
		super(null);
		as(alias);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		if (nameAlias == null) {
			sb.append("CURDATE()");
		} else {
			sb.append("CURDATE() AS `").append(nameAlias).append('`');
		}
	}

	@Override
	public void nameInWhere(StringBuilder sb) {
		sb.append("CURDATE()");
	}
}
