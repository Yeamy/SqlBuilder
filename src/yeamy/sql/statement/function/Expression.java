package yeamy.sql.statement.function;

import yeamy.sql.statement.AbsColumn;
import yeamy.sql.statement.SQLString;

public class Expression extends AbsColumn<Expression> {
	private static final char[] symbol = { '+', '-', '*', '/', '%', '(', ')', '|', '>', '<', '=' };
	private Object[] objs;

	/**
	 * @param Object {@code AbsColumn}<br>
	 *               {@code Searchable}<br>
	 *               {@code String}(earch char is symbol) <br>
	 *               {@code String}(value) <br>
	 *               {@code SQLString}(value) <br>
	 *               {@code Number}
	 */
	public Expression(Object... objs) {
		this.objs = objs;
	}

	@Override
	public void toSQL(StringBuilder sb) {
		boolean f = true;
		for (Object obj : objs) {
			if (f) {
				f = false;
			} else {
				sb.append(' ');
			}
			if (obj instanceof AbsColumn) {
				((AbsColumn<?>) obj).toSQL(sb);
			} else if (obj instanceof String) {
				String str = (String) obj;
				if (allSymbol(str)) {
					sb.append(str);
				} else {
					SQLString.appendValue(sb, str);
				}
			} else if (obj instanceof Character) {
				char c = (char) obj;
				if (isSymbol(c)) {
					sb.append(c);
				} else {
					SQLString.appendValue(sb, c);
				}
			} else {
				SQLString.appendValue(sb, obj);
			}
		}
	}

	private boolean allSymbol(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!isSymbol(c)) {
				return false;
			}
		}
		return true;
	}

	private boolean isSymbol(char c) {
		for (char s : symbol) {
			if (s == c) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void nameInColumn(StringBuilder sql) {
		if (nameAlias == null) {
			toSQL(sql);
		} else {
			sql.append('(');
			toSQL(sql);
			sql.append(") AS ");
			SQLString.appendColumn(sql, nameAlias);
		}
	}

}
