package yeamy.sql.statement.function;

import yeamy.sql.statement.Column;
import yeamy.sql.statement.Searchable;

public class Lcase extends Column {

	public Lcase(String name) {
		super(name);
	}

	public Lcase(String table, String name) {
		super(table, name);
	}

	public Lcase(Searchable<?> column, String format) {
		super(column);
	}

	public Lcase(Searchable<?> table, String tableAlias, String name) {
		super(table, tableAlias, name);
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("LCASE(");
		super.toSQL(sb);
		sb.append(')');
	}

}