package com.yeamy.sql.statement;

public class Having implements SQLString {
	private AbsColumn column;
	private String operator;
	private Number num;

	public Having(AbsColumn column, String operator, Number num) {
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

	public static Having equal(AbsColumn column, Number num) {
		return new Having(column, " = ", num);
	}

	public static Having greaterThan(AbsColumn column, Number num) {
		return new Having(column, " > ", num);
	}

	public static Having greaterEqual(AbsColumn column, Number num) {
		return new Having(column, " >= ", num);
	}

	public static Having lessThan(AbsColumn column, Number num) {
		return new Having(column, " < ", num);
	}

	public static Having lessEqual(AbsColumn column, Number num) {
		return new Having(column, " <= ", num);
	}
}
