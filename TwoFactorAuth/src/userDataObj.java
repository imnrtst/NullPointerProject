
public class UserDataObj 
{
	//Datum
	public int id;
	public String email;
	public String password;
	public String ccnum;
	public boolean ccreg;
	public String ccpin;
	
	public UserDataObj()
	{
		initData();
	}
	
	private void initData()
	{
		id = -1;
		email = null;
		password = null;
		ccnum = null;
		ccreg = false;
		ccpin = null;
	}
	
	@Override
	public String toString()
	{	
		return "id:" + id + "|" + "email:" + email + "|" + "password:" + password + "|" + "ccnum:" + ccnum + "ccreg:" + ccreg + "|" + "ccpin:" + ccpin;
	}
}
