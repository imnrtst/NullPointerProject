package database;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import dataobj.AuthDataObj;
import dataobj.UserDataObj;

public class DB 
{
	//------Statics-----//
	
	//Finals
	private static final String DB_NAME = "ProjectDB";
	private static final String DB_USER = "sa";
	private static final String DB_PASS = "";
	private static final String DB_URL = "jdbc:h2:./" + DB_NAME;
	private static final String DB_DRIVER = "org.h2.Driver";
	
	private static final String USER_TABLE_NAME= "Userdata";
	private static final String AUTH_TABLE_NAME= "Authdata";
	
	private static final String LOGGER_NAME = "DBLog";
	private static final String LOGGER_FILE = "logs/DBLog.log";
	
	//SQL Queries that should NOT be changed
	private static final String USER_TABLE_SQL = 
			"CREATE TABLE " + USER_TABLE_NAME  + 
			" (id INT NOT NULL AUTO_INCREMENT, " +
			" email VARCHAR(50) NOT NULL UNIQUE, " + 
			" password VARCHAR(100) NOT NULL," +
			" ccnum VARCHAR(16), " + 
			" ccreg BOOLEAN, " +
			" ccpin VARCHAR(10)," +
			" PRIMARY KEY ( id ))";
	
	private static final String AUTH_TABLE_SQL = 
			"CREATE TABLE " + AUTH_TABLE_NAME + 
			" (id INT NOT NULL AUTO_INCREMENT, " +
			" purchEmail VARCHAR(50) NOT NULL, " + 
			" authEmail VARCHAR(50) NOT NULL, " + 
			" authPin VARCHAR(10)," +
			" PRIMARY KEY ( id ))";
	
	
	
	
	//------Globals------//
	private Logger logger = null;
	
	//-------Constructors------//
	public DB ()
	{
		initLogger();
		createTable(USER_TABLE_SQL);
		createTable(AUTH_TABLE_SQL);
	}
	
	//--------PUBLICS-----------//
	
	/*
	 * Method to create a DB if it does not already exist
	 * THis method is deprecated as the connection will create the DB if it does
	 * not already exist
	 */
	@Deprecated
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
	 * Note: there should only be one table added but the method has been created such that it will take 
	 * a String (SQL create table statement) as a parm and will create the table
	 */
	private boolean createTable(String sql)
	{
		boolean success = false;
		Connection conn = openDBConnection();
		int sqlNum = 0;
		try
		{
			//Create a statement handler for the SQL statement
			Statement dbStatement = conn.createStatement();
			//Execute SQL statement and store the SQL code returned for debugging purposes
			sqlNum = dbStatement.executeUpdate(sql);
			logger.info("DB Table created. SQL execution returned code:" + sqlNum);
			success = true;
		} 
		catch (SQLException e) 
		{
			//Log the error and return code
			logger.info("Error creating the table (SQL Code: " + sqlNum + "): \n" + e.getLocalizedMessage());
		}
		finally
		{
			//Always close your connections or face the wrath of code -502!!!
			closeDBConnection(conn);
		}
		return success;
	}
	
	/*
	 * A method to add a user to the DB. The method takes a UserDataObj as a param and returns a boolean
	 * regarding success of the operation
	 */
	public boolean addUserData(UserDataObj userData)
	{
		boolean success = false;
		Connection conn = openDBConnection();
		int sqlNum = 0;
		
		if(checkCCNum(userData.ccnum))
		{
			userData.ccreg = false;
		}
		else
		{
			userData.ccreg = true;
		}
		//SQL update statement to add a user to the default table
		String query = "INSERT INTO " + USER_TABLE_NAME + " (email, password, ccnum, ccreg)" +
						"VALUES ('" + userData.email + "', '" + userData.password + "', '" + userData.ccnum + "', " + userData.ccreg + ")";
		try
		{
			Statement dbStatement = conn.createStatement();
			sqlNum= dbStatement.executeUpdate(query);
			logger.info("DB: User " + userData.email + " added to the DB (SQL: " + sqlNum + ")");
			success = true;
		} 
		catch (SQLException e) 
		{
			logger.info("Error adding user " + userData.email + ": \n" + e.getLocalizedMessage());
		}
		finally
		{
			closeDBConnection(conn);
		}
		
		return success;
	}
	
	public boolean addAuthData(AuthDataObj ado)
	{
		boolean success = false;
		Connection conn = openDBConnection();
		int sqlNum = 0;
		
		//SQL update statement to add a user to the default table
		String query = "INSERT INTO " + AUTH_TABLE_NAME + " (purchEmail, authEmail, authPin)" +
						"VALUES ('" + ado.purchEmail + "', '" + ado.authEmail + "', '" + ado.authPin + "')";
		try
		{
			Statement dbStatement = conn.createStatement();
			sqlNum= dbStatement.executeUpdate(query);
			logger.info("DB: Auth entry added to the DB (SQL: " + sqlNum + ")");
			success = true;
		} 
		catch (SQLException e) 
		{
			logger.info("Error adding auth entry to the DB");
		}
		finally
		{
			closeDBConnection(conn);
		}
		
		return success;
	}
	
	public boolean delAuthData(int id)
	{
		boolean success = false;
		Connection conn = openDBConnection();
		int sqlNum = 0;
		
		//SQL update statement to add a user to the default table
		String query = "DELETE FROM " + AUTH_TABLE_NAME + " WHERE id = " + id + ";";
		try
		{
			Statement dbStatement = conn.createStatement();
			sqlNum= dbStatement.executeUpdate(query);
			logger.info("DB: Auth entry added to the DB (SQL: " + sqlNum + ")");
			success = true;
		} 
		catch (SQLException e) 
		{
			logger.info("Error adding auth entry to the DB");
		}
		finally
		{
			closeDBConnection(conn);
		}
		
		return success;
	}
	public ArrayList<AuthDataObj> getAuthData(AuthDataObj ado)
	{
		//Var to store returned UserDataObjs
		ArrayList<AuthDataObj> authData = null;
		ResultSet rs = null;
		Connection conn = openDBConnection();
		//SQL query
		String query = 	"SELECT * " +
						"FROM " + AUTH_TABLE_NAME +
						" WHERE purchEmail = '" + ado.purchEmail + "'" +
						" AND authEmail = '" + ado.authEmail + "'" +
						" AND authPin = '" + ado.authPin + "'" ;
		try
		{
			Statement dbStatement = conn.createStatement();
			//Queries return result sets, so store the result in a result set.  What a concept!
			rs = dbStatement.executeQuery(query);
			logger.info("DB: Entry found. Returning auth data.");
			authData = packageAuthData(rs);
		} 
		catch (SQLException e) 
		{
			logger.info("Error during auth query: \n" + e.getLocalizedMessage());
		}
		finally
		{
			closeDBConnection(conn);
		}
		return authData;
	}
	
	public ArrayList<AuthDataObj> getAllAuthData(AuthDataObj ado)
	{
		//Var to store returned UserDataObjs
		ArrayList<AuthDataObj> authData = null;
		ResultSet rs = null;
		Connection conn = openDBConnection();
		//SQL query
		String query = 	"SELECT * " +
						"FROM " + AUTH_TABLE_NAME +
						" WHERE authEmail = '" + ado.authEmail + "'";
		try
		{
			Statement dbStatement = conn.createStatement();
			//Queries return result sets, so store the result in a result set.  What a concept!
			rs = dbStatement.executeQuery(query);
			logger.info("DB: Entry found. Returning auth data.");
			authData = packageAuthData(rs);
		} 
		catch (SQLException e) 
		{
			logger.info("Error during auth query: \n" + e.getLocalizedMessage());
		}
		finally
		{
			closeDBConnection(conn);
		}
		return authData;
	}
	/*
	 * This class takes an email for a user and returns all instances of that user from the DB
	 * in the form of an array list of UserDataObj
	 */
	public ArrayList<UserDataObj> getUserData(String email)
	{
		//Var to store returned UserDataObjs
		ArrayList<UserDataObj> userData = null;
		ResultSet rs = null;
		Connection conn = openDBConnection();
		//SQL query
		String query = 	"SELECT * " +
						"FROM " + USER_TABLE_NAME +
						" WHERE email = '" + email + "'";
		try
		{
			Statement dbStatement = conn.createStatement();
			//Queries return result sets, so store the reult in a result set.  What a concept!
			rs = dbStatement.executeQuery(query);
			logger.info("DB: User " + email + " found. Returning user data.");
			userData = packageUserData(rs);
		} 
		catch (SQLException e) 
		{
			logger.info("Error during user query: \n" + e.getLocalizedMessage());
		}
		finally
		{
			closeDBConnection(conn);
		}
		return userData;
	}
	
	public ArrayList<UserDataObj> getUserDataCCnum(String ccnum)
	{
		//Var to store returned UserDataObjs
		ArrayList<UserDataObj> userData = null;
		ResultSet rs = null;
		Connection conn = openDBConnection();
		//SQL query
		String query = 	"SELECT * " +
						"FROM " + USER_TABLE_NAME +
						" WHERE ccnum = '" + ccnum + "' AND ccreg = TRUE";
		try
		{
			Statement dbStatement = conn.createStatement();
			//Queries return result sets, so store the reult in a result set.  What a concept!
			rs = dbStatement.executeQuery(query);
			logger.info("DB: ccnum " + ccnum + " found. Returning user data.");
			userData = packageUserData(rs);
		} 
		catch (SQLException e) 
		{
			logger.info("Error during  ccnum query: \n" + e.getLocalizedMessage());
		}
		finally
		{
			closeDBConnection(conn);
		}
		return userData;
	}
	public ArrayList<UserDataObj> getAllUsers()
	{
		//Var to store returned UserDataObjs
		ArrayList<UserDataObj> userData = null;
		ResultSet rs = null;
		Connection conn = openDBConnection();
		//SQL query
		String query = 	"SELECT * " +
						"FROM " + USER_TABLE_NAME;
		try
		{
			Statement dbStatement = conn.createStatement();
			//Queries return result sets, so store the reult in a result set.  What a concept!
			rs = dbStatement.executeQuery(query);
			logger.info("DB: Entries found. Returning user data.");
			userData = packageUserData(rs);
		} 
		catch (SQLException e) 
		{
			logger.info("Error querying table: \n" + e.getLocalizedMessage());
		}
		finally
		{
			closeDBConnection(conn);
		}
		return userData;
	}
	/*
	 * A method to update a user's data
	 */
	public boolean updateUserData(String email, UserDataObj userData, String oldCCNum)
	{
		boolean success = false;
		int sqlNum = 0;
		Connection conn = openDBConnection();
		
		if(checkCCNum(userData.ccnum))
		{
			if(userData.ccnum.equals(oldCCNum) && userData.ccreg)
			{
				userData.ccreg = true;
			}
			else
			{
				userData.ccreg = false;
			}
		}
		else
		{
			userData.ccreg = true;
		}
		
		String query = 	" UPDATE " + USER_TABLE_NAME +
				" SET email = '" + userData.email + "'," +
				" password = '" + userData.password + "'," +
				" ccnum = '" + userData.ccnum + "'," +
				" ccreg = '" + userData.ccreg + "'," +
				" ccpin = '" + userData.ccpin + "'" +
				" WHERE email = '" + email + "';";
		
		try
		{
			Statement dbStatement = conn.createStatement();
			sqlNum= dbStatement.executeUpdate(query);
			logger.info("DB: User " + email + " entry updated (SQL: " + sqlNum + ")");
			success = true;
		} 
		catch (SQLException e) 
		{
			logger.info("Error updating user " + email + ": \n" + e.getLocalizedMessage());
		}
		finally
		{
			closeDBConnection(conn);
		}
		return success;
	}
	
	/*
	 * A method to check if a ccnum already exists in the DB
	 */
	public boolean checkCCNum(String ccnum)
	{
		boolean exists = false;
		int count = 0;
		ResultSet rs = null;
		Connection conn = openDBConnection();
		//SQL query
		String query = 	" SELECT COUNT (*) as cccount" +
						" FROM " + USER_TABLE_NAME +
						" WHERE ccnum = '" + ccnum + "'";
		try
		{
			Statement dbStatement = conn.createStatement();
			rs = dbStatement.executeQuery(query);
			rs.next();
			count = rs.getInt("cccount");
		} 
		catch (SQLException se) 
		{
			logger.info( se.getLocalizedMessage());
		}
		finally
		{
			closeDBConnection(conn);
		}
		
		if(count > 0)
		{
			logger.info("DB: Credit card " + ccnum + " already registered to a user" );
			exists = true;
		}
		return exists;
	}
	
	/*
	 * THis is a method to get a connection to a data base.  If the connection is already op
	 * an error is logged and the connection returned is null
	 */
	public Connection openDBConnection()
	{
		Connection conn = null;
		try
		{
			//Choose a driver
		    Class.forName(DB_DRIVER);

		    //Connect to DB
		    logger.info("Connecting to DB --> " + DB_NAME + "...");
		    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		            
		    //No SQL errors received so the DB is accessible
		    logger.info("Connected to the " + DB_NAME + " DB successfully...");
		}
		
		catch(SQLException se)
		{
			//Handle errors for JDBC/SQL
			logger.info("Error opening DB: \n" + se.getLocalizedMessage());
        }
		catch(Exception e)
		{
            //Handle all other errors
			logger.info("Something went VERY wrong when openning a connection to the DB");
            e.printStackTrace();
        }
		return conn;
	}
	
	/*
	 * This method closes the DB connection and logs it.
	 */
	public void closeDBConnection(Connection conn)
	{	
		try
        {
           if(conn!=null)
           {
        	   conn.close();
           }
        }
        catch(SQLException se)
        {
        	se.getLocalizedMessage();
        }
		logger.info("Connection to the " + DB_NAME + " DB closed...");
	}
	
	
	//----------HELPERS--------------//
		          
	/*
	 * THis method gets the name of the DB being connected to based on the value devclared in the
	 * static var DB_URL
	 */
	@Deprecated
	/*
	 * This method is no longer required as the DM name needed to be set as a static for reuse
	 */
	private String getDbName()
	{
		String[] splitUrl = DB_URL.split("/");
		return splitUrl[splitUrl.length -1];
	}
	
	/*
	 * A method to initialize the logger for this class
	 * All output should go to the file specified in the LOGGER_FILE var except major errors
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
	
	/*
	 * A method for packaging the data being returned to the user
	 * Takes a result set from a SQL query and returns an ArrayList of UserDataObjs
	 * If result set is empty userData will be null
	 */
	private ArrayList<UserDataObj> packageUserData(ResultSet rs)
	{
		ArrayList<UserDataObj> allUsersData = new ArrayList<UserDataObj>();
		UserDataObj userData = null;
		int recCount = 0;

		logger.info("Getting user data from the query results");
		
		
		try {
			while(rs.next())
			{
				userData = new UserDataObj();
				userData.id = rs.getInt("id");
				userData.email = rs.getString("email");
				userData.password = rs.getString("password");
				userData.ccnum = rs.getString("ccnum");
				userData.ccreg = rs.getBoolean("ccreg");
				userData.ccpin = rs.getString("ccpin");
				
				allUsersData.add(userData);
				recCount++;
				logger.info(userData.toString());
			}
			rs.close();
		} 
		catch (SQLException e) 
		{
			logger.info("Error getting user data: \n" + e.getLocalizedMessage());
		}
		catch (NullPointerException np)
		{
			logger.info("Error getting user data: \n" + np.getLocalizedMessage());
		}
		logger.info("Done getting user data from the query results\n" + recCount + " records transcribed");
		return allUsersData;
	}
	
	private ArrayList<AuthDataObj> packageAuthData(ResultSet rs)
	{
		ArrayList<AuthDataObj> allAuthData = new ArrayList<AuthDataObj>();
		AuthDataObj authData = null;
		int recCount = 0;

		logger.info("Getting auth data from the query results");
		
		try {
			while(rs.next())
			{
				authData = new AuthDataObj();
				authData.transID = rs.getInt("id");
				authData.purchEmail = rs.getString("purchEmail");
				authData.authEmail = rs.getString("authEmail");
				authData.authPin = rs.getString("authPin");
				
				allAuthData.add(authData);
				recCount++;
				logger.info(authData.toString());
			}
			rs.close();
		} 
		catch (SQLException e) 
		{
			logger.info("Error getting auth data: \n" + e.getLocalizedMessage());
		}
		catch (NullPointerException np)
		{
			logger.info("Error getting auth data: \n" + np.getLocalizedMessage());
		}
		logger.info("Done getting auth data from the query results\n" + recCount + " records transcribed");
		return allAuthData;
	}
	
	//----------------TEST METHODS----------------//
	
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
	
	
}