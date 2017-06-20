package com.yeamy.sql.statement;

import java.util.Collection;
import java.util.List;

public abstract class Clause implements SQLString {

	private static class SimpleClause extends Clause {
		protected Column column;
		protected String pattern;

		SimpleClause(Column column, String pattern) {
			this.column = column;
			this.pattern = pattern;
		}

		@Override
		public void toSQL(StringBuilder sb) {
			column.nameInWhere(sb);
			sb.append(pattern);
		}
	}

	private static class NormalClause extends Clause {
		protected Column column;
		protected String cal;
		protected Object pattern;

		NormalClause(Column column, String cal, Object pattern) {
			this.column = column;
			this.cal = cal;
			this.pattern = pattern;
		}

		@Override
		public void toSQL(StringBuilder sb) {
			column.nameInWhere(sb);
			sb.append(cal);
			Object pattern = this.pattern;
			if (pattern instanceof Column) {
				Column column = (Column) pattern;
				column.nameInWhere(sb);
			} else {
				SQLString.appendValue(sb, pattern);
			}
		}
	}

	private static class ClauseIn extends Clause {
		private Column column;
		private Object[] array;

		ClauseIn(Column column, Object[] array) {
			this.column = column;
			this.array = array;
		}

		@Override
		public void toSQL(StringBuilder sb) {
			column.nameInWhere(sb);
			sb.append(" IN (");
			boolean f = true;
			for (Object li : array) {
				if (f) {
					f = false;
				} else {
					sb.append(", ");
				}
				System.out.println(li.getClass().getName() + "    " + li);// XXX
				SQLString.appendValue(sb, li);
			}
			sb.append(')');
		}
	}

	private static class ClauseBetween extends Clause {
		protected Column column;
		private Object start;
		private Object end;

		ClauseBetween(Column column, Object start, Object end) {
			this.column = column;
			this.start = start;
			this.end = end;
		}

		@Override
		public void toSQL(StringBuilder sb) {
			column.nameInWhere(sb);
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
					clause = new MultiClause(list.get(0));
					f = false;
				}
				clause.and(li);
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
					clause = new MultiClause(list.get(0));
					f = false;
				}
				clause.or(li);
			}
			return clause;
		}
	}

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
	public static Clause moreThan(Column column, Object pattern) {
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

	public static Clause in(Column column, int... array) {
		return new Clause() {
			@Override
			public void toSQL(StringBuilder sb) {
				column.nameInWhere(sb);
				sb.append(" IN (");
				boolean f = true;
				for (int li : array) {
					if (f) {
						f = false;
					} else {
						sb.append(", ");
					}
					SQLString.appendValue(sb, li);
				}
				sb.append(')');
			}
		};
	}

	public static Clause in(Column column, Object... pattern) {
		return new ClauseIn(column, pattern);
	}

	public static Clause in(Column column, Collection<?> pattern) {
		return new ClauseIn(column, pattern.toArray());
	}

	// BETWEEN 在某个范围内
	public static Clause between(Column column, Object start, Object end) {
		return new ClauseBetween(column, start, end);
	}
}
