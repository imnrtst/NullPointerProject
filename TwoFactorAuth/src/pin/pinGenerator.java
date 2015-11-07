package pin;
// Generates the random pin for the user to enter.
import java.util.Random;

public class pinGenerator 
{

	public static int randomGen()
	{
		Random r = new Random (System.currentTimeMillis());
		return 1000 + r.nextInt(9999);
	}
	
	
}
