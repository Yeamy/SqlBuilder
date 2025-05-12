package yeamy.sql.statement;

public abstract class AbsColumn<T extends AbsColumn<T>> implements SQLString {
	protected String nameAlias;

	@SuppressWarnings("unchecked")
	public T as(String nameAlias) {
		this.nameAlias = nameAlias;
		return (T) this;
	}

	/**
	 * tableAlias.name AS alias -> table.name AS alias
	 */
	public void nameInColumn(StringBuilder sql) {
		toSQL(sql);
		if (nameAlias != null) {
			sql.append(" AS ");
			SQLString.appendColumn(sql, nameAlias);
		}
	}

	/**
	 * group by / order by
	 */
	public void shortName(StringBuilder sql) {
		if (nameAlias != null) {
			SQLString.appendColumn(sql, nameAlias);
		} else {
			toSQL(sql);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toSQL(sb);
		return sb.toString();
	}

}