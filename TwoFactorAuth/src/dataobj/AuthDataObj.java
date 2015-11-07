package dataobj;

public class AuthDataObj 
{
	public String purchEmail = null;
	public String authEmail = null;
	public String authPin = null;
	public int transID = -1;
	
	public String toString()
	{
		String entry = "";
		
		entry += "Transaction ID:" + transID + "|";
		entry += "Purchaser Email:" + purchEmail + "|";
		entry += "Auth Email:" + authEmail + "|";
		entry += "Auth Pin:" + authPin;
		
		return entry;
	}
	public String augmentedToString()
	{
		String entry = "";
		
		entry += "Transaction ID:" + transID + "|";
		entry += "Purchaser Email:" + purchEmail + "|";
		
		return entry;
	}
}
