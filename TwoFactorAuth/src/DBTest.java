import java.sql.DriverManager;
import java.sql.*;

public class DBTest {
	   
	public static void main(String[] args) throws Exception
	{
		DB db = new DB();
		db.testDbConnection();
	}
	

}
