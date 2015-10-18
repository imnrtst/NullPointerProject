import java.awt.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DB 
{
	//------Statics-----//
	
	//Finals
	private static final String TABLE_SQL = 
					"CREATE TABLE USERS " + 
					" (ID int NOT NULL AUTO_INCREMENT, " +
					" email VARCHAR(50), " + 
					" ccnum VARCHAR(16), " + 
					" ccreg BOOLEAN, " + 
					" PRIMARY KEY ( id ))";
	private static final String DB_USER = "sa";
	private static final String DB_PASS = "";
	private static final String DB_URL = "jdbc:h2:./ProjectDatabase";
	private static final String DB_DRIVER = "org.h2.Driver";
	
	private static final String LOGGER_NAME = "DBLog";
	private static final String LOGGER_FILE = "logs/DBLog.log";
	
	//------Globals------//
	private Logger logger = null;
	
	//-------Constructors------//
	public DB ()
	{
		initLogger();
		createTable();
	}
	
	//--------PUBLICS-----------//
	
	/*
	 * Method to create a DB if it does not already exist
	 * 
	 * @deprecated: the open connection  instruction will create the DB if not exists
	 */
	public boolean createDB()
	{
		boolean success = false;
		String dbName = getDbName();
		
		try
		{
			Connection conn = openDBConnection();
			Statement dbStatement = conn.createStatement();
			int sqlNum = dbStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName.toUpperCase());
			logger.info("DB Created: " + sqlNum);
			success = true;
		}
		catch(SQLException e)
		{
			logger.info("Error creating the database: \n" + e.getLocalizedMessage());
		}
		return success;
	}
	
	/*
	 * A method to create to add a table to the DB
	 * 
	 * @note: there should only be one table added but the method has been created such that it will take 
	 * 		a String as a parm and will create that table
	 */
	public boolean createTable()
	{
		boolean success = false;
		Connection conn = openDBConnection();
		
		try
		{
			Statement dbStatement = conn.createStatement();
			int sqlNum = dbStatement.executeUpdate(TABLE_SQL);
			logger.info("DB Table created. SQL execution returned code:" + sqlNum);
			success = true;
		} 
		catch (SQLException e) 
		{
			logger.info("Error creating the table: \n" + e.getLocalizedMessage());
		}
		finally
		{
			closeDBConnection(conn);
		}
		
		return success;
	}
	public boolean addToDb()
	{
		boolean success = false;
		
		
		
		return success;
	}
	
	public void testDbConnection()
	{
		//get the db name being connected to
		String dbName = getDbName();
		Connection conn = null;
        try
        {
            //Choose a driver
            Class.forName(DB_DRIVER);

            //Connect to DB
            logger.info("Connecting to DB --> " + dbName + "...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            
            //No SQL errors received so the DB is accessible
            logger.info("Connected to the " + dbName + " DB successfully...");
            
         }
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        finally
        {
            //finally block used to close resources
            try
            {
               if(conn!=null)
                  conn.close();
            }
            catch(SQLException se)
            {
               se.printStackTrace();
            }
         }
         logger.info("Connection to the " + dbName + " DB closed...");
         logger.info("DB connection test succesful.");
	}
	
	
	//----------HELPERS--------------//
	/*
	 * THis is a method to get the connection to a data base
	 */
	public Connection openDBConnection()
	{
		//get the db name being connected to
		String dbName = getDbName();
		Connection conn = null;
		try
		{
			//Choose a driver
		    Class.forName(DB_DRIVER);

		    //Connect to DB
		    logger.info("Connecting to DB --> " + dbName + "...");
		    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		            
		    //No SQL errors received so the DB is accessible
		    logger.info("Connected to the " + dbName + " DB successfully...");
		}
		
		catch(SQLException se)
		{
			//Handle errors for JDBC
            se.printStackTrace();
        }
		catch(Exception e)
		{
            //Handle errors for Class.forName
            e.printStackTrace();
        }
		
		return conn;
		//This portion will close the db connection
		/*
		finally
		{
            //finally block used to close resources
            try
            {
               if(conn!=null)
               {
            	   conn.close();
               }
            }
            catch(SQLException se)
            {
               se.printStackTrace();
            }
        }
		logger.info("Connection to the " + dbName + " DB closed...");
		logger.info("DB connection test succesful.");
		*/
	}
	
	public void closeDBConnection(Connection conn)
	{
		String dbName = getDbName();
		
		try
        {
           if(conn!=null)
           {
        	   conn.close();
           }
        }
        catch(SQLException se)
        {
           se.printStackTrace();
        }
		logger.info("Connection to the " + dbName + " DB closed...");
		logger.info("DB connection test succesful.");
	}
		          
	/*
	 * THis method gets the name of the DB being connected to based on the value devclared in the
	 * static var DB_URL
	 */
	private String getDbName()
	{
		String[] splitUrl = DB_URL.split("/");
		return splitUrl[splitUrl.length -1];
	}
	
	/*
	 * A method to initialize the logger for this class
	 * All output should go to the file specified in the LOGGER_FILE var
	 * Format for adding to logger file is as follows: logger.info("message")
	 */
	private void initLogger()
	{
		logger = Logger.getLogger(LOGGER_NAME);  
	    FileHandler fh;  

	    try 
	    {  
	        fh = new FileHandler(LOGGER_FILE);  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	        logger.info("<---Logging started!--->");
	    } 
	    catch (SecurityException e) 
	    {  
	        e.printStackTrace();  
	    } 
	    catch (IOException e) 
	    {  
	        e.printStackTrace();  
	    }    
	}
}