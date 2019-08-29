package com.yeamy.sql.statement.function;

import com.yeamy.sql.statement.Column;
import com.yeamy.sql.statement.SQLString;

public class Having implements SQLString {
	private Column column;
	private String operator;
	private Number num;

	public Having(FunctionColumn column, String operator, Number num) {
		this.column = column;
		this.operator = operator;
		this.num = num;
	}

	public Column getColumn() {
		return column;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		sb.append(" HAVING ");
		column.toSQL(sb);
		sb.append(operator).append(num);
	}

	public static Having equal(FunctionColumn column, Number num) {
		return new Having(column, " = ", num);
	}

	public static Having greaterThan(FunctionColumn column, Number num) {
		return new Having(column, " > ", num);
	}

	public static Having greaterEqual(FunctionColumn column, Number num) {
		return new Having(column, " >= ", num);
	}

	public static Having lessThan(FunctionColumn column, Number num) {
		return new Having(column, " < ", num);
	}

	public static Having lessEqual(FunctionColumn column, Number num) {
		return new Having(column, " <= ", num);
	}
}
