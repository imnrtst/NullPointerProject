import java.util.ArrayList;

public class DBTest {
	   
	public static void main(String[] args) throws Exception
	{
		//Create a new DB obj so that we can interface with the DB
		DB db = new DB();
		
		//Create a user data obj 
		UserDataObj userData = new UserDataObj();
		
		//Populate user data in userDataObj to be added to DB
		userData.email = "someone@somewhere.com";
		userData.ccnum = "1234123412341234";
		
		//Pass the userDataObj to the DB handler to add the contained user info
		db.addUserData(userData);
		
		//Get user data that matches an email address
		ArrayList<UserDataObj> userDataList =  db.getUserData("someone@somewhere.com");
		
		//Display all results from the query 
		while(!userDataList.isEmpty())
		{
			System.out.println(userDataList.remove(0).toString());
		}
	}
}
