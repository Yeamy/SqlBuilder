package com.yeamy.sql.statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Clause implements SQLString {

	private abstract static class BaseClause extends Clause {
		protected Object column;

		protected void addColumn(StringBuilder sb) {
			if (column instanceof Column) {
				((Column) column).nameInWhere(sb);
			} else {
				sb.append('`').append(column).append('`');
			}
		}
	}

	private static class SimpleClause extends BaseClause {
		protected String pattern;

		SimpleClause(Column column, String pattern) {
			this.column = column;
			this.pattern = pattern;
		}

		SimpleClause(String column, String pattern) {
			this.column = column;
			this.pattern = pattern;
		}

		@Override
		protected void subSQL(StringBuilder sb) {
			addColumn(sb);
			sb.append(pattern);
		}
	}

	private static class NormalClause extends BaseClause {
		protected String cal;
		protected Object pattern;

		NormalClause(Column column, String cal, Object pattern) {
			this.column = column;
			this.cal = cal;
			this.pattern = pattern;
		}

		NormalClause(String column, String cal, Object pattern) {
			this.column = column;
			this.cal = cal;
			this.pattern = pattern;
		}

		@Override
		protected void subSQL(StringBuilder sb) {
			addColumn(sb);
			sb.append(cal);
			SQLString.appendValue(sb, pattern);
		}
	}

	private static class ClauseIn extends BaseClause {
		private Object[] array;
		private int[] intArray;
		private String cal;
		static final String IN = " IN(";
		static final String NOT_IN = " NOT IN(";

		ClauseIn(Column column, String cal, Object[] array) {
			this.column = column;
			this.array = array;
			this.cal = cal;
		}

		ClauseIn(String column, String cal, Object[] array) {
			this.column = column;
			this.array = array;
			this.cal = cal;
		}

		ClauseIn(Column column, String cal, int[] intArray) {
			this.column = column;
			this.intArray = intArray;
			this.cal = cal;
		}

		ClauseIn(String column, String cal, int[] intArray) {
			this.column = column;
			this.intArray = intArray;
			this.cal = cal;
		}

		@Override
		protected void subSQL(StringBuilder sb) {
			addColumn(sb);
			sb.append(cal);
			boolean f = true;
			if (array != null) {
				for (Object li : array) {
					if (f) {
						f = false;
					} else {
						sb.append(", ");
					}
					SQLString.appendValue(sb, li);
				}
			} else {
				for (int li : intArray) {
					if (f) {
						f = false;
					} else {
						sb.append(", ");
					}
					SQLString.appendValue(sb, li);
				}
			}
			sb.append(')');
		}
	}

	private static class ClauseBetween extends BaseClause {
		private Object start;
		private Object end;

		ClauseBetween(Column column, Object start, Object end) {
			this.column = column;
			this.start = start;
			this.end = end;
		}

		ClauseBetween(String column, Object start, Object end) {
			this.column = column;
			this.start = start;
			this.end = end;
		}

		@Override
		protected void subSQL(StringBuilder sb) {
			addColumn(sb);
			sb.append(" BETWEEN ");
			SQLString.appendValue(sb, start);
			sb.append(" AND ");
			SQLString.appendValue(sb, end);
		}
	}

	public static Clause andAll(List<Clause> list) {
		switch (list.size()) {
		case 0:
			return null;
		case 1:
			return list.get(0);
		default:
			Clause clause = null;
			for (Clause li : list) {
				if (clause == null) {
					clause = li;
				} else {
					clause.and(li);
				}
			}
			return clause;
		}
	}

	public static Clause orAll(List<Clause> list) {
		switch (list.size()) {
		case 0:
			return null;
		case 1:
			return list.get(0);
		default:
			Clause clause = null;
			for (Clause li : list) {
				if (clause == null) {
					clause = li;
				} else {
					clause.or(li);
				}
			}
			return clause;
		}
	}
	// ---------------------------------------------------------------------------

	// IS NULL
	public static Clause isNull(Column column) {
		return new SimpleClause(column, " IS NULL");
	}

	// IS NOT NULL
	public static Clause isNotNull(Column column) {
		return new SimpleClause(column, " IS NOT NULL");
	}

	// = 等于
	public static Clause equal(Column column, Object pattern) {
		return new NormalClause(column, " = ", pattern);
	}

	// <> 不等于
	public static Clause notEqual(Column column, Object pattern) {
		return new NormalClause(column, " <> ", pattern);
	}

	// > 大于
	public static Clause greaterThan(Column column, Object pattern) {
		return new NormalClause(column, " > ", pattern);
	}

	// < 小于
	public static Clause lessThan(Column column, Object pattern) {
		return new NormalClause(column, " < ", pattern);
	}

	// >= 大于等于
	public static Clause greaterEqual(Column column, Object pattern) {
		return new NormalClause(column, " >= ", pattern);
	}

	// <= 小于等于
	public static Clause lessEqual(Column column, Object pattern) {
		return new NormalClause(column, " <= ", pattern);
	}

	// LIKE 搜索某种模式
	public static Clause like(Column column, String pattern) {
		return new NormalClause(column, " LIKE ", pattern);
	}

	public static Clause contains(Column column, String pattern) {
		return new NormalClause(column, " LIKE ", '%' + pattern + '%');
	}

	public static Clause startWith(Column column, String pattern) {
		return new NormalClause(column, " LIKE ", pattern + '%');
	}

	public static Clause endWith(Column column, String pattern) {
		return new NormalClause(column, " LIKE ", '%' + pattern);
	}

	// IN
	public static Clause in(Column column, int... array) {
		return new ClauseIn(column, ClauseIn.IN, array);
	}

	public static Clause in(Column column, Object... pattern) {
		return new ClauseIn(column, ClauseIn.IN, pattern);
	}

	public static Clause in(Column column, Collection<?> pattern) {
		return new ClauseIn(column, ClauseIn.IN, pattern.toArray());
	}

	public static Clause in(Column column, Select pattern) {
		return new NormalClause(column, " IN ", pattern);
	}

	// NOT IN
	public static Clause notIn(Column column, int... array) {
		return new ClauseIn(column, ClauseIn.NOT_IN, array);
	}

	public static Clause notIn(Column column, Object... pattern) {
		return new ClauseIn(column, ClauseIn.NOT_IN, pattern);
	}

	public static Clause notIn(Column column, Collection<?> pattern) {
		return new ClauseIn(column, ClauseIn.NOT_IN, pattern.toArray());
	}

	public static Clause notIn(Column column, Select pattern) {
		return new NormalClause(column, " NOT IN ", pattern);
	}

	// BETWEEN 在某个范围内
	public static Clause between(Column column, Object start, Object end) {
		return new ClauseBetween(column, start, end);
	}

	// ---------------------------------------------------------------------------

	// IS NULL
	public static Clause isNull(String column) {
		return new SimpleClause(column, " IS NULL");
	}

	// IS NOT NULL
	public static Clause isNotNull(String column) {
		return new SimpleClause(column, " IS NOT NULL");
	}

	// = 等于
	public static Clause equal(String column, Object pattern) {
		return new NormalClause(column, " = ", pattern);
	}

	// <> 不等于
	public static Clause notEqual(String column, Object pattern) {
		return new NormalClause(column, " <> ", pattern);
	}

	// > 大于
	public static Clause greaterThan(String column, Object pattern) {
		return new NormalClause(column, " > ", pattern);
	}

	// < 小于
	public static Clause lessThan(String column, Object pattern) {
		return new NormalClause(column, " < ", pattern);
	}

	// >= 大于等于
	public static Clause greaterEqual(String column, Object pattern) {
		return new NormalClause(column, " >= ", pattern);
	}

	// <= 小于等于
	public static Clause lessEqual(String column, Object pattern) {
		return new NormalClause(column, " <= ", pattern);
	}

	// LIKE 搜索某种模式
	public static Clause like(String column, String pattern) {
		return new NormalClause(column, " LIKE ", pattern);
	}

	public static Clause contains(String column, String pattern) {
		return new NormalClause(column, " LIKE ", '%' + pattern + '%');
	}

	public static Clause startWith(String column, String pattern) {
		return new NormalClause(column, " LIKE ", pattern + '%');
	}

	public static Clause endWith(String column, String pattern) {
		return new NormalClause(column, " LIKE ", '%' + pattern);
	}

	// IN
	public static Clause in(String column, int... array) {
		return new ClauseIn(column, ClauseIn.IN, array);
	}

	public static Clause in(String column, Object... pattern) {
		return new ClauseIn(column, ClauseIn.IN, pattern);
	}

	public static Clause in(String column, Collection<?> pattern) {
		return new ClauseIn(column, ClauseIn.IN, pattern.toArray());
	}

	public static Clause in(String column, Select pattern) {
		return new NormalClause(column, " IN ", pattern);
	}

	// NOT IN
	public static Clause notIn(String column, int... array) {
		return new ClauseIn(column, ClauseIn.NOT_IN, array);
	}

	public static Clause notIn(String column, Object... pattern) {
		return new ClauseIn(column, ClauseIn.NOT_IN, pattern);
	}

	public static Clause notIn(String column, Collection<?> pattern) {
		return new ClauseIn(column, ClauseIn.NOT_IN, pattern.toArray());
	}

	public static Clause notIn(String column, Select pattern) {
		return new NormalClause(column, " NOT IN ", pattern);
	}

	// BETWEEN 在某个范围内
	public static Clause between(String column, Object start, Object end) {
		return new ClauseBetween(column, start, end);
	}

	// Multi ----------------------------------------------------------------------

	private static class ClauseLi {
		Clause clause;
		String logic;

		private ClauseLi(Clause clause, String logic) {
			this.clause = clause;
			this.logic = logic;
		}
	}

	private ArrayList<ClauseLi> clauses;

	protected boolean isMulti() {
		return clauses != null && clauses.size() > 0;
	}

	public Clause and(Clause clause) {
		if (clause == null) {
			return this;
		}
		if (clauses == null) {
			clauses = new ArrayList<>();
		}
		clauses.add(new ClauseLi(clause, " AND "));
		return this;
	}

	public Clause or(Clause clause) {
		if (clause == null) {
			return this;
		}
		if (clauses == null) {
			clauses = new ArrayList<>();
		}
		clauses.add(new ClauseLi(clause, " OR "));
		return this;
	}

	@Override
	public final void toSQL(StringBuilder sql) {
		subSQL(sql);
		if (isMulti()) {
			for (ClauseLi li : clauses) {
				sql.append(li.logic);
				Clause clause = li.clause;
				if (clause.isMulti()) {
					sql.append('(');
					clause.toSQL(sql);
					sql.append(')');
				} else {
					clause.toSQL(sql);
				}
			}
		}
	}

	protected abstract void subSQL(StringBuilder sql);

}
