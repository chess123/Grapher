import java.io.PrintWriter;
import java.awt.*;
import javax.swing.*;

public class IntegralCalculator extends JFrame {

	static String func;

	public IntegralCalculator() {
		super("Integral Calculator");
		getContentPane().add(new GraphPanel(func));
		setSize(2160, 1440);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

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

	public static void main(String[] args) {
		func = Prompt.getString("Enter a function f(x) = ");
		// double start = Prompt.getDouble("Enter the lower limit of integration: ");
		// double end = Prompt.getDouble("Enter the upper limit of integration: ");
		// System.out.println("NINT(" + func + ", x, " + start + ", " + end + ") = " + integral(start, end, 1000000));
		IntegralCalculator ic = new IntegralCalculator();
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