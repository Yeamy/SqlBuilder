package com.yeamy.sql.statement;

import java.util.Collection;
import java.util.List;

public abstract class Clause implements SQLString {

	private abstract static class BaseClause extends Clause {
		protected Object column;

		protected void addColumn(StringBuilder sb) {
			if (column instanceof Column) {
				sb.append(column);
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
		public void toSQL(StringBuilder sb) {
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
		public void toSQL(StringBuilder sb) {
			addColumn(sb);
			sb.append(cal);
			SQLString.appendValue(sb, pattern);
		}
	}

	private static class ClauseIn extends BaseClause {
		private Object[] array;

		ClauseIn(Column column, Object[] array) {
			this.column = column;
			this.array = array;
		}

		ClauseIn(String column, Object[] array) {
			this.column = column;
			this.array = array;
		}

		@Override
		public void toSQL(StringBuilder sb) {
			addColumn(sb);
			sb.append(" IN (");
			boolean f = true;
			for (Object li : array) {
				if (f) {
					f = false;
				} else {
					sb.append(", ");
				}
				SQLString.appendValue(sb, li);
			}
			sb.append(')');
		}
	}

	private static class ClauseInIntArray extends BaseClause {
		private int[] array;

		ClauseInIntArray(Column column, int[] array) {
			this.column = column;
			this.array = array;
		}

		ClauseInIntArray(String column, int[] array) {
			this.column = column;
			this.array = array;
		}

		@Override
		public void toSQL(StringBuilder sb) {
			addColumn(sb);
			sb.append(" IN (");
			boolean f = true;
			for (int li : array) {
				if (f) {
					f = false;
				} else {
					sb.append(", ");
				}
				sb.append(li);
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
		public void toSQL(StringBuilder sb) {
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
			boolean f = true;
			MultiClause clause = null;
			for (Clause li : list) {
				if (f) {
					clause = new MultiClause(li);
					f = false;
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
			boolean f = true;
			MultiClause clause = null;
			for (Clause li : list) {
				if (f) {
					clause = new MultiClause(li);
					f = false;
				} else {
					clause.and(li);
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

	public static Clause in(Column column, int... array) {
		return new ClauseInIntArray(column, array);
	}

	public static Clause in(Column column, Object... pattern) {
		return new ClauseIn(column, pattern);
	}

	public static Clause in(Column column, Collection<?> pattern) {
		return new ClauseIn(column, pattern.toArray());
	}

	public static Clause in(Column column, Select pattern) {
		return new NormalClause(column, " IN ", pattern);
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

	public static Clause in(String column, int... array) {
		return new ClauseInIntArray(column, array);
	}

	public static Clause in(String column, Object... pattern) {
		return new ClauseIn(column, pattern);
	}

	public static Clause in(String column, Collection<?> pattern) {
		return new ClauseIn(column, pattern.toArray());
	}

	public static Clause in(String column, Select pattern) {
		return new NormalClause(column, " IN ", pattern);
	}

	// BETWEEN 在某个范围内
	public static Clause between(String column, Object start, Object end) {
		return new ClauseBetween(column, start, end);
	}

	// Multi ----------------------------------------------------------------------

	public Clause and(Clause clause) {
		return new MultiClause(this).and(clause);
	}

	public Clause or(Clause clause) {
		return new MultiClause(this).or(clause);
	}

}
