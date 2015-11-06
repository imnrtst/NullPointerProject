package classes;
import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Create a set of hashed passwords and save them to the file "shadow".
 */
public class PwGen {

	
	private static Console con;
	private static int num_pws;
	private static String[] password_hashes;
	private static String[] usernames;

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		con = System.console();
        if (con == null) {
            System.err.println("No console.");
            System.exit(1);
        }
		make_pw_list();
		save_hashes();
	}

	/**
	 * Print the hashes of plaintext passwords input by user.
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void make_pw_list() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		num_pws = Integer.parseInt(con.readLine("How many passwords will you enter? "));
		usernames = new String[num_pws];
		password_hashes = new String[num_pws];

		for (int i=0; i < num_pws; i++){
			System.out.println(String.format("\nPassword %d", i+1));
			usernames[i] = con.readLine("Username: ");
			password_hashes[i] = get_hash(String.copyValueOf(con.readPassword("Password: ")));
		}

		System.out.println("\n\nThe credentials you entered:");
		for (int i=0; i < num_pws; i++) {
			System.out.println(usernames[i] + ":" + password_hashes[i]);
		}
		System.out.println("\n");
	}

	/**
	 * Invoke a hash algorithm on the password, return the result.
	 *
	 * @param plain_password The password in plain text to be hashed.
	 * @return The hashed version of the password.
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static String get_hash(String plain_password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String hashed_password = "";

		//Ensure that the formating of the string does not contain unrecognized characters
		byte[] plainPasswordBytes = plain_password.getBytes("UTF-8");
		//Create a hash that uses MD5
		MessageDigest pwDigest = MessageDigest.getInstance("MD5");
		//Clear it in case of a multi-threaded environment
		pwDigest.reset();
		//Use 64 bit encoding to ensure no negative/signed values for the bytes
		byte[] hashedPasswordBytes16 = Base64.getEncoder().encode(plainPasswordBytes);
		
		//convert the base32 representation of the hash to base16(hex) 
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hashedPasswordBytes16.length; i++) 
        {
        	sb.append(Integer.toString((hashedPasswordBytes16[i] & 0xff) + 0x100, 16).substring(1));
        }
        hashed_password = sb.toString();
        
        //add 0's to pad the hex value(it may not be 32 chars by default and JTR is expecting 32 for MD5)
        while(hashed_password.length() < 32)
        {
        	hashed_password = "0" + hashed_password;
        }
		//Return the usable hash
		return hashed_password;
	}
	
	/**
	 * Save the users and hashes to file "shadow".
	 */
	private static void save_hashes() {
		PrintWriter fout = null;
		try 
		{
			fout = new PrintWriter(new FileWriter("shadow"));
			
			//Check to make sure counts match
			if(password_hashes.length == usernames.length && usernames.length == num_pws)
			{
				for(int i = 0; i < usernames.length; i++)
				{
					fout.println( usernames[i] + ":" + password_hashes[i] + ":" + i);
				}
			}
			//If counts do not match something is seriously wrong and length of arrays cannot be ensured.
			else
			{
				fout.println("Error: Number of passwords and usernames are not equal.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fout.close();
		}
	}
}
