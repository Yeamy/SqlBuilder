package yeamy.sql.statement.date;

import yeamy.sql.statement.Column;
import yeamy.sql.statement.Searchable;

public class Extract extends Column {
	public DateType type;

	public Extract(String name, DateType type) {
		super(name);
		this.type = type;
	}

	public Extract(String table, String name, DateType type) {
		super(table, name);
		this.type = type;
	}

	public Extract(Searchable<?> column, DateType type) {
		super(column);
		this.type = type;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("EXTRACT(").append(type).append(" FROM ");
		super.toSQL(sb);
		sb.append(')');
	}

}
