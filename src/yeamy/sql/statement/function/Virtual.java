package yeamy.sql.statement.function;

import yeamy.sql.statement.AbsColumn;
import yeamy.sql.statement.SQLString;

public class Virtual extends AbsColumn<Virtual> {
	private Object value;

	public Virtual(Object value, String nameAlias) {
		this.value = value;
		this.nameAlias = nameAlias;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		SQLString.appendValue(sb, value);
	}

}
