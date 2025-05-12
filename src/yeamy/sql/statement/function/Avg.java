package yeamy.sql.statement.function;

import yeamy.sql.statement.Column;
import yeamy.sql.statement.Searchable;

public class Avg extends Column {

	public Avg(String name) {
		super(name);
	}

	public Avg(String table, String name) {
		super(table, name);
	}

	public Avg(Searchable<?> table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}
	
	public Avg(Searchable<?> column) {
		super(column);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("AVG(");
		super.toSQL(sb);
		sb.append(')');
	}

}
