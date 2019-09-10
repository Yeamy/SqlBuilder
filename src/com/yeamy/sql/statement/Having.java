package com.yeamy.sql.statement;

import com.yeamy.sql.statement.function.Function;

public class Having implements SQLString {
	private AbsColumn column;
	private String operator;
	private Number num;

	public Having(Function column, String operator, Number num) {
		this.column = column;
		this.operator = operator;
		this.num = num;
	}

	public AbsColumn getColumn() {
		return column;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append(" HAVING ");
		column.toSQL(sb);
		sb.append(operator).append(num);
	}

	public static Having equal(Function column, Number num) {
		return new Having(column, " = ", num);
	}

	public static Having greaterThan(Function column, Number num) {
		return new Having(column, " > ", num);
	}

	public static Having greaterEqual(Function column, Number num) {
		return new Having(column, " >= ", num);
	}

	public static Having lessThan(Function column, Number num) {
		return new Having(column, " < ", num);
	}

	public static Having lessEqual(Function column, Number num) {
		return new Having(column, " <= ", num);
	}
}
