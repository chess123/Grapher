import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GraphPanel extends JPanel implements KeyListener, MouseMotionListener {

	private boolean showDeriv;
	private boolean first;
	private boolean validGraph;
	private int mouseX;
	private double xlow;
	private double xhigh;
	private double ylow;
	private double yhigh;
	private double resolution;
	public static double width;
	public static double height;
	private ArrayList<String> functions;
	private JTextField box;
	private JTextField xmin;
	private JTextField xmax;
	private JTextField ymin;
	private JTextField ymax;
	private JTextField resol;
	private JButton integral;
	private JCheckBox derivative;

	public GraphPanel(String s) {
		mouseX = -100;
		first = true;
		validGraph = true;
		showDeriv = true;
		resolution = 1;
		xlow = -15;
		xhigh = 15;
		ylow = -10;
		yhigh = 10;
		functions = new ArrayList<String>();
		functions.add(s);
		box = new JTextField();
		xmin = new JTextField();
		xmax = new JTextField();
		ymin = new JTextField();
		ymax = new JTextField();
		resol = new JTextField();
		derivative = new JCheckBox("SHOW DERIVATIVE?", true);
		setLayout(null);
		add(box);
		add(xmin);
		add(xmax);
		add(ymin);
		add(ymax);
		add(resol);
		add(derivative);
		xmin.addActionListener(new ChangeScale());
		xmax.addActionListener(new ChangeScale());
		ymin.addActionListener(new ChangeScale());
		ymax.addActionListener(new ChangeScale());
		resol.addActionListener(new ChangeResol());
		derivative.addActionListener(new ToggleDeriv());
		box.addKeyListener(this);
		addMouseMotionListener(this);
	}
	
	public void initialize() {
		// repaint();
		// System.out.println(getWidth() + "\t" + getHeight());
		width = getWidth();
		height = getHeight();
		box.setSize((int)(1000 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		box.setLocation((int)(580 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		box.setFont(new Font("Arial", Font.PLAIN, (int)(50 * getHeight() / 1384.0)));
		box.setText(functions.get(0));
		xmin.setSize((int)(80 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		xmin.setLocation((int)(20 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		xmin.setFont(new Font("Arial", Font.PLAIN, (int)(50 * getHeight() / 1384.0)));
		xmin.setText((int)xlow + "");
		xmax.setSize((int)(80 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		xmax.setLocation((int)(120 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		xmax.setFont(new Font("Arial", Font.PLAIN, (int)(50 * getHeight() / 1384.0)));
		xmax.setText((int)xhigh + "");
		ymin.setSize((int)(80 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		ymin.setLocation((int)(1920 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		ymin.setFont(new Font("Arial", Font.PLAIN, (int)(50 * getHeight() / 1384.0)));
		ymin.setText((int)ylow + "");
		ymax.setSize((int)(80 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		ymax.setLocation((int)(2020 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		ymax.setFont(new Font("Arial", Font.PLAIN, (int)(50 * getHeight() / 1384.0)));
		ymax.setText((int)yhigh + "");
		resol.setSize((int)(80 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		resol.setLocation((int)(80 * getWidth() / 2138.0), (int)(350 * getHeight() / 1384.0));
		resol.setFont(new Font("Arial", Font.PLAIN, (int)(50 * getHeight() / 1384.0)));
		resol.setText(resolution + "");
		derivative.setLocation((int)(240 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		derivative.setFont(new Font("Arial", Font.PLAIN, (int)(24 * getHeight() / 1384.0)));
		derivative.setSize((int)(300 * getWidth() / 2138.0), (int)(50 * getHeight() / 1384.0));
		derivative.setBackground(Color.BLACK);
		derivative.setForeground(Color.WHITE);
	}

	private class ChangeResol implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			resolution = Double.parseDouble(resol.getText());
			repaint();
		}
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
		try {
			paintMonkeys(g);
		} catch (Throwable e) {
			if (e instanceof BadParensException)
				validGraph = false;
		}
	}

	public void paintMonkeys(Graphics g) throws Throwable {
		super.paintComponent(g);
		if (getWidth() != width || getHeight() != height) {
			initialize();
		}
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
			g.fillRect(0, yofx, getWidth(), 5);
		}
		if (xlow < 0 && xhigh > 0) {
			g.fillRect(xofy, 0, 5, getHeight());
		}
		g.setFont(new Font("Arial", Font.PLAIN, (int)(24 * getWidth() / 2138.0)));
		for (double i = xlow; i <= xhigh; i += (xhigh - xlow) / 30) {
			g.drawLine((int)((i - xlow) / (xhigh - xlow) * getWidth()), yofx - 10, (int)((i - xlow) / (xhigh - xlow) * getWidth()), yofx + 10);
			if (Math.abs(i) > 0.0000001 && Math.abs(i % 1) <= 0.001) g.drawString((int)i + "", (int)((i - xlow) / (xhigh - xlow) * getWidth()) - 10, yofx + 40);
		} 
		for (double i = ylow; i <= yhigh; i += (yhigh - ylow) / 20) {
			g.drawLine(xofy - 10, getHeight() - (int)((i - ylow) / (yhigh - ylow) * getHeight()), xofy + 10, getHeight() - (int)((i - ylow) / (yhigh - ylow) * getHeight()));
			if (Math.abs(i) > 0.0000001 && Math.abs(i % 1) <= 0.001) g.drawString((int)i + "", xofy - 40, getHeight() - (int)((i - ylow) / (yhigh - ylow) * getHeight()) + 10);
		}
		if (validGraph) {
			for (String func : functions) {
				for (double i = 0; i < getWidth(); i += resolution) {
					g.setColor(Color.GREEN);
					double y1 = ParseExpression.parse(func, xlow + (double)i / getWidth() * (xhigh - xlow));
					double y2 = ParseExpression.parse(func, xlow + (double)(i + resolution) / getWidth() * (xhigh - xlow));
					if (Double.isNaN(y1) || Double.isNaN(y2) || Double.isInfinite(y1) || Double.isInfinite(y2)) continue;
					if (y1 < yhigh && y1 > ylow || y2 < yhigh && y2 > ylow) {
						g.drawLine((int)Math.round(i), (int)Math.round(getHeight() * (yhigh - y1) / (yhigh - ylow)), (int)Math.round(i + 1), (int)Math.round(getHeight() * (yhigh - y2) / (yhigh - ylow)));
						if (i == mouseX) {
							g.fillOval((int)Math.round(i) - 5, (int)Math.round(getHeight() * (yhigh - y1) / (yhigh - ylow)) - 5, 10, 10);
						}
					}
					double y3 = ParseExpression.parse(func, xlow + (double)(i - resolution) / getWidth() * (xhigh - xlow));
					if (y1 > y3 && y1 > y2 || y1 < y3 && y1 < y2) {
						g.setColor(Color.BLUE);
						g.fillOval((int)Math.round(i) - 5, (int)Math.round(getHeight() * (yhigh - y1) / (yhigh - ylow)) - 5, 10, 10);
					}
					if (showDeriv) {
						g.setColor(Color.RED);
						y1 = nDeriv(func, xlow + (double)i / getWidth() * (xhigh - xlow));
						y2 = nDeriv(func, xlow + (double)(i + resolution) / getWidth() * (xhigh - xlow));
						y3 = nDeriv(func, xlow + (double)(i - resolution) / getWidth() * (xhigh - xlow));
						if (y1 < yhigh && y1 > ylow || y2 < yhigh && y2 > ylow) {
							g.drawLine((int)Math.round(i), (int)Math.round(getHeight() * (yhigh - y1) / (yhigh - ylow)), (int)Math.round(i + 1), (int)Math.round(getHeight() * (yhigh - y2) / (yhigh - ylow)));
							if (i == mouseX) {
								g.fillOval((int)Math.round(i) - 5, (int)Math.round(getHeight() * (yhigh - y1) / (yhigh - ylow)) - 5, 10, 10);
							}
						}
					}
				}
			}
		}
		// if (mouseX > 0 && mouseX < getWidth()) {
		// 	g.setColor(Color.GREEN);
		// 	g.drawString("(" + (xlow + (double)mouseX / getWidth() * (xhigh - xlow)) + "," + ParseExpression.parse(function, (xlow + (double)mouseX / getWidth() * (xhigh - xlow))) + ")", (int)(50 * getWidth() / 2138.0), (int)(200 * getHeight() / 1384.0));
		// 	g.setColor(Color.RED);
		// 	g.drawString("(" + (xlow + (double)mouseX / getWidth() * (xhigh - xlow)) + "," + nDeriv((xlow + (double)mouseX / getWidth() * (xhigh - xlow))) + ")", (int)(50 * getWidth() / 2138.0), (int)(280 * getHeight() / 1384.0));
		// }
	}

	private double nDeriv(String s, double val) throws Throwable {
		return (ParseExpression.parse(s, val + 0.0000001) - ParseExpression.parse(s, val - 0.0000001)) / 0.0000002;
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyChar()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_SPACE: return;
		}
		String[] funcs = box.getText().split(";");
		functions.clear();
		for (String s : funcs) {
			functions.add(s);
		}
		try {
			for (String s : functions)
				ParseExpression.parse(s, 0);
		} catch (Throwable exc) {
			box.setForeground(Color.RED);
			validGraph = false;
			repaint();
			return;
		}
		validGraph = true;
		box.setForeground(Color.BLACK);
		// functions.set(0, box.getText());
		repaint();
	}
	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public void mouseMoved(MouseEvent e) {
		if (resolution < 0.5) return;
		mouseX = e.getX();
		repaint();
	}
	public void mouseDragged(MouseEvent e) {}
}