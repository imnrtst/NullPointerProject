import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB 
{
	//------Statics-----//
	
	//Finals
	private static final String DB_USER = "sa";
	private static final String DB_PASS = "";
	private static final String DB_URL = "jdbc:h2:~/test";
	private static final String DB_DRIVER = "org.h2.Driver";
	
	//------Globals------//
	
	public boolean addToDb()
	{
		
	}
	public void testDbConnection()
	{
		Connection conn = null;
        try{
            //Choose a driver
            Class.forName(DB_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database --> " + getDbName() + "...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Connected database successfully...");
         }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
         }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
         }finally{
            //finally block used to close resources
            try{
               if(conn!=null)
                  conn.close();
            }catch(SQLException se){
               se.printStackTrace();
            }//end finally try
         }//end try
         System.out.println("Goodbye!");
	}
	
	
	//----------HELPERS--------------//
	/*
	 * THis method gets the name of the DB being connected to based on the value devclared in the
	 * static var DB_URL
	 */
	private String getDbName()
	{
		String[] splitUrl = DB_URL.split("/");
		return splitUrl[splitUrl.length -1];
	}

}