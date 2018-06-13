package com.yeamy.sql.statement;

public class VirtualColumn extends Column {
	private String value;

	public VirtualColumn(String name, Object value) {
		super(name);
		this.value = String.valueOf(value);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append('\'').append(value).append("' `")//
				.append(name).append('`');
	}
}
