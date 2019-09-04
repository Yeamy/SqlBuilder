package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;
import com.yeamy.sql.statement.function.FunctionColumn;

/**
 * 16:25:46
 */
@Target(DataBase.MySQL)
public class CurTime extends FunctionColumn {
	public static final CurTime curTime = new CurTime(null);

	public CurTime(String alias) {
		super(null);
		as(alias);
	}

	@Override
	public String fun() {
		return "CURTIME";
	}

	@Override
	protected void params(StringBuilder sb) {
	}
}
