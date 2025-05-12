package yeamy.sql.statement.function;

import yeamy.sql.statement.Column;
import yeamy.sql.statement.Searchable;

public class Length extends Column {

	public Length(String name) {
		super(name);
	}

	public Length(String table, String name) {
		super(table, name);
	}

	public Length(Searchable<?> column) {
		super(column);
	}

	public Length(Searchable<?> table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("LENGTH(");
		super.toSQL(sb);
		sb.append(')');
	}

}