
public class userDataObj 
{
	//Datum
	public int id;
	public String email;
	public String ccnum;
	public boolean ccreg;
	
	public userDataObj()
	{
		initData();
	}
	
	private void initData()
	{
		id = -1;
		email = null;
		ccnum = null;
		ccreg = false;
	}
}
