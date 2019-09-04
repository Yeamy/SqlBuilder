package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;
import com.yeamy.sql.statement.function.FunctionColumn;

/**
 * 2008-12-29
 */
@Target(DataBase.MySQL)
public class CurDate extends FunctionColumn {
	public static final CurDate curDate = new CurDate(null);

	public CurDate(String alias) {
		super(null);
		as(alias);
	}

	@Override
	public String fun() {
		return "CURDATE";
	}

	@Override
	protected void params(StringBuilder sb) {
	}

}
