public class ParseExpression {
	public static void main(String[] args) {
		String expr = Prompt.getString("Enter an expression -> ");
		double val = Prompt.getDouble("Enter a value -> ");
		System.out.println("f(x) = " + parseEvaluate(expr, val));
	}

	public static double parseEvaluate(String expr, double x) {
		if (expr.charAt(0) == '(' && expr.charAt(expr.length() - 1) == ')') expr = expr.substring(1, expr.length() - 1);
		if (expr.equals("x")) return x;
		if (isNumber(expr)) return Double.parseDouble(expr);
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
}