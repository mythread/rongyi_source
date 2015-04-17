/**
 * 
 */
package base.util.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.BaseDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import base.util.crypt.MD5Encryption;

/**
 * @author ansenhuang
 * 
 */
@SuppressWarnings("deprecation")
public class UserPasswordEncoder extends BaseDigestPasswordEncoder implements PasswordEncoder {

	public String encodePassword(String rawPass, Object salt) throws DataAccessException {
		return MD5Encryption.encrypt(rawPass);

	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);
		return pass1.equals(pass2);
	}

	public static void main(String[] adfd) {
		UserPasswordEncoder user = new UserPasswordEncoder();
		// Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5Str = MD5Encryption.encrypt("admin");
		System.out.println(md5Str);
	}
}
