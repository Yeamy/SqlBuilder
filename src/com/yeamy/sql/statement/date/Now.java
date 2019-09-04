package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;
import com.yeamy.sql.statement.function.FunctionColumn;

/**
 * 2008-12-29 16:25:46
 */
@Target(DataBase.MySQL)
public class Now extends FunctionColumn {
	public static final Now now = new Now(null);

	public Now(String alias) {
		super(null);
		as(alias);
	}

	@Override
	public String fun() {
		return "NOW";
	}

	@Override
	protected void params(StringBuilder sb) {
	}
}
