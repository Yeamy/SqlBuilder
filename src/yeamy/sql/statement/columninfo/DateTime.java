package yeamy.sql.statement.columninfo;

public class DateTime extends MySQLTime {

	@Override
	protected void dataType(StringBuilder sql) {
		sql.append("datetime");
	}

}
