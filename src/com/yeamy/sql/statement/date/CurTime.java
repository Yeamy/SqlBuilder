package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;

/**
 * 16:25:46
 */
@Target(DataBase.MySQL)
public class CurTime extends Column {
	public static final CurTime curTime = new CurTime(null);

	public CurTime(String alias) {
		super(null);
		as(alias);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("CURTIME()");
	}
}
