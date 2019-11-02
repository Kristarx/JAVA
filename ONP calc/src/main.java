import java.io.*;
import java.util.*;

public class main {

	public static void main(String[] args) throws IOException {
	    for (;;) {
	
	      Stack<String> elements = toONP();
	
	      try
	      {
	        double wynik = Wynik(elements);
	        if (!elements.empty())
	        	throw new Exception();
	        System.out.println(wynik);
	      }
	      catch (Exception e) {System.out.println("error");}
	    }
	}

	private static Stack<String> toONP() throws IOException
	{
	  BufferedReader wyrazenie = new BufferedReader(new InputStreamReader(System.in));
	  String s = wyrazenie.readLine();
	  char[] try1 = s.toCharArray();
	  s = "";

      for (int i = try1.length-1; i>=0; i--)
    	  s = s + try1[i];

	  Stack<String> stos = new Stack<String>();
	  Stack<String> wyjscie = new Stack<String>();
	  Stack<String> elements = new Stack<String>();
      elements.addAll(Arrays.asList(s.trim().split("[ \t]+")));

      for(int i = 0; i < elements.size(); i++)
      {
    	  String reverse = new String();
    	  for (int j = elements.get(i).length() - 1 ; j >= 0 ; j--)
    	         reverse = reverse + elements.get(i).charAt(j);
    	  elements.setElementAt(reverse, i);
      }


	  while(!elements.empty())
	  {
				  String pom = new String();
				  pom = elements.pop();
				  if(pom.equals("("))
				  {
					  stos.push("(");
				  }
				  else if(pom.equals(")"))
				  {
					  while(!stos.lastElement().equals("("))
					  {
						  if(!stos.lastElement().equals("("))
							  wyjscie.push(stos.pop());
						  else
							  stos.pop();
					  }
	
					  stos.pop();
				  }
				  else if(pom.equals("+") || pom.equals("-") || pom.equals("*") || pom.equals("/") || pom.equals("^"))
				  {
					  while(!stos.empty())
					  {
						  if(level(pom) == 3 || level(pom) > level(stos.lastElement()))
						  {
							  break;
						  }
	
						  wyjscie.push(stos.pop());
					  }
	
					  stos.push(pom);
				  }
	
				  else if(!pom.equals(" "))
					  wyjscie.push(pom);
		  }
	
		  while(!stos.empty())
		  {
			  String pom = new String();
			  pom = stos.pop();
			  wyjscie.push(pom);
		  }
	
		  String wynik = new String();
		  wynik = "";
	
		  for(int i = 0; i < wyjscie.size(); i++)
			  wynik = wynik + " " + wyjscie.get(i);
	
		  System.out.println(wynik);
		  return wyjscie;
	}

	private static double Wynik(Stack<String> elements) throws Exception {
	    String element = elements.pop();
	    double x,y;
	
	    try
	    {
	    	x = Double.parseDouble(element);
	    }
	    catch (Exception e)
	    {
		      y = Wynik(elements);
		      x = Wynik(elements);
	
		      if(element.equals("+"))
		    	  x += y;
		      else if (element.equals("-"))
		    	  x -= y;
		      else if (element.equals("*"))
		    	  x *= y;
		      else if (element.equals("/"))
		    	  x /= y;
		      else if (element.equals("^"))
		    	  x = Math.pow(x, y);
		      else throw new Exception();
	    }
	
	    return x;

	}

	private static int level(String pom)
	{
		  switch(pom)
		  {
		  		case "+":;
		  		case "-":  return 1;
		  		case "*":;
		  	    case "/":  return 2;
		  	    case "^":  return 3;
		  }
		  return 0;
	}
}