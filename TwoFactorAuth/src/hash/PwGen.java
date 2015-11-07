package hash;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Create a set of hashed passwords and save them to the file "shadow".
 */
public class PwGen {
	

	/**
	 * Invoke a hash algorithm on the password, return the result.
	 *
	 * @param plain_password The password in plain text to be hashed.
	 * @return The MD5 hashed version of the password in a hexadecimal representation
	 */
	public static String get_hash(String plain_password)
	{
		//Use the apache commons codec library to create a hex Md5 hash of the plain text password.
		return DigestUtils.md5Hex(plain_password);
	}
	
}
