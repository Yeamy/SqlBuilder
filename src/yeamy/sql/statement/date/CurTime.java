package yeamy.sql.statement.date;

import yeamy.sql.statement.AbsColumn;

/**
 * 16:25:46
 */
public class CurTime extends AbsColumn<CurTime> {
	public static final CurTime curTime = new CurTime(null);

	public CurTime(String alias) {
		as(alias);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("CURTIME()");
	}

}
