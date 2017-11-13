package com.yeamy.sql.statement;

import java.util.ArrayList;

public class MultiClause extends Clause {
	private static class ClauseLi {
		Clause clause;
		String logic;

		private ClauseLi(Clause clause, String logic) {
			this.clause = clause;
			this.logic = logic;
		}
	}

	protected ArrayList<ClauseLi> clauses = new ArrayList<>();

	protected int size() {
		return clauses.size();
	}

	public MultiClause(Clause clause) {
		clauses.add(new ClauseLi(clause, null));
	}

	@Override
	public MultiClause and(Clause clause) {
		clauses.add(new ClauseLi(clause, " AND "));
		return this;
	}

	@Override
	public MultiClause or(Clause clause) {
		clauses.add(new ClauseLi(clause, " OR "));
		return this;
	}

	@Override
	public void toSQL(StringBuilder sql) {
		boolean m = true;
		for (ClauseLi li : clauses) {
			if (m) {
				m = false;
			} else {
				sql.append(li.logic);
			}
			Clause clause = li.clause;
			if (clause instanceof MultiClause) {
				MultiClause mc = (MultiClause) clause;
				if (mc.size() > 0) {
					sql.append('(');
					mc.toSQL(sql);
					sql.append(')');
				} else {
					mc.clauses.get(0).clause.toSQL(sql);
				}
			} else {
				clause.toSQL(sql);
			}
		}
	}

}