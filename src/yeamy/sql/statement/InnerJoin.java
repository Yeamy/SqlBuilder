package yeamy.sql.statement;

public class InnerJoin extends Join<InnerJoin> {

	public InnerJoin(Column column, Column pattern) {
		super(" INNER JOIN ", column, pattern);
	}

}