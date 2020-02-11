import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class File 
{
	public static String[] getDataForGUI(String quesNum)
	{
		try
		{
			BufferedReader read = new BufferedReader(new FileReader("C:/Users/g580/Documents/Programming/Java/Innne/Others/ClientSerwer/App GUI/yyyy.txt"));
			String line = read.readLine();
			String Id = "", Ques = "", ques_A = "", ques_B = "", ques_C = "", correct_ans = "";
			
			
			while(line != null)
			{
				if(line.equals(quesNum))
				{
					Ques = read.readLine();
					ques_A = read.readLine();
					ques_B = read.readLine();
					ques_C = read.readLine();
					correct_ans = read.readLine();

					while(Ques.charAt(0) >= '0' && Ques.charAt(0) <= '9')
					{
						Id += Ques.charAt(0);
						Ques = Ques.substring(1);
					}
					break;
				}
					
				line = read.readLine();
			}
			read.close();
			return new String[] {Ques, ques_A, ques_B, ques_C, correct_ans, Id};
		}	
		catch (IOException e) 
		{
			e.printStackTrace();
			return new String[] {""};
		}		
	}
}
