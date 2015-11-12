import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GraphPanel extends JPanel implements ActionListener {

	private String function;
	private JTextField box;
	private JTextField xmin;
	private JTextField xmax;
	private JTextField ymin;
	private JTextField ymax;
	private double xlow;
	private double xhigh;
	private double ylow;
	private double yhigh;
	private JButton integral;
	
	public GraphPanel(String s) {
		function = s;
		xlow = -15;
		xhigh = 15;
		ylow = -10;
		yhigh = 10;
		box = new JTextField();
		setLayout(null);
		add(box);
		box.setSize(1000, 50);
		box.setLocation(580, 50);
		box.setFont(new Font("Arial", Font.PLAIN, 50));
		box.addActionListener(this);
		xmin = new JTextField();
		add(xmin);
		xmin.setSize(80, 50);
		xmin.setLocation(20, 50);
		xmin.setFont(new Font("Arial", Font.PLAIN, 50));
		xmin.addActionListener(new ChangeScale());
		xmax = new JTextField();
		add(xmax);
		xmax.setSize(80, 50);
		xmax.setLocation(120, 50);
		xmax.setFont(new Font("Arial", Font.PLAIN, 50));
		xmax.addActionListener(new ChangeScale());
		ymin = new JTextField();
		add(ymin);
		ymin.setSize(80, 50);
		ymin.setLocation(1920, 50);
		ymin.setFont(new Font("Arial", Font.PLAIN, 50));
		ymin.addActionListener(new ChangeScale());
		ymax = new JTextField();
		add(ymax);
		ymax.setSize(80, 50);
		ymax.setLocation(2020, 50);
		ymax.setFont(new Font("Arial", Font.PLAIN, 50));
		ymax.addActionListener(new ChangeScale());
		// integral = new JButton("INTEGRAL");
		// add(integral);
		// integral.setSize(200, 50);
		// integral.setLocation(1650, 50);
	}

	private class ChangeScale implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			double a = Double.parseDouble(xmin.getText());
			double b = Double.parseDouble(xmax.getText());
			double c = Double.parseDouble(ymin.getText());
			double d = Double.parseDouble(ymax.getText());
			if (a < b && c < d) {
				xlow = a;
				xhigh = b;
				ylow = c;
				yhigh = d;
			}
			repaint();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		g.setColor(Color.DARK_GRAY);
		for (double i = xlow; i <= xhigh; i += (xhigh - xlow) / 30) {
			g.drawLine((int)((i - xlow) / (xhigh - xlow) * 2160), 0, (int)((i - xlow) / (xhigh - xlow) * 2160), 1440);
		}
		for (double i = ylow; i <= yhigh; i += (yhigh - ylow) / 20) {
			g.drawLine(0, 1440 - (int)((i - ylow) / (yhigh - ylow) * 1440), 2160, 1440 - (int)((i - ylow) / (yhigh - ylow) * 1440));
		}
		g.setColor(Color.WHITE);
		for (double i = 0; i < 2159; i += 0.01) {
			double y1 = ParseExpression.parse(function, xlow + (double)i / 2160.0 * (xhigh - xlow));
			double y2 = ParseExpression.parse(function, xlow + (double)(i + 1) / 2160.0 * (xhigh - xlow));
			// System.out.println(y1 + "\t" + y2);
			if (y1 < yhigh && y1 > ylow && y2 < 1000 + yhigh && y2 > ylow - 1000) g.drawLine((int)Math.round(i), (int)Math.round(1440 * (yhigh - y1) / (yhigh - ylow)), (int)Math.round(i + 1), (int)Math.round(1440 * (yhigh - y2) / (yhigh - ylow)));
		}
		int yofx = 1440 - (int)((-ylow) / (yhigh - ylow) * 1440);
		int xofy = (int)((-xlow) / (xhigh - xlow) * 2160);
		if (ylow < 0 && yhigh > 0) {
			g.drawLine(0, yofx, 2160, yofx);
		}
		if (xlow < 0 && xhigh > 0) {
			g.drawLine(xofy, 0, xofy, 1440);
		}
		g.setFont(new Font("Arial", Font.PLAIN, 24));
		for (double i = xlow; i <= xhigh; i += (xhigh - xlow) / 30) {
			g.drawLine((int)((i - xlow) / (xhigh - xlow) * 2160), yofx - 10, (int)((i - xlow) / (xhigh - xlow) * 2160), yofx + 10);
			if (i != 0) g.drawString((int)i + "", (int)((i - xlow) / (xhigh - xlow) * 2160) - 10, yofx + 40);
		}
		for (double i = ylow; i <= yhigh; i += (yhigh - ylow) / 20) {
			g.drawLine(xofy - 10, 1440 - (int)((i - ylow) / (yhigh - ylow) * 1440), xofy + 10, 1440 - (int)((i - ylow) / (yhigh - ylow) * 1440));
			if (i != 0) g.drawString((int)i + "", xofy - 40, 1440 - (int)((i - ylow) / (yhigh - ylow) * 1440) + 10);
		}
	}

	public void actionPerformed(ActionEvent e) {
		try {
			ParseExpression.parse(box.getText(), 0);
		} catch (Exception exc) {
			box.setForeground(Color.RED);
			Timer timer = new Timer(2000, new MakeRed());
			timer.setRepeats(false);
			timer.start();
			return;
		}
		function = box.getText();
		repaint();
	}

	private class MakeRed implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			box.setForeground(Color.BLACK);
		}
	}
}