package yeamy.sql.statement;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class Update implements SQLString {
	private final String table;
	private final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
	private LinkedList<Join<?>> joins;
	private Clause where;

	public Update(String table) {
		this.table = table;
	}

	public Update set(String column, Object value) {
		map.put(column, value);
		return this;
	}

	public Update ifNotNullSet(String column, Object value) {
		if (value != null) map.put(column, value);
		return this;
	}

	public Update increase(String column, int number) {
		return increase(column, (long) number);
	}

	public Update reduce(String column, int number) {
		map.put(column, new Expression(column, '-', number));
		return this;
	}

	public Update increase(String column, long number) {
		map.put(column, new Expression(column, '+', number));
		return this;
	}

	public Update reduce(String column, long number) {
		map.put(column, new Expression(column, '-', number));
		return this;
	}

	public Update increase(String column, Number number) {
		map.put(column, new Expression(column, '+', number));
		return this;
	}

	public Update reduce(String column, Number number) {
		map.put(column, new Expression(column, '-', number));
		return this;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	private class Expression implements SQLString {
		private String column;
		private long longNum;
		private Number number;
		private char sign;

		public Expression(String column, char sign, long longNum) {
			this.column = column;
			this.sign = sign;
			this.longNum = longNum;
		}

		public Expression(String column, char sign, Number number) {
			this.column = column;
			this.sign = sign;
			this.number = number;
		}

		@Override
		public void toSQL(StringBuilder sql) {
			SQLString.appendColumn(sql, column);
			sql.append(' ').append(sign).append(' ');
			if (number != null) {
				sql.append(number);
			} else {
				sql.append(longNum);
			}
		}
	}

	public Update addAll(Map<String, Object> cv) {
		return setAll(cv);
	}

	public Update setAll(Map<String, Object> cv) {
		map.putAll(cv);
		return this;
	}

	public Update where(Clause where) {
		this.where = where;
		return this;
	}

	private void initJoins() {
		if (joins == null) {
			joins = new LinkedList<>();
		}
	}

	public Update join(Join<?> join) {
		initJoins();
		joins.add(join);
		return this;
	}

	public Update innerJoin(String src, Column pattern) {
		initJoins();
		joins.add(new InnerJoin(new Column(table, src), pattern));
		return this;
	}

	public Update innerJoin(Column src, Column pattern) {
		initJoins();
		joins.add(new InnerJoin(src, pattern));
		return this;
	}

	public Update leftJoin(String src, Column pattern) {
		return leftJoin(new Column(table, src), pattern);
	}

	public Update leftJoin(Column src, Column pattern) {
		initJoins();
		joins.add(new LeftJoin(src, pattern));
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		sql.append("UPDATE ");
		SQLString.appendTable(sql, table);
		// join on
		if (joins != null) {
			for (Join<?> join : joins) {
				join.toSQL(sql);
			}
		}
		sql.append(" SET ");
		boolean f = true;
		for (Entry<String, Object> li : map.entrySet()) {
			if (f) {
				f = false;
			} else {
				sql.append(", ");
			}
			SQLString.appendColumn(sql, li.getKey());
			sql.append(" = ");
			Object value = li.getValue();
			if (value instanceof AbsColumn) {
				AbsColumn<?> col = (AbsColumn<?>) value;
				col.toSQL(sql);
			} else {
				SQLString.appendValue(sql, value);
			}
		}
		if (where != null) {
			sql.append(" WHERE ");
			where.toSQL(sql);
		}
		sql.append(';');
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toSQL(sb);
		sb.append(';');
		return sb.toString();
	}
}
