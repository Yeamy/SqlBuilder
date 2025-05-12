package yeamy.sql.statement;

public class Asc extends Sort {
	public Asc(AbsColumn<?> column) {
		super(column, ASC);
	}

	public Asc(String column) {
		super(column, ASC);
	}

	public Asc(String table, String column) {
		super(table, column, ASC);
	}
}