import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.ServerSocket;

public class Serwer 
{
  private static ServerSocket serverSocket = null;
  private static Socket clientSocket = null;

  private static final int maxClientsCount = 20;//MAKSYMALNA ILOSC ZDAJACYCH
  private static final clientThread[] threads = new clientThread[maxClientsCount];

  public static void main(String args[]) 
  {
    int portNumber = 2222;
    if (args.length < 1) 
    {
    	System.out.println("Wielowatkowosc egzamin prawo jazdy. Numer portu wynosi =" + portNumber);
    } 
    else 
    {
    	portNumber = Integer.valueOf(args[0]).intValue();
    }

    
    try 
    {
    	serverSocket = new ServerSocket(portNumber);//proba otwarcia socket serwera dla potu 2222
    } 
    catch (IOException e) 
    {
    	System.out.println(e);
    }

    //tworzymy klienckiego socketa dla kazdego zdajacego i przypisujemy nowy watek
    
    while (true) 
    {
      try 
      {
        clientSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) 
        {
          if (threads[i] == null) 
          {
            (threads[i] = new clientThread(clientSocket, threads)).start();
            break;
          }
        }
        if (i == maxClientsCount) 
        {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Limit zdajacych");
          os.close();
          clientSocket.close();
        }
      } 
      catch (IOException e) 
      {
        System.out.println(e);
      }
    }
  }
}

//Klient otwiera strumienie wejscia/wyjscia, konkretny klient jest pytany o imie,
//jest w stanie informowac innych klientow o dolaczeniu nowego klienta, serwer 
//zbiera dane i moze rozeslac odebrane dane do wszystkich innych. Gdy klient
//sie wylogowuje to watek ten moze wszystkich informowac o tym fakcie

class clientThread extends Thread {

  static ArrayList<String> ZDAJACY = new ArrayList<String>(); //Nazwy zdajacych
  static HashMap<String, Integer> PUNKTY = new HashMap<String, Integer>(); //Przypisanie punktow po kazdym pytaniu dla konkretnego zdajacego
  static HashMap<String, Integer> ILEPYTAN = new HashMap<String, Integer>(); //Ile pytan minelo
  static HashMap<String, ArrayList<Integer>> BEZPOWTORZEN = new HashMap<String, ArrayList<Integer>>(); //Ile pytan minelo
  private DataInputStream is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final clientThread[] threads;
  private int maxClientsCount;

  public clientThread(Socket clientSocket, clientThread[] threads) //konstruktor
  {
	  this.clientSocket = clientSocket;
	  this.threads = threads;
	  maxClientsCount = threads.length;
  }

  public void run() 
  {
    int maxClientsCount = this.maxClientsCount;
    clientThread[] threads = this.threads;
    //File f = new File();
    Database db = new Database();
    Users u = new Users();
    Integer zmienna1, zmienna2;

    try 
    {
    	//tworzenie strumienia wejscia/wyjscia dla danego klienta
      is = new DataInputStream(clientSocket.getInputStream());
      os = new PrintStream(clientSocket.getOutputStream());
      //os.println("Podaj imie i nazwisko.");
      String name = is.readLine().trim();
	  //os.println(u.UserInit(name, ZDAJACY,PUNKTY, ILEPYTAN, BEZPOWTORZEN));
	  u.UserInit(name, ZDAJACY,PUNKTY, ILEPYTAN, BEZPOWTORZEN);
      os.println(db.GetDataFromDB(name, BEZPOWTORZEN));
      BEZPOWTORZEN.get(name).add(db.GetId());
      
      
      for(int j = 0; j < ZDAJACY.size(); j++)
      {
      	System.out.println(ZDAJACY.get(j));
      	zmienna1 = PUNKTY.get(ZDAJACY.get(j));
      	System.out.print(zmienna1);
      	System.out.println(" " + j + ZDAJACY.get(j));
      }
      
      for (int i = 0; i < maxClientsCount; i++) 
      {
        if (threads[i] != null && threads[i] != this) 
        {
          System.out.println("Zdajacy " + name + " zalogowal sie");
        }
      }
      while (true) 
      {
        String line = is.readLine();
        System.out.println(line + "\n" + name);
        
        System.out.println("Odpowiedz z baz danych: " + db.GetAns());
        
        if(line.equals(db.GetAns()))
        {
        	zmienna1 = PUNKTY.get(name);
        	zmienna2 = ILEPYTAN.get(name);
        	PUNKTY.replace(name, zmienna1+1);
        	ILEPYTAN.replace(name, zmienna2+1);
        }
        else
        {
        	zmienna1 = ILEPYTAN.get(name);
        	ILEPYTAN.replace(name, zmienna1+1);
        }
        
        
        if(line.startsWith("/SKONCZ") || ILEPYTAN.get(name) == 5) 
        {
        	u.IfPassed(name, PUNKTY, ILEPYTAN);
        	String sepName = "";
        	String surname = "";
        	int flag = 0, counter = 0;
        	for(int i = 0; i < name.length(); i++)
        	{
        		if(name.charAt(i) == ' ')
        		{
        			flag++;
        		}
        		
        		if(flag == 0)
        		{
        			sepName += name.charAt(i);
        		}
        		else
        		{
        			surname += name.charAt(i);
        		}
        		
        	}
        	
        	surname = surname.trim() + " ";
        	sepName = sepName.trim() + " ";
        	System.out.println("Imie: " + sepName + " i nazwisko: " + surname);
        	
        	if(u.RetWynik() > 60)
        	{
        		os.println("Gratulacje! Zdales egzamin z wynikiem " + u.RetWynik() +"%");
        		db.WriteToDB(sepName, surname, u.RetWynik(), true);
        	}    	
        	else
        	{
        		os.println("Niestety uzyskales " + u.RetWynik() + "% a tym samym nie zaliczyles egzaminu");
        		db.WriteToDB(sepName, surname, u.RetWynik(), false);
        	}
        		
        	break;
        }
        
        os.println(db.GetDataFromDB(name, BEZPOWTORZEN));
      //  os.println(db.GetAns());
        
        System.out.println("*********************");
        for(int j = 0; j < ZDAJACY.size(); j++)
        {
        	zmienna1 = PUNKTY.get(ZDAJACY.get(j));
        	System.out.print(zmienna1 + " " + ILEPYTAN.get(ZDAJACY.get(j)));
        	System.out.println(" " + ZDAJACY.get(j));
        }
        System.out.print("**********************");
        
        for (int i = 0; i < maxClientsCount; i++) 
        {
	          if (threads[i] != null) 
	          {
	            //threads[i].os.println("<" + name + ": " + line);
	        	  zmienna1 = PUNKTY.get(ZDAJACY.get(i));
	             //threads[i].os.println(zmienna1);
	          }
        }
      }
      for (int i = 0; i < maxClientsCount; i++) 
      {
	        if (threads[i] != null && threads[i] != this)
	        {
	          threads[i].os.println("***Uzytkownik " + name + " zakonczyl test ***");
	        }
      }
      //os.println("*** Koniec " + name + " ***");

      //Ustawienie null dla obecnego watku tak by nowy klient mogl byc
      //zaakceptowany przez serwer
      for (int i = 0; i < maxClientsCount; i++) 
      {
    	  if (threads[i] == this) 
    	  {
    		  threads[i] = null;
    	  }
      }

      //Zamkniecie strumienia wejscia/wyjscia oraz socketu
      is.close();
      os.close();
      clientSocket.close();
    } 
    catch (IOException e) 
    {
    	
    }
  }
}