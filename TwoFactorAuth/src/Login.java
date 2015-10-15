import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


/**
 * A simple login utility.
 *
 * Usage: java Login [-u USERNAME]
 *
 * Options:
 *   -u USERNAME   Login with USERNAME
 *
 * Uses the contents of the file "shadow" in the same directory as this file to
 * authenticate a user. A message is displayed indicating if the authentication
 * was successful or not.
 *
 */
public class Login {

	private static Console con;
	public static String usage = ""
			+ "Usage: java login [-u USERNAME]\n"
			+ "\n"
			+ "Options:\n"
			+ "  -u USERNAME   Login with USERNAME\n"
			+ "\n"
			+ "Uses the contents of the file \"shadow\" in the same directory as this file to\n"
			+ "authenticate a user. A message is displayed indicating if the authentication\n"
			+ "was successful or not.\n";

	public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		con = System.console();
        if (con == null) {
            System.err.println("No console.");
            System.exit(1);
        }
		do_login(args);
	}

	/**
	 * Get the user's credentials, check if they are correct.
	 *
	 * @param args Array of command-line parameters.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void do_login(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		String un = null;

		// Validate the parameters
		if (args.length == 2 && args[0].equals("-u")) {
			un = args[1];
		}
		else if (args.length == 0) {
			un = get_username();
		}
		else {
			System.out.println(args.length);
			System.err.println(usage);
			System.exit(1);
		}

		String pw = String.copyValueOf(con.readPassword("Password: "));

		if (verify_creds(un, pw)) {
			System.out.println("\nYou have logged in successfully!\n");
		}
		else {
			System.out.println("\nIncorrect credentials.\n");
		}
	}

	/**
	 * Return the username entered by the user.
	 *
	 * @return The username.
	 */
	private static String get_username() {
		return con.readLine("Username: ");
	}

	/**
	 * Return true if the username/password pair are in the shadow file.
	 *
	 * @param username The username.
	 * @param password The password.
	 * @return True if the user is in the shadow file AND the password, when
	 * 	hashed, matches the entry for that user.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static boolean verify_creds(String username, String password) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		boolean credentials_valid = false;

		try (BufferedReader br = new BufferedReader(new FileReader("shadow"))) {
		    String line;
		    while ((line = br.readLine()) != null) 
		    {
		       if(line.contains(username))
		       {
		    	   //Split the line to obtain the hashed password in the shadow file
		    	   String[] strings = line.split(":");
		    	   //Derive the hash of the provided password
		    	   String hashedPass = PwGen.get_hash(password);
		    	   //COmpare the two and set true if they are the same (assumes password is second entry in split)
		    	   if(hashedPass.compareTo(strings[1]) == 0)
		    	   {
		    		   credentials_valid = true;
		    	   }
		    	   //Break condition as name was found in list, no need to keep searching
		    	   break;
		       }
		    }
		}
		return credentials_valid;
	}
}
