package yeamy.sql.statement.columninfo;

public class Int extends MySQLNumber {
	private int size;

	public Int(int size) {
		this.size = size;
	}

	@Override
	protected void dataType(StringBuilder sql) {
		sql.append("int").append('(').append(size).append(')');
	}
}
