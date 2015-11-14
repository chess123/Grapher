public class IntegralCalculator {
	
	public static double integralRRAM(double start, double end, double numRects) {
		double sum = 0;
		for (double i = 1; i <= numRects; i++)
			sum += ParseExpression.parse(func, start + i * (end - start) / numRects) * (end - start) / numRects;
		return sum;
	}

	public static double integralLRAM(double start, double end, double numRects) {
		double sum = 0;
		for (double i = 1; i <= numRects; i++)
			sum += ParseExpression.parse(func, start + (i - 1) * (end - start) / numRects) * (end - start) / numRects;
		return sum;
	}

	public static double integral(double start, double end, double numRects) {
		double d = (integralLRAM(start, end, numRects) + integralRRAM(start, end, numRects)) / 2;
		return d;
	}


	public static double derivative(double x) {
		double d = derivative(x, 0.000001);
		return d;
	}

	public static double derivative(double x, double limitDist) {
		double d1 = (ParseExpression.parse(func, x + limitDist) - ParseExpression.parse(func, x)) / limitDist;
		limitDist *= -1;
		double d2 = (ParseExpression.parse(func, x + limitDist) - ParseExpression.parse(func, x)) / limitDist;
		return (d1 + d2) / 2;
	}

	public static double nthDerivative(double n, double times) {
		for (double i = 0; i < times; i++) {
			n = derivative(n, 0.000001);
		}
		return n;
	}
}