import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIClientSide implements Runnable{

	private JFrame frame;
	private static int checkConStatus = 0;
	private static int quesCount = 1;
	private static Socket clientSocket = null;
	private static PrintStream os = null;
	private static DataInputStream is = null;
	public static String ans = "";

	private static BufferedReader inputLine = null;
	private static boolean closed = false;
	private JTextField TextNameAndRes;
	private JTextField TextSurAndRes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIClientSide window = new GUIClientSide();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		int portNumber = 2222;
	    String host = "localhost";

	    if (args.length < 2) 
	    {
	    	System.out.println("Wielowatkowosc strona klienta. Host = " + host + ", numer portu = " + portNumber);
	    } 
	    else 
		{
		      host = args[0];
		      portNumber = Integer.valueOf(args[1]).intValue();
	    }

	    //Otwarcie socketu dla podanego hostu i portu.
	    //Otwarcie strumienia wejscia/wyjscia
	    
	    try 
	    {
		      clientSocket = new Socket(host, portNumber);
		      inputLine = new BufferedReader(new InputStreamReader(System.in));
		      os = new PrintStream(clientSocket.getOutputStream());
		      is = new DataInputStream(clientSocket.getInputStream());
		} 
	    catch (UnknownHostException e) 
	    {
	    	System.err.println("Nieznany host " + host);
	    } 
	    catch (IOException e) 
	    {
	    	System.err.println("Nie udalo siê nawiazac polaczenia " + host);
	    	checkConStatus = -1;
	    	JOptionPane.showMessageDialog(null, "Nie udalo siê nawiazac polaczenia!");
	    	System.exit(checkConStatus);
	    }
	    
	    //Jeœli wszystko zosta³o zainicjowane, mo¿emy zapisaæ 
	    //dane w sockecie, do którego otworzyliœmy po³¹czenie
	    //na porcie numer_portu
	    
	    if (clientSocket != null && os != null && is != null) 
	    {
	      try 
	      {

	    	//Tworzenie watku do czytania z serwera
	        new Thread(new GUIClientSide()).start();
	        while (!closed) 
	        {
	        	os.println(inputLine.readLine().trim());       
	        }

	        //zamkniecie strumienia wejscia/wyjscia i socketu
	        os.close();
	        is.close();
	        clientSocket.close();
	      } 
	      catch (IOException e) 
	      {
	        System.err.println("IOException:  " + e);
	      }
	    }
	}
	
	//Tworzenie watku do czytania z serwera
	  
	  public void run() 
	  {

		//Czytaj z socketa dopoki wiadomosc otrzymana z serwera
		//bedzie inna niz KONIEC. Po otrzymaniu klient 
		//przerywa dzialanie
		  
	    String responseLine;
	    try {
	      while ((responseLine = is.readLine()) != null) 
	      {
	        System.out.println(responseLine);
	        ans += responseLine;
	        if (responseLine.indexOf("*** Koniec") != -1)
	          break;
	        
	      }
	      closed = true;
	      
	    } catch (IOException e) {
	      System.err.println("IOException:  " + e);
	    }
	  }
	

	/**
	 * Create the application.
	 */
	public GUIClientSide() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 975, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JCheckBox AnsA = new JCheckBox("Answer A");
		AnsA.setVisible(false);
		AnsA.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JCheckBox AnsB = new JCheckBox("Answer B");
		AnsB.setVisible(false);
		AnsB.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JCheckBox AnsC = new JCheckBox("Answer C");
		AnsC.setVisible(false);
		AnsC.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton Button1 = new JButton("Start exam");
		
		Button1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel Label2Pic = new JLabel("");
		ImageIcon icon = new ImageIcon("C:/Users/g580/Documents/Programming/Java/Innne/Others/ClientSerwer/App GUI/Photos/rocket.jpg"); 
		Label2Pic.setIcon(icon);
		
		JTextPane Label1Text = new JTextPane();
		Label1Text.setBackground(UIManager.getColor("Button.background"));
		Label1Text.setFont(new Font("Tahoma", Font.PLAIN, 28));
		Label1Text.setFocusable(false);
		Label1Text.setEditable(false);
		Label1Text.setText("Hello in driving licence exam. To start test, please fill gaps. First gap is your name and second one is surname. Then click \"Start exam\". After exam you will immediately see your result and information if you pass an exam. Good luck!");
		
		TextNameAndRes = new JTextField();
		TextNameAndRes.setFont(new Font("Tahoma", Font.PLAIN, 24));
		TextNameAndRes.setFocusCycleRoot(true);
		TextNameAndRes.setColumns(10);
		
		TextSurAndRes = new JTextField();
		TextSurAndRes.setFont(new Font("Tahoma", Font.PLAIN, 24));
		TextSurAndRes.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(Label1Text, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
							.addComponent(Label2Pic, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(AnsA)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(AnsB)
										.addComponent(AnsC))
									.addGap(36)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(TextSurAndRes)
										.addComponent(TextNameAndRes, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))))
							.addPreferredGap(ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
							.addComponent(Button1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(95)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(Label1Text, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED, 87, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(Label2Pic, GroupLayout.PREFERRED_SIZE, 411, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(AnsA)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(35)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(TextNameAndRes, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
										.addComponent(AnsB))))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(TextSurAndRes, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(AnsC))))
						.addComponent(Button1, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(9, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
		
		AnsA.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				if(AnsB.isSelected() || AnsC.isSelected())
				{
					AnsA.setSelected(true);
					AnsB.setSelected(false);
					AnsC.setSelected(false);
				}
			}
		});
		
		AnsB.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(AnsA.isSelected() || AnsC.isSelected())
				{
					AnsA.setSelected(false);
					AnsB.setSelected(true);
					AnsC.setSelected(false);
				}
			}
		});
		
		AnsC.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(AnsA.isSelected() || AnsB.isSelected())
				{
					AnsA.setSelected(false);
					AnsB.setSelected(false);
					AnsC.setSelected(true);
				}
			}
		});
		
		
		Button1.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				if(Button1.getText().trim().equals("Start exam"))
				{
					if(TextNameAndRes.getText().trim().equals("") || TextSurAndRes.getText().trim().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Fill all gaps!");
					}
					else
					{
						System.out.println("1.");
						os.println(TextNameAndRes.getText() + " " + TextSurAndRes.getText());
						JOptionPane.showMessageDialog(null, "Hello " + TextNameAndRes.getText() + " " + TextSurAndRes.getText());
						Button1.setText("NEXT");
						Button1.setLocation(370, 390);
						Label1Text.setText("");
						
						setPicture();
						quesCount++;
						
						TextNameAndRes.setVisible(false);
						TextSurAndRes.setVisible(false);
						AnsA.setVisible(true);
						AnsB.setVisible(true);
						AnsC.setVisible(true);
						
					}
					
				}
				else if(!AnsA.isSelected() && !AnsB.isSelected() && !AnsC.isSelected())
				{
					
				}
				
				else if(quesCount <= 5)
				{	
					String userAns = corrAns();
					
					Label1Text.setText("");
					System.out.println(quesCount + ".");
					os.println(userAns);
					
					AnsA.setSelected(false);
					AnsB.setSelected(false);
					AnsC.setSelected(false);
					Label1Text.setText("");
					AnsA.setText("");
					AnsB.setText("");
					AnsC.setText("");
					
					moveOn();
					setPicture();
					quesCount++;
				}
				else
				{
					String userAns = corrAns();
					ans = null;	
					os.println(userAns);
					moveOn();					
					JOptionPane.showMessageDialog(null, ans.substring(4));
					System.exit(0);
				}
			}	
			private void moveOn()
			{
				try
				{
					TimeUnit.MILLISECONDS.sleep(20);
				} 
				catch (InterruptedException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			private String corrAns()
			{
				if(AnsA.isSelected())
				{
					return "a";
				}
				else if(AnsB.isSelected())
				{
					return "b";
				}
				else if(AnsC.isSelected())
				{
					return "c";
				}
				
				return "";
			}
			
			private void setPicture()
			{
				while((Label1Text.getText().equals("")))
				{
					String result[] = File.getDataForGUI(Integer.toString(quesCount) + ".");
					Label1Text.setText(result[0]);
					AnsA.setText(result[1]);
					AnsB.setText(result[2]);
					AnsC.setText(result[3]);
					
					try
					{
						ImageIcon icon = new ImageIcon("C:/Users/g580/Documents/Programming/Java/Innne/Others/ClientSerwer/App GUI/Photos/"+result[5]+".jpg"); 
						Label2Pic.setIcon(icon);
					}
					catch(Exception e1)
					{
						Label2Pic.setIcon(null);
					}	
				}
			}
		});	
	}
}
