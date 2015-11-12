import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GraphPanel extends JPanel implements KeyListener {

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
	private JCheckBox derivative;
	private boolean showDeriv;
	private Timer timer;
	
	public GraphPanel(String s) {
		timer = new Timer(2000, new MakeRed());
		timer.setRepeats(false);
		showDeriv = true;
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
		box.addKeyListener(this);
		box.setText(function);
		xmin = new JTextField();
		add(xmin);
		xmin.setSize(80, 50);
		xmin.setLocation(20, 50);
		xmin.setFont(new Font("Arial", Font.PLAIN, 50));
		xmin.addActionListener(new ChangeScale());
		xmin.setText((int)xlow + "");
		xmax = new JTextField();
		add(xmax);
		xmax.setSize(80, 50);
		xmax.setLocation(120, 50);
		xmax.setFont(new Font("Arial", Font.PLAIN, 50));
		xmax.addActionListener(new ChangeScale());
		xmax.setText((int)xhigh + "");
		ymin = new JTextField();
		add(ymin);
		ymin.setSize(80, 50);
		ymin.setLocation(1920, 50);
		ymin.setFont(new Font("Arial", Font.PLAIN, 50));
		ymin.addActionListener(new ChangeScale());
		ymin.setText((int)ylow + "");
		ymax = new JTextField();
		add(ymax);
		ymax.setSize(80, 50);
		ymax.setLocation(2020, 50);
		ymax.setFont(new Font("Arial", Font.PLAIN, 50));
		ymax.addActionListener(new ChangeScale());
		ymax.setText((int)yhigh + "");
		derivative = new JCheckBox("SHOW DERIVATIVE?", true);
		add(derivative);
		derivative.setLocation(240, 50);
		derivative.setFont(new Font("Arial", Font.PLAIN, 24));
		derivative.setSize(300, 50);
		derivative.setBackground(Color.BLACK);
		derivative.setForeground(Color.WHITE);
		derivative.addActionListener(new ToggleDeriv());
		// integral = new JButton("INTEGRAL");
		// add(integral);
		// integral.setSize(200, 50);
		// integral.setLocation(1650, 50);
	}

	private class ToggleDeriv implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showDeriv = !showDeriv;
			repaint();
		}
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
			g.drawLine((int)((i - xlow) / (xhigh - xlow) * getWidth()), 0, (int)((i - xlow) / (xhigh - xlow) * getWidth()), getHeight());
		}
		for (double i = ylow; i <= yhigh; i += (yhigh - ylow) / 20) {
			g.drawLine(0, getHeight() - (int)((i - ylow) / (yhigh - ylow) * getHeight()), getWidth(), getHeight() - (int)((i - ylow) / (yhigh - ylow) * getHeight()));
		}
		g.setColor(Color.WHITE);
		int yofx = getHeight() - (int)((-ylow) / (yhigh - ylow) * getHeight());
		int xofy = (int)((-xlow) / (xhigh - xlow) * getWidth());
		if (ylow < 0 && yhigh > 0) {
			g.drawLine(0, yofx, getWidth(), yofx);
		}
		if (xlow < 0 && xhigh > 0) {
			g.drawLine(xofy, 0, xofy, getHeight());
		}
		g.setFont(new Font("Arial", Font.PLAIN, 24));
		for (double i = xlow; i <= xhigh; i += (xhigh - xlow) / 30) {
			g.drawLine((int)((i - xlow) / (xhigh - xlow) * getWidth()), yofx - 10, (int)((i - xlow) / (xhigh - xlow) * getWidth()), yofx + 10);
			if (Math.abs(i) > 0.0000001 && Math.abs(i % 1) <= 0.001) g.drawString((int)i + "", (int)((i - xlow) / (xhigh - xlow) * getWidth()) - 10, yofx + 40);
		} 
		for (double i = ylow; i <= yhigh; i += (yhigh - ylow) / 20) {
			g.drawLine(xofy - 10, getHeight() - (int)((i - ylow) / (yhigh - ylow) * getHeight()), xofy + 10, getHeight() - (int)((i - ylow) / (yhigh - ylow) * getHeight()));
			if (Math.abs(i) > 0.0000001 && Math.abs(i % 1) <= 0.001) g.drawString((int)i + "", xofy - 40, getHeight() - (int)((i - ylow) / (yhigh - ylow) * getHeight()) + 10);
		}
		for (double i = 0; i < 2159; i += 0.01) {
			g.setColor(Color.GREEN);
			double y1 = ParseExpression.parse(function, xlow + (double)i / getWidth() * (xhigh - xlow));
			double y2 = ParseExpression.parse(function, xlow + (double)(i + 1) / getWidth() * (xhigh - xlow));
			if (y1 < yhigh && y1 > ylow && y2 < 1000 + yhigh && y2 > ylow - 1000) g.drawLine((int)Math.round(i), (int)Math.round(getHeight() * (yhigh - y1) / (yhigh - ylow)), (int)Math.round(i + 1), (int)Math.round(getHeight() * (yhigh - y2) / (yhigh - ylow)));
			if (showDeriv) {
				g.setColor(Color.RED);
				y1 = nDeriv(xlow + (double)i / getWidth() * (xhigh - xlow));
				y2 = nDeriv(xlow + (double)(i + 1) / getWidth() * (xhigh - xlow));
				if (y1 < yhigh && y1 > ylow && y2 < 1000 + yhigh && y2 > ylow - 1000) g.drawLine((int)Math.round(i), (int)Math.round(getHeight() * (yhigh - y1) / (yhigh - ylow)), (int)Math.round(i + 1), (int)Math.round(getHeight() * (yhigh - y2) / (yhigh - ylow)));
			}
		}
	}

	private double nDeriv(double val) {
		return (ParseExpression.parse(function, val + 0.0000001) - ParseExpression.parse(function, val - 0.0000001)) / 0.0000002;
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyChar()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_SPACE: return;
		}
		try {
			ParseExpression.parse(box.getText(), 0);
		} catch (Exception exc) {
			box.setForeground(Color.RED);
			if (!timer.isRunning()) timer.start();
			return;
		}
		if (timer.isRunning()) timer.stop();
		box.setForeground(Color.BLACK);
		function = box.getText();
		repaint();
	}
	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	private class MakeRed implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			box.setForeground(Color.BLACK);
		}
	}
}