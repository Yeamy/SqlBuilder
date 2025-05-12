package yeamy.sql.statement.date;

import yeamy.sql.statement.AbsColumn;

/**
 * 2008-12-29
 */
public class CurDate extends AbsColumn<CurDate> {

	public static final CurDate curDate = new CurDate(null);

	public CurDate(String alias) {
		as(alias);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("CURDATE()");
	}

}
