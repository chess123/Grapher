public class ParseExpression {
	public static void main(String[] args) {
		String expr = Prompt.getString("Enter an expression for f(x) -> ");
		double val = Prompt.getDouble("Enter a value -> ");
		System.out.println("f(" + val + ") = " + parseEvaluate(expr, val));
	}

	public static double parseEvaluate(String expr, double x) {
		if (expr.charAt(0) == '(' && expr.charAt(expr.length() - 1) == ')') expr = expr.substring(1, expr.length() - 1);
		if (expr.equals("x")) return x;
		if (isNumber(expr)) return Double.parseDouble(expr);
		if (expr.equals("pi")) return Math.PI;
		if (expr.equals("e")) return Math.E;
		int maxParen = numParens(expr);
		int num;
		for (int j = 0; j <= maxParen; j++) {
			num = 0;
			for (int i = 0; i < expr.length(); i++) {
				if (expr.charAt(i) == '(') num++;
				if (expr.charAt(i) == ')') num--;
				if (expr.charAt(i) == '+' && num == j) {
					String expr1 = expr.substring(0, i).trim();
					String expr2 = expr.substring(i + 1, expr.length()).trim();
					return parseEvaluate(expr1, x) + parseEvaluate(expr2, x);
				}
			}
			num = 0;
			for (int i = 0; i < expr.length(); i++) {
				if (expr.charAt(i) == '(') num++;
				if (expr.charAt(i) == ')') num--;
				if (expr.charAt(i) == '-' && num == j) {
					String expr1 = expr.substring(0, i).trim();
					String expr2 = expr.substring(i + 1, expr.length()).trim();
					return parseEvaluate(expr1, x) - parseEvaluate(expr2, x);
				}
			}
			num = 0;
			for (int i = 0; i < expr.length(); i++) {
				if (expr.charAt(i) == '(') num++;
				if (expr.charAt(i) == ')') num--;
				if (expr.charAt(i) == '*' && num == j) {
					String expr1 = expr.substring(0, i).trim();
					String expr2 = expr.substring(i + 1, expr.length()).trim();
					return parseEvaluate(expr1, x) * parseEvaluate(expr2, x);
				}
			}
			num = 0;
			for (int i = 0; i < expr.length(); i++) {
				if (expr.charAt(i) == '(') num++;
				if (expr.charAt(i) == ')') num--;
				if (expr.charAt(i) == '/' && num == j) {
					String expr1 = expr.substring(0, i).trim();
					String expr2 = expr.substring(i + 1, expr.length()).trim();
					return parseEvaluate(expr1, x) / parseEvaluate(expr2, x);
				}
			}
			num = 0;
			for (int i = 0; i < expr.length(); i++) {
				if (expr.charAt(i) == '(') num++;
				if (expr.charAt(i) == ')') num--;
				if (expr.charAt(i) == '^' && num == j) {
					String expr1 = expr.substring(0, i).trim();
					String expr2 = expr.substring(i + 1, expr.length()).trim();
					return Math.pow(parseEvaluate(expr1, x), parseEvaluate(expr2, x));
				}
			}
			num = 0;
			for (int i = 0; i < expr.length(); i++) {
				if (expr.charAt(i) == '(') num++;
				if (expr.charAt(i) == ')') num--;
				if (i + 3 < expr.length() && expr.substring(i, i + 3).equals("sin") && num == j) {
					String expr1 = expr.substring(i + 4, nextParen(expr, i + 4)).trim();
					return Math.sin(parseEvaluate(expr1, x));
				}
				if (i + 3 < expr.length() && expr.substring(i, i + 3).equals("cos") && num == j) {
					String expr1 = expr.substring(i + 4, nextParen(expr, i + 4)).trim();
					return Math.cos(parseEvaluate(expr1, x));
				}
				if (i + 3 < expr.length() && expr.substring(i, i + 3).equals("tan") && num == j) {
					String expr1 = expr.substring(i + 4, nextParen(expr, i + 4)).trim();
					return Math.tan(parseEvaluate(expr1, x));
				}
				if (i + 3 < expr.length() && expr.substring(i, i + 3).equals("csc") && num == j) {
					String expr1 = expr.substring(i + 4, nextParen(expr, i + 4)).trim();
					return 1 / Math.sin(parseEvaluate(expr1, x));
				}
				if (i + 3 < expr.length() && expr.substring(i, i + 3).equals("sec") && num == j) {
					String expr1 = expr.substring(i + 4, nextParen(expr, i + 4)).trim();
					return 1 / Math.cos(parseEvaluate(expr1, x));
				}
				if (i + 3 < expr.length() && expr.substring(i, i + 3).equals("cot") && num == j) {
					String expr1 = expr.substring(i + 4, nextParen(expr, i + 4)).trim();
					return 1 / Math.tan(parseEvaluate(expr1, x));
				}
				if (i + 2 < expr.length() && expr.substring(i, i + 2).equals("ln") && num == j) {
					String expr1 = expr.substring(i + 3, nextParen(expr, i + 3)).trim();
					return Math.log(parseEvaluate(expr1, x));
				}
				if (i + 4 < expr.length() && expr.substring(i, i + 4).equals("sqrt") && num == j) {
					String expr1 = expr.substring(i + 5, nextParen(expr, i + 5)).trim();
					return Math.sqrt(parseEvaluate(expr1, x));
				}
				if (i + 3 < expr.length() && expr.substring(i, i + 3).equals("abs") && num == j) {
					String expr1 = expr.substring(i + 4, nextParen(expr, i + 4)).trim();
					return Math.abs(parseEvaluate(expr1, x));
				}
				if (i + 6 < expr.length() && expr.substring(i, i + 6).equals("arcsin") && num == j) {
					String expr1 = expr.substring(i + 7, nextParen(expr, i + 7)).trim();
					return Math.asin(parseEvaluate(expr1, x));
				}
				if (i + 6 < expr.length() && expr.substring(i, i + 6).equals("arccos") && num == j) {
					String expr1 = expr.substring(i + 7, nextParen(expr, i + 7)).trim();
					return Math.acos(parseEvaluate(expr1, x));
				}
				if (i + 6 < expr.length() && expr.substring(i, i + 6).equals("arctan") && num == j) {
					String expr1 = expr.substring(i + 7, nextParen(expr, i + 7)).trim();
					return Math.atan(parseEvaluate(expr1, x));
				}
				if (i + 6 < expr.length() && expr.substring(i, i + 6).equals("arccsc") && num == j) {
					String expr1 = expr.substring(i + 7, nextParen(expr, i + 7)).trim();
					return Math.asin(1 / parseEvaluate(expr1, x));
				}
				if (i + 6 < expr.length() && expr.substring(i, i + 6).equals("arcsec") && num == j) {
					String expr1 = expr.substring(i + 7, nextParen(expr, i + 7)).trim();
					return Math.acos(1 / parseEvaluate(expr1, x));
				}
				if (i + 6 < expr.length() && expr.substring(i, i + 6).equals("arccot") && num == j) {
					String expr1 = expr.substring(i + 7, nextParen(expr, i + 7)).trim();
					return Math.atan(1 / parseEvaluate(expr1, x));
				}
			}
		}
		return 9.9;
	}

	public static boolean isNumber(String s) {
		if (s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')')
			s = s.substring(1, s.length() - 1);
		boolean point = false;
		boolean neg = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ((c < '0' || c > '9') && !((c == '.' && !point) || (c == '-' && !neg && i == 0))) return false;
			else if (c == '.') point = true;
			else if (c == '-' && i == 0) neg = true;
		}
		return true;
	}

	public static int numParens(String str) {
		int cnt = 0;
		int max = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') cnt++;
			if (str.charAt(i) == ')') cnt--;
			if (cnt > max) max = cnt;
		}
		return max;
	}

	public static int nextParen(String str, int index) {
		int cntParens = 1;
		for (int i = index + 1; i < str.length(); i++) {
			if (str.charAt(i) == '(') cntParens++;
			if (str.charAt(i) == ')') cntParens--;
			if (cntParens == 0) return i;
		}
		return -1;
	}
}