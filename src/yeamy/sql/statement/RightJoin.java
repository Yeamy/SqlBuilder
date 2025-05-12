package yeamy.sql.statement;

public class RightJoin extends Join<RightJoin> {

	public RightJoin(Column column, Column pattern) {
		super(" RIGHT JOIN ", column, pattern);
	}

}
