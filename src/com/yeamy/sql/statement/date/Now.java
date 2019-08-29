package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;

/**
 * 2008-12-29 16:25:46
 */
@Target(DataBase.MySQL)
public class Now extends Column {
	public static final Now now = new Now(null);

	public Now(String alias) {
		super(null);
		as(alias);
	}

	@Override
	public void rawName(StringBuilder sb) {
		sb.append("NOW()");
	}
}
