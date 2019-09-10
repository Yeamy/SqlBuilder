package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.AbsColumn;
import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;

/**
 * 16:25:46
 */
@Target(DataBase.MySQL)
public class CurTime extends AbsColumn {
	public static final CurTime curTime = new CurTime(null);

	public CurTime(String alias) {
		as(alias);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("CURTIME()");
	}

}
