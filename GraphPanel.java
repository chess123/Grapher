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
		box.setFont(new Font("Arial", Font.BOLD, 50));
		box.addActionListener(this);
		xmin = new JTextField();
		setLayout(null);
		add(xmin);
		xmin.setSize(80, 50);
		xmin.setLocation(20, 50);
		xmin.setFont(new Font("Arial", Font.BOLD, 50));
		xmin.addActionListener(new ChangeScale());
		xmax = new JTextField();
		setLayout(null);
		add(xmax);
		xmax.setSize(80, 50);
		xmax.setLocation(120, 50);
		xmax.setFont(new Font("Arial", Font.BOLD, 50));
		xmax.addActionListener(new ChangeScale());
		ymin = new JTextField();
		setLayout(null);
		add(ymin);
		ymin.setSize(80, 50);
		ymin.setLocation(1920, 50);
		ymin.setFont(new Font("Arial", Font.BOLD, 50));
		ymin.addActionListener(new ChangeScale());
		ymax = new JTextField();
		setLayout(null);
		add(ymax);
		ymax.setSize(80, 50);
		ymax.setLocation(2020, 50);
		ymax.setFont(new Font("Arial", Font.BOLD, 50));
		ymax.addActionListener(new ChangeScale());
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
		g.setColor(Color.WHITE);
		for (int i = 0; i < 2159; i++) {
			double y1 = ParseExpression.parse(function, xlow + (double)i / 2160.0 * (xhigh - xlow));
			double y2 = ParseExpression.parse(function, xlow + (double)(i + 1) / 2160.0 * (xhigh - xlow));
			// System.out.println(y1 + "\t" + y2);
			if (y2 < 1000 * yhigh && y1 < 1000 * yhigh) g.drawLine(i, (int)(720 - (1440 / (yhigh - ylow)) * y1), i + 1, (int)(720 - (1440 / (yhigh - ylow)) * y2));
		}
		int yofx = 1440 - (int)((-ylow) / (yhigh - ylow) * 1440);
		int xofy = (int)((-xlow) / (xhigh - xlow) * 2160);
		if (ylow < 0 && yhigh > 0) {
			g.drawLine(0, yofx, 2160, yofx);
		}
		if (xlow < 0 && xhigh > 0) {
			g.drawLine(xofy, 0, xofy, 1440);
		}
	}

	public void actionPerformed(ActionEvent e) {
		function = box.getText();
		repaint();
	}
}