import java.io.PrintWriter;
import java.awt.*;
import javax.swing.*;

public class GraphingCalculator extends JFrame {

	static String func;

	public GraphingCalculator() {
		super("Graphing Calculator");
		getContentPane().add(new GraphPanel(func));
		int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) throws Throwable {
		while (true) {
			func = "";
			// try {
			// 	ParseExpression.parse(func, 0);
			// } catch (Throwable e) {
			// 	System.out.println("ERROR: Invalid function");
			// 	continue;
			// }
			break;
		}
		GraphingCalculator gc = new GraphingCalculator();
	}
}