public class IntegralCalculator {

	public static String func;
	
	public static double integralRRAM(double start, double end, double numRects) throws Throwable {
		double sum = 0;
		for (double i = 1; i <= numRects; i++)
			sum += ParseExpression.parse(func, start + i * (end - start) / numRects) * (end - start) / numRects;
		return sum;
	}

	public static double integralLRAM(double start, double end, double numRects) throws Throwable {
		double sum = 0;
		for (double i = 1; i <= numRects; i++)
			sum += ParseExpression.parse(func, start + (i - 1) * (end - start) / numRects) * (end - start) / numRects;
		return sum;
	}

	public static double integral(double start, double end, double numRects) throws Throwable {
		if (end < start) {
			return -1 * integral(end, start, numRects);
		}
		double d = (integralLRAM(start, end, numRects) + integralRRAM(start, end, numRects)) / 2;
		return d;
	}


	public static double derivative(double x) throws Throwable {
		double d = derivative(x, 0.000001);
		return d;
	}

	public static double derivative(double x, double limitDist) throws Throwable {
		double d1 = (ParseExpression.parse(func, x + limitDist) - ParseExpression.parse(func, x)) / limitDist;
		limitDist *= -1;
		double d2 = (ParseExpression.parse(func, x + limitDist) - ParseExpression.parse(func, x)) / limitDist;
		return (d1 + d2) / 2;
	}

	public static double nthDerivative(double x, int times) throws Throwable {
		if (times == 0) return ParseExpression.parse(func, x);
		return (nthDerivative(x + 0.0001, times - 1) - nthDerivative(x - 0.0001, times - 1)) / 0.0002;
	}

	public static void main(String[] args) throws Throwable {
		while (true) {
			func = Prompt.getString("\nEnter a function -> ");
			try {
				ParseExpression.parse(func, 0);
			} catch (Throwable e) {
				System.out.println("\nERROR: Invalid function");
				continue;
			}
			break;
		}
		// double start = Prompt.getDouble("\nEnter the lower limit -> ");
		// double end = Prompt.getDouble("\nEnter the upper limit -> ");
		// double area = integral(start, end, 100000);
		// if (Double.toString(area).length() > 8) area = Double.parseDouble(Double.toString(area).substring(0, 8));
		// System.out.println("\nThe integral from " + start + " to " + end + " of (" + func + ") dx is " + area + "\n");
		int n = Prompt.getInt("Enter the nth derivative that you would like: ");
		double val = Prompt.getDouble("Enter the x-value at which you would like to find that derivative: ");
		double dval = nthDerivative(val, n);
		System.out.println("d^" + n + "/dx^" + n + " at " + val + " = " + dval);
	}
}