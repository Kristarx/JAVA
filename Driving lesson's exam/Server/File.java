import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class File 
{
	
	String num_of_task;
	String ques_A;
	String ques_B;
	String ques_C;
	String QueAndAns = "";
	String correct_ans = "";
	
	public String GetAns()
	{
		return correct_ans;
	}
	
	public synchronized String OpenFile()
	{
		Random rand = new Random(); 
	    int rand_num = rand.nextInt(7)+1;
	    
		try
		{
			BufferedReader read = new BufferedReader(new FileReader("C:/Users/g580/Documents/Programming/Java/Innne/Others/Taka Hilfe/takaHilfe/heh.txt"));
			String line = read.readLine();
			String num_of_ques = Integer.toString(rand_num) + ".";
			
			
			while(line != null)
			{
				if(line.equals(num_of_ques))
				{
					num_of_task = read.readLine();
					ques_A = read.readLine();
					ques_B = read.readLine();
					ques_C = read.readLine();
					correct_ans = read.readLine();	
					QueAndAns = num_of_task + "\n" + ques_A + "\n" + ques_B + "\n" + ques_C;
					System.out.println(QueAndAns);
					break;
				}
					
				line = read.readLine();
			}
			
			read.close();
			return QueAndAns;
		}	
		catch (IOException e) 
		{
			e.printStackTrace();
			return "";
		}		
	}
}
