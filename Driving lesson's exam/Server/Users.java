import java.util.ArrayList;
import java.util.HashMap;

public class Users 
{
	Serwer s = new Serwer();
	Integer wynik;
	
	public void UserInit(String name, ArrayList<String> ZDAJACY, HashMap<String, Integer> PUNKTY, HashMap<String, Integer> ILEPYTAN, HashMap<String, ArrayList<Integer>> BEZPOWTORZEN)
	{
	      ZDAJACY.add(name);
	      PUNKTY.put(name, 0);
	      ILEPYTAN.put(name, 0);
	      
	      if (BEZPOWTORZEN.get(name) == null) 
	      {
	    	  BEZPOWTORZEN.put(name, new ArrayList<Integer>());
		  }
	}
	
	public Integer RetWynik()
	{
		return wynik;
	}
	
	public void IfPassed(String name, HashMap<String, Integer> PUNKTY, HashMap<String, Integer> ILEPYTAN)
	{
        wynik = (int)((float)PUNKTY.get(name)/(float)ILEPYTAN.get(name)*100);

        if(wynik > 60)
        	System.out.println(wynik + " - zdany");
        else
        	System.out.println(wynik + " - niezdany");

	}
}
