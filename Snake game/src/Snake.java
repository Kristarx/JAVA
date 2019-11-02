import javax.swing.JFrame;
import java.awt.Point;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Snake extends JFrame {
	
	static JButton start;
	static JButton wyniki;
	private JButton zakoncz;
	private JLabel wynikTekst;
	static JLabel wynik;
	private JLabel obraz;
	
	public Snake() {
		
		super("Snake");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setLocation(new Point(240, 120));
		
		Insets insets = new Insets(1,1,1,1);
		
		start = new JButton("Start");
		start.setMargin(insets);
		start.setFocusable(false);
		start.setBounds(407, 90, 80, 30);
		start.addActionListener(new Start());
		
		wyniki = new JButton("Wyniki");
		wyniki.setMargin(insets);
		wyniki.setFocusable(false);
		wyniki.setBounds(407, 130, 80, 30);
		wyniki.addActionListener(new WynikiZ());
		
		zakoncz = new JButton("Zakoñcz");
		zakoncz.setMargin(insets);
		zakoncz.setFocusable(false);
		zakoncz.setBounds(407, 170, 80, 30);
		zakoncz.addActionListener(new Zakoncz());
		
		wynikTekst = new JLabel("Wynik:");
		wynikTekst.setBounds(406, 30, 40, 30);
		
		wynik = new JLabel("0");
		wynik.setBounds(450, 30, 42, 30);
		
		obraz = new JLabel(new ImageIcon("waz.jpg"));
		obraz.setBounds(401, 210, 92, 161);
		
		Widok widok = new Widok();
		widok.setBounds(0, 0, 400, 372);
		
		add(widok);
		add(start);
		add(wyniki);
		add(wynikTekst);
		add(zakoncz);
		add(obraz);
		add(wynik);
		
		setVisible(true);
	}
	
	private class Zakoncz implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Widok.timer.stop();
			dispose();
		}
	}
	
	private class Start implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			start.setEnabled(false);
			wyniki.setEnabled(false);
			Widok.timer.start();
		}
	}
	
	private class WynikiZ implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OknoWyniki ow = new OknoWyniki();
		}
	}
	
	public static void main(String[] args) {
		new Snake();
	}
}