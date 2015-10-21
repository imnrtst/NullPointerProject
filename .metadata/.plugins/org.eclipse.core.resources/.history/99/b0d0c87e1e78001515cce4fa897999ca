import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Random;
import java.sql.*;

public class DBTest {
	   
	public static void main(String[] args) throws Exception
	{
		DB db = new DB();
		//Connection conn = db.openDBConnection();
		//db.closeDBConnection(conn);
		
		UserDataObj userData = new UserDataObj();
		userData.email = "someone@somewhere.com";
		userData.ccnum = "1234123412341234";
		userData.ccreg = true;
		db.addUserData(userData);
		ArrayList<UserDataObj> userDataList =  db.getUserData("someone@somewhere.com");
		while(!userDataList.isEmpty())
		{
			System.out.println(userDataList.remove(0).toString());
		}
	}
}
