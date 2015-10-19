
import java.util.Random;

public class pinGenerator 
{

	public int randomGen()
	{
		Random r = new Random (System.currentTimeMillis());
		return r.nextInt(9999);
	}
	
	
}
