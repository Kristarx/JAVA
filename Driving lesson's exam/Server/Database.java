import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class Database 
{
	 Integer Id;
     String Pytanie;   
     String aOdp;
     String bOdp;
     String cOdp;
     String Poprawna = "";
     String QueAndAns = "";
     
     public String GetAns()
     {
    	 return Poprawna;
     }
     
     private synchronized int CheckIfAlreadyUsed(String name, HashMap<String, ArrayList<Integer>> BEZPOWTORZEN)
     {
    	 Random rand = new Random(); 
  	     boolean IfNotRepeated = true;
  	     int NrPytania = 1;
  	     int IfRepeated =0;
  	     
  	          
  	   for (Entry<String, ArrayList<Integer>> ee : BEZPOWTORZEN.entrySet()) 
  	   {
			if(ee.getKey().equals(name))
			{
				while(IfNotRepeated)
				{	
					NrPytania = rand.nextInt(9)+1;
					ArrayList<Integer> value = ee.getValue();
				    for(int i = 0; i < value.size(); i++)
				    {		
				    	System.out.println(i);
				    	if(NrPytania == value.get(i))
				    	{
				    		IfRepeated++;
				    	}
				    }
				    
				    if(IfRepeated != 0)
				    {
				    	IfRepeated = 0;
				    }
				    else
				    {
				    	IfNotRepeated = false;
				    	BEZPOWTORZEN.get(name).add(NrPytania);
			    		System.out.println(value);
				    }
				}	
			}    
		    //System.out.println(ee.getValue() + " " + ee.getKey());
		}
  	   
  	   return NrPytania;
  	     
     }
     
     public int GetId()
     {
    	 return Id;
     }
     
     public synchronized String GetDataFromDB(String name, HashMap<String, ArrayList<Integer>> BEZPOWTORZEN)
     {
 	    int NrPytania = CheckIfAlreadyUsed(name, BEZPOWTORZEN);
    	 
    	 try
 		{
 			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/egzaminprawojazdy?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
 			Statement stmt = con.createStatement();
 			String sql = "Select * from pytania WHERE id = " + NrPytania;
 			ResultSet rs = stmt.executeQuery(sql);
 			
 		     while(rs.next())
 		     {
 		        Id = rs.getInt("Id");
 		        Pytanie = rs.getString("Pytanie");	   
 		        aOdp = rs.getString("aOdp");
 		        bOdp = rs.getString("bOdp");
 		        cOdp = rs.getString("cOdp");
 		        Poprawna = rs.getString("Poprawna");
 		        QueAndAns = Id+Pytanie + "\n" + aOdp + "\n" + bOdp + "\n" + cOdp + "\n" + Poprawna;    
 		     }
 		     stmt.close();
 		     return QueAndAns;
 		}
 		catch(Exception e)
 		{
 			return "";
 		}
     }
     
     public synchronized void WriteToDB(String imie, String nazwisko, int wynik, boolean czyZdane)
     {
    	
 
    	 try 
    	 {
    		 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/egzaminprawojazdy?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
    		 Statement stmt = con.createStatement();

    		 stmt.executeUpdate("INSERT INTO `zdajacy`(Imie,Nazwisko,Wynik,CzyZdane) VALUE ('"+imie+"','"+nazwisko+"','"+wynik+"',"+czyZdane+")");
    		 System.out.println(imie + nazwisko + wynik + czyZdane);				
    	     con.close();
    	}

    	catch (SQLException ex) 
    	 {
    		System.out.println("Something goes wrong");
    	 } 
     }
}
