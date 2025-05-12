package yeamy.sql.statement.columninfo;

public class Decimal extends MySQLNumber {
	private int size, d;

	public Decimal(int size, int d) {
		this.size = size;
		this.d = d;
	}

	@Override
	protected void dataType(StringBuilder sql) {
		sql.append("decimal").append('(').append(size).append(", ").append(d).append(')');
	}

}
