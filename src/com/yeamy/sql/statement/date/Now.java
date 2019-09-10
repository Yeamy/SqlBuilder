package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.AbsColumn;
import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;

/**
 * 2008-12-29 16:25:46
 */
@Target(DataBase.MySQL)
public class Now extends AbsColumn {
	public static final Now now = new Now(null);

	public Now(String alias) {
		as(alias);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("NOW()");
	}

}
