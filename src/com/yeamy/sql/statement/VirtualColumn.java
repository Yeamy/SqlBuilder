package com.yeamy.sql.statement;

public class VirtualColumn extends Column {
	private String value;

	public VirtualColumn(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append('\'').append(value).append("' `")//
				.append(name).append('`');
	}

}
