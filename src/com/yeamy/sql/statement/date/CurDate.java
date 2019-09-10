package com.yeamy.sql.statement.date;

import com.yeamy.sql.statement.AbsColumn;
import com.yeamy.sql.statement.annotation.DataBase;
import com.yeamy.sql.statement.annotation.Target;

/**
 * 2008-12-29
 */
@Target(DataBase.MySQL)
public class CurDate extends AbsColumn {

	public static final CurDate curDate = new CurDate(null);

	public CurDate(String alias) {
		as(alias);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("CURDATE()");
	}

}
