package yeamy.sql.statement.function;

import yeamy.sql.statement.Column;
import yeamy.sql.statement.SQLString;
import yeamy.sql.statement.Searchable;

public class Format extends Column {
	public String format;

	public Format(String name, String format) {
		super(name);
		this.format = format;
	}

	public Format(String table, String name, String format) {
		super(table, name);
		this.format = format;
	}

	public Format(Searchable<?> column, String format) {
		super(column);
		this.format = format;
	}

	public Format(Searchable<?> table, String tableAlias, String name, String format) {
		super(table, tableAlias, name);
		this.format = format;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append("FORMAT(");
		super.toSQL(sb);
		sb.append(", ");
		SQLString.appendValue(sb, format);
		sb.append(')');
	}

}