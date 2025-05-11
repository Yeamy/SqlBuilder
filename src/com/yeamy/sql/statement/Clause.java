package com.yeamy.sql.statement;

import java.util.*;

@SuppressWarnings("rawtypes")
public abstract class Clause extends TableColumn<Clause> {
	protected Object column;

	public Object getColumn() {
		return column;
	}

	protected void appendColumn(StringBuilder sb) {
		if (column instanceof AbsColumn) {
			((AbsColumn<?>) column).toSQL(sb);
		} else {
			SQLString.appendColumn(sb, column.toString());
		}
	}

	@Override
	public void signTable(Map<Object, TableColumn> tables) {
		if (column != null && column instanceof TableColumn) {
			((TableColumn<?>) column).signTable(tables);
		}
	}

	@Override
	public void unSignTable(Map<Object, TableColumn> tables) {
		if (column != null && column instanceof TableColumn) {
			((TableColumn<?>) column).unSignTable(tables);
		}
	}

	@Override
	public void tableInFrom(StringBuilder sb) {
		if (column != null && column instanceof TableColumn) {
			((TableColumn) column).tableInFrom(sb);
		}
	}

	private static class SimpleClause extends Clause {
		protected String pattern;

		SimpleClause(AbsColumn<?> column, String pattern) {
			this.column = column;
			this.pattern = pattern;
		}

		SimpleClause(String column, String pattern) {
			this.column = column;
			this.pattern = pattern;
		}

		@Override
		protected void subSQL(StringBuilder sb) {
			appendColumn(sb);
			sb.append(pattern);
		}
	}

	private static class NormalClause extends Clause {
		protected String cal;
		protected Object pattern;

		NormalClause(AbsColumn<?> column, String cal, Object pattern) {
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
			appendColumn(sb);
			sb.append(cal);
			SQLString.appendValue(sb, pattern);
		}

		@Override
		public void signTable(Map<Object, TableColumn> tables) {
			super.signTable(tables);
			if (pattern != null && pattern instanceof TableColumn) {
				((TableColumn<?>) pattern).signTable(tables);
			}
		}

		@Override
		public void unSignTable(Map<Object, TableColumn> tables) {
			super.unSignTable(tables);
			if (pattern != null && pattern instanceof TableColumn) {
				((TableColumn<?>) pattern).unSignTable(tables);
			}
		}
	}

	private static class ClauseIn<T> extends Clause {
		private Collection<T> array;
		private int[] intArray;
		private String cal;

		ClauseIn(AbsColumn<?> column, String cal, Collection<T> array) {
			this.column = column;
			this.array = array;
			this.cal = cal;
		}

		ClauseIn(String column, String cal, Collection<T> array) {
			this.column = column;
			this.array = array;
			this.cal = cal;
		}

		ClauseIn(AbsColumn<?> column, String cal, int[] intArray) {
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
			appendColumn(sb);
			sb.append(cal);
			sb.append('(');
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

	private static class ClauseBetween extends Clause {
		private Object start;
		private Object end;

		ClauseBetween(AbsColumn<?> column, Object start, Object end) {
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
			appendColumn(sb);
			sb.append(" BETWEEN ");
			SQLString.appendValue(sb, start);
			sb.append(" AND ");
			SQLString.appendValue(sb, end);
		}

		@Override
		public void signTable(Map<Object, TableColumn> tables) {
			super.signTable(tables);
			if (start != null && start instanceof TableColumn) {
				((TableColumn<?>) start).signTable(tables);
			}
			if (end != null && end instanceof TableColumn) {
				((TableColumn<?>) end).signTable(tables);
			}
		}

		@Override
		public void unSignTable(Map<Object, TableColumn> tables) {
			super.unSignTable(tables);
			if (start != null && start instanceof TableColumn) {
				((TableColumn<?>) start).unSignTable(tables);
			}
			if (end != null && end instanceof TableColumn) {
				((TableColumn<?>) end).unSignTable(tables);
			}
		}
	}

	private static class ClauseRaw extends Clause {
		private String raw;

		ClauseRaw(String raw) {
			this.raw = raw;
		}

		@Override
		protected void subSQL(StringBuilder sb) {
			sb.append(raw);
		}
	}

	public static Clause andAll(List<Clause> list) {
		switch (list.size()) {
			case 0:
				return null;
			case 1:
				return list.get(0);
			default:
				MultiClause clause = null;
				for (Clause li : list) {
					if (clause == null) {
						clause = new MultiClause(li);
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
				MultiClause clause = null;
				for (Clause li : list) {
					if (clause == null) {
						clause = new MultiClause(li);
					} else {
						clause.or(li);
					}
				}
				return clause;
		}
	}
	// ---------------------------------------------------------------------------

	// IS NULL
	public static Clause isNull(AbsColumn<?> column) {
		return new SimpleClause(column, " IS NULL");
	}

	// IS NULL
	public static Clause isNull(String column) {
		return new SimpleClause(column, " IS NULL");
	}

	// IS NULL
	public static Clause isNull(String table, String column) {
		return new SimpleClause(new Column(table, column), " IS NULL");
	}

	// IS NOT NULL
	public static Clause isNotNull(AbsColumn<?> column) {
		return new SimpleClause(column, " IS NOT NULL");
	}

	// IS NOT NULL
	public static Clause isNotNull(String column) {
		return new SimpleClause(column, " IS NOT NULL");
	}

	public static Clause isNotNull(String table, String column) {
		return new SimpleClause(new Column(table, column), " IS NOT NULL");
	}

	// = 等于
	public static Clause equal(AbsColumn<?> column, Object pattern) {
		return new NormalClause(column, " = ", pattern);
	}

	// = 等于
	public static Clause equal(String column, Object pattern) {
		return new NormalClause(column, " = ", pattern);
	}

	// = 等于
	public static Clause equal(String table, String column, Object pattern) {
		return new NormalClause(new Column(table, column), " = ", pattern);
	}

	public static Clause equal(String table, String column, String pTable, String pColumn) {
		return new NormalClause(new Column(table, column), " = ", new Column(pTable, pColumn));
	}

	// <> 不等于
	public static Clause notEqual(AbsColumn<?> column, Object pattern) {
		return new NormalClause(column, " <> ", pattern);
	}

	// <> 不等于
	public static Clause notEqual(String column, Object pattern) {
		return new NormalClause(column, " <> ", pattern);
	}

	// > 大于
	public static Clause notEqual(String table, String column, Object pattern) {
		return new NormalClause(new Column(table, column), " <> ", pattern);
	}

	public static Clause notEqual(String table, String column, String pTable, String pColumn) {
		return new NormalClause(new Column(table, column), " <> ", new Column(pTable, pColumn));
	}

	// > 大于
	public static Clause greaterThan(AbsColumn<?> column, Object pattern) {
		return new NormalClause(column, " > ", pattern);
	}

	// > 大于
	public static Clause greaterThan(String column, Object pattern) {
		return new NormalClause(column, " > ", pattern);
	}

	// > 大于
	public static Clause greaterThan(String table, String column, Object pattern) {
		return new NormalClause(new Column(table, column), " > ", pattern);
	}

	// > 大于
	public static Clause greaterThan(String table, String column, String pTable, String pColumn) {
		return new NormalClause(new Column(table, column), " > ", new Column(pTable, pColumn));
	}

	// < 小于
	public static Clause lessThan(AbsColumn<?> column, Object pattern) {
		return new NormalClause(column, " < ", pattern);
	}

	// < 小于
	public static Clause lessThan(String column, Object pattern) {
		return new NormalClause(column, " < ", pattern);
	}

	// < 小于
	public static Clause lessThan(String table, String column, Object pattern) {
		return new NormalClause(new Column(table, column), " < ", pattern);
	}

	// < 小于
	public static Clause lessThan(String table, String column, String pTable, String pColumn) {
		return new NormalClause(new Column(table, column), " < ", new Column(pTable, pColumn));
	}

	// >= 大于等于
	public static Clause greaterEqual(AbsColumn<?> column, Object pattern) {
		return new NormalClause(column, " >= ", pattern);
	}

	// >= 大于等于
	public static Clause greaterEqual(String column, Object pattern) {
		return new NormalClause(column, " >= ", pattern);
	}

	// >= 大于等于
	public static Clause greaterEqual(String table, String column, Object pattern) {
		return new NormalClause(new Column(table, column), " >= ", pattern);
	}

	// >= 大于等于
	public static Clause greaterEqual(String table, String column, String pTable, String pColumn) {
		return new NormalClause(new Column(table, column), " >= ", new Column(pTable, pColumn));
	}

	// <= 小于等于
	public static Clause lessEqual(AbsColumn<?> column, Object pattern) {
		return new NormalClause(column, " <= ", pattern);
	}

	// <= 小于等于
	public static Clause lessEqual(String column, Object pattern) {
		return new NormalClause(column, " <= ", pattern);
	}

	// <= 小于等于
	public static Clause lessEqual(String table, String column, Object pattern) {
		return new NormalClause(new Column(table, column), " <= ", pattern);
	}

	// <= 小于等于
	public static Clause lessEqual(String table, String column, String pTable, String pColumn) {
		return new NormalClause(new Column(table, column), " <= ", new Column(pTable, pColumn));
	}

	// LIKE 搜索某种模式
	public static Clause like(AbsColumn<?> column, String pattern) {
		return new NormalClause(column, " LIKE ", pattern);
	}

	// LIKE 搜索某种模式
	public static Clause like(String column, String pattern) {
		return new NormalClause(column, " LIKE ", pattern);
	}

	// <= 小于等于
	public static Clause like(String table, String column, String pattern) {
		return new NormalClause(new Column(table, column), " LIKE ", pattern);
	}

	public static Clause contains(AbsColumn<?> column, String pattern) {
		return like(column, '%' + pattern + '%');
	}

	public static Clause contains(String column, String pattern) {
		return new NormalClause(column, " LIKE ", '%' + pattern + '%');
	}

	public static Clause contains(String table, String column, String pattern) {
		return like(new Column(table, column), '%' + pattern + '%');
	}

	public static Clause startWith(AbsColumn<?> column, String pattern) {
		return like(column, pattern + '%');
	}

	public static Clause startWith(String column, String pattern) {
		return new NormalClause(column, " LIKE ", pattern + '%');
	}

	public static Clause startWith(String table, String column, String pattern) {
		return like(new Column(table, column), pattern + '%');
	}

	public static Clause endWith(AbsColumn<?> column, String pattern) {
		return like(column, '%' + pattern);
	}

	public static Clause endWith(String column, String pattern) {
		return new NormalClause(column, " LIKE ", '%' + pattern);
	}

	public static Clause endWith(String table, String column, String pattern) {
		return like(new Column(table, column), '%' + pattern);
	}

	// NOT LIKE
	public static Clause notLike(Column column, String pattern) {
		return new NormalClause(column, " NOT LIKE ", pattern);
	}

	// NOT LIKE
	public static Clause notLike(String column, String pattern) {
		return new NormalClause(column, " NOT LIKE ", pattern);
	}

	public static Clause notLike(String table, String column, String pattern) {
		return new NormalClause(new Column(table, column), " NOT LIKE ", pattern);
	}

	public static Clause notContains(Column column, String pattern) {
		return new NormalClause(column, " NOT LIKE ", '%' + pattern + '%');
	}

	public static Clause notContains(String table, String column, String pattern) {
		return new NormalClause(new Column(table, column), " NOT LIKE ", '%' + pattern + '%');
	}

	public static Clause notStartWith(Column column, String pattern) {
		return new NormalClause(column, " NOT LIKE ", pattern + '%');
	}

	public static Clause notStartWith(String table, String column, String pattern) {
		return new NormalClause(new Column(table, column), " NOT LIKE ", pattern + '%');
	}

	public static Clause notEndWith(Column column, String pattern) {
		return new NormalClause(column, " NOT LIKE ", '%' + pattern);
	}

	public static Clause notEndWith(String table, String column, String pattern) {
		return new NormalClause(new Column(table, column), " NOT LIKE ", '%' + pattern);
	}

	// IN
	public static Clause in(AbsColumn<?> column, int... array) {
		return new ClauseIn<>(column, " IN ", array);
	}

	// IN
	public static Clause in(String column, int... array) {
		return new ClauseIn(column, " IN ", array);
	}

	public static Clause in(String table, String column, int... array) {
		return new NormalClause(new Column(table, column), " IN ", array);
	}

	@SafeVarargs
	public static <T> Clause in(AbsColumn<?> column, T... pattern) {
		return new ClauseIn<>(column, " IN ", Arrays.asList(pattern));
	}

	@SafeVarargs
	public static <T> Clause in(String column, T... pattern) {
		return new ClauseIn<>(column, " IN ", Arrays.asList(pattern));
	}

	public static <T> Clause in(String table, String column, T[] pattern) {
		return new NormalClause(new Column(table, column), " IN ", Arrays.asList(pattern));
	}

	public static <T> Clause in(AbsColumn<?> column, Collection<T> pattern) {
		return new ClauseIn<>(column, " IN ", pattern);
	}

	public static <T> Clause in(String column, Collection<T> pattern) {
		return new ClauseIn<>(column, " IN ", pattern);
	}

	public static <T> Clause in(String table, String column, Collection<T> pattern) {
		return new NormalClause(new Column(table, column), " IN ", pattern);
	}

	public static Clause in(AbsColumn<?> column, Select pattern) {
		return new NormalClause(column, " IN ", pattern);
	}

	public static Clause in(String column, Select pattern) {
		return new NormalClause(column, " IN ", pattern);
	}

	public static Clause in(String table, String column, Select pattern) {
		return new NormalClause(new Column(table, column), " IN ", pattern);
	}

	// NOT IN
	public static <T> Clause notIn(AbsColumn<?> column, int... array) {
		return new ClauseIn<T>(column, " NOT IN ", array);
	}
	// NOT IN
	public static Clause notIn(String column, int... array) {
		return new ClauseIn(column, " NOT IN ", array);
	}

	public static <T> Clause notIn(String table, String column, int... array) {
		return new NormalClause(new Column(table, column), " NOT IN ", array);
	}

	@SafeVarargs
	public static <T> Clause notIn(AbsColumn<?> column, T... pattern) {
		return new ClauseIn<T>(column, " NOT IN ", Arrays.asList(pattern));
	}
	@SafeVarargs
	public static <T> Clause notIn(String column, T... pattern) {
		return new ClauseIn<>(column, " NOT IN ", Arrays.asList(pattern));
	}

	public static <T> Clause notIn(String table, String column, T[] pattern) {
		return new NormalClause(new Column(table, column), " NOT IN ", Arrays.asList(pattern));
	}

	public static <T> Clause notIn(AbsColumn<?> column, Collection<T> pattern) {
		return new ClauseIn<T>(column, " NOT IN ", pattern);
	}

	public static <T> Clause notIn(String column, Collection<T> pattern) {
		return new ClauseIn<>(column, " NOT IN ", pattern);
	}

	public static <T> Clause notIn(String table, String column, Collection<T> pattern) {
		return new NormalClause(new Column(table, column), " NOT IN ", pattern);
	}

	public static Clause notIn(AbsColumn<?> column, Select pattern) {
		return new NormalClause(column, " NOT IN ", pattern);
	}

	public static Clause notIn(String column, Select pattern) {
		return new NormalClause(column, " NOT IN ", pattern);
	}

	public static Clause notIn(String table, String column, Select pattern) {
		return new NormalClause(new Column(table, column), " NOT IN ", pattern);
	}

	// BETWEEN 在某个范围内
	public static Clause between(AbsColumn<?> column, Object start, Object end) {
		return new ClauseBetween(column, start, end);
	}

	// BETWEEN 在某个范围内
	public static Clause between(String column, Object start, Object end) {
		return new ClauseBetween(column, start, end);
	}

	public static Clause between(String table, String column, Object start, Object end) {
		return new ClauseBetween(new Column(table, column), start, end);
	}

	// ---------------------------------------------------------------------------

	public static Clause parse(String sql) {
		return new ClauseRaw(sql);
	}

	public static Clause exists(Select select) {
		return new Exists("EXISTS", select);
	}

	public static Clause notExists(Select select) {
		return new Exists("NOT EXISTS", select);
	}

	private static class Exists extends Clause {
		private final String prefix;
		private final Select select;

		private Exists(String prefix, Select select) {
			this.prefix = prefix;
			this.select = select;
		}

		@Override
		protected void subSQL(StringBuilder sql) {
			sql.append(prefix);
			SQLString.appendValue(sql, select);
			select.toSQL(sql);
		}

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
