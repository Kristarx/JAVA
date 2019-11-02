import javax.swing.JDialog;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

class KoniecGry extends JDialog {
	
	private JLabel imieTekst;
	private JTextField imie;
	private JLabel twojWynik;
	private JButton ok;
	
	public KoniecGry() {
		setTitle("Koniec Gry");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(270, 160);
		setModal(true);
		setLocation(new Point(310, 230));
		setResizable(false);
		
		imieTekst = new JLabel("Podaj imiê:");
		imieTekst.setBounds(45, 20, 65, 20);
		imieTekst.setBackground(Color.white);
		
		imie = new JTextField("");
		imie.setBounds(115, 20, 100, 20);
		
		twojWynik = new JLabel("Twój wynik: " + Widok.wynik);
		twojWynik.setBounds(80, 50, 110, 20);
		twojWynik.setBackground(Color.white);
				
		ok = new JButton("OK");
		ok.setBounds(110, 85, 50, 30);
		ok.setFocusable(false);
		ok.setMargin(new Insets(1,1,1,1));
		ok.addActionListener(new Koniec());
		
		add(ok);
		add(imieTekst);
		add(imie);
		add(twojWynik);
		
		setVisible(true);
	}
	
	public void zapiszWynik() {
		ArrayList<String> gracz = new ArrayList<String>();
		ArrayList<String> punkty = new ArrayList<String>();
		boolean b = true;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("plik.txt"));
			String tmp = "";
			
			while ((tmp = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(tmp);
								
				while (st.hasMoreTokens()) {
					String temp = st.nextToken();
					if (temp.equals(";")) {
						punkty.add(st.nextToken());
					}	
					else if (b) {
						gracz.add(temp);
						b = false;
					}	
				}
				b = true;
			}
			br.close();
		}
		catch (IOException ex) {
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("plik.txt"));
			boolean bool = false;
			
			int i = 0;
			for (int j = 0; j < 10; j++) {
				if ((!bool) && (Widok.wynik > Integer.parseInt(punkty.get(i)))) {
					bw.write(imie.getText() + " ; " + Widok.wynik);
					bool = true;
				}
				else {
					bw.write(gracz.get(i) + " ; " + punkty.get(i));
					++i;
				}
				bw.newLine();
			}
			
			bw.close();		
		}
		catch (IOException ex) {
		}
	}
	
	private class Koniec implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!(imie.getText().equals(""))) {
				if (Character.isLetterOrDigit((imie.getText()).charAt(0))) {
					zapiszWynik();
					Widok.wynik = 0;
					Snake.wynik.setText("0");
					dispose();
				}
				else {
					imie.setText("Anonim");
				}
			}
		}
	}
}
