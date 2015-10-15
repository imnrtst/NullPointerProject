import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class TestDriver {

	public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, IOException 
	{
		
		String username = "dave1";
		String password = "abc123";
		
		System.out.println("Hash func integrity test passed? (" + testHashFunc("abc123") + ")");
		if(Login.verify_creds(username, password))
		{
			System.out.println("Match found!");
		}
		else
		{
			System.out.println("No match!");
		}
	}
	
	public static Boolean testHashFunc(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		if(PwGen.get_hash(password).compareTo(PwGen.get_hash(password)) == 0)
		{
			return true;
		}
		return false;
	}

}
