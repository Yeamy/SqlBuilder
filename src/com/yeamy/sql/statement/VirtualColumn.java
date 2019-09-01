package com.yeamy.sql.statement;

public class VirtualColumn extends Column {
	private Object value;

	public VirtualColumn(String name, Object value) {
		super(name);
		this.value = value;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append(" `").append(name).append('`');
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		SQLString.appendValue(sb, value);
		toSQL(sb);
	}
}
