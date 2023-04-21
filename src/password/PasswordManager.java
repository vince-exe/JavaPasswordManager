package password;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class PasswordManager {
	private ArrayList<Password> pwdList;
	private final static String ALGORITHM = "AES/CBC/PKCS5Padding";
	
	public PasswordManager()  {
		this.pwdList = new ArrayList<Password>();
	}
	
	public String addPsw(Password pwd, String password) {
		try {
			IvParameterSpec ivParameterSpec = generateIv();
			SecretKey k = getKeyFromPassword(password, Integer.toString(password.length()));
			
			String encriptedPwd = encrypt_(ALGORITHM, pwd.getBody(), k, ivParameterSpec);
			pwdList.add(new Password(pwd.getTitle(), encriptedPwd, pwd.getPath()));
			
			return encriptedPwd;
		} 
		catch (Exception e) {
			return null;
		}
	}
	
	public String decrypt(String encriptedText, String password) {
		try {
			IvParameterSpec iv = generateIv();
			SecretKey key = getKeyFromPassword(password, Integer.toString(password.length()));
			
			return decrypt_(ALGORITHM, encriptedText, key, iv);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private IvParameterSpec generateIv() {
	    byte[] iv = new byte[16];
	    new SecureRandom().nextBytes(iv);
	    
	    return new IvParameterSpec(iv);
	}
	
	public SecretKey getKeyFromPassword(String password, String salt) {
		try {
		    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
		    
		    return secret;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	private static String encrypt_(String algorithm, String input, SecretKey key, IvParameterSpec iv) {
		try {
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		    
		    byte[] cipherText = cipher.doFinal(input.getBytes());
		    
		    return Base64.getEncoder().encodeToString(cipherText);
		}
		catch(Exception e) {
			return null;
		}
	}
	
	private static String decrypt_(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv)  {
		try {
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.DECRYPT_MODE, key, iv);
		    
		    byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		    
		    return new String(plainText);
		}
		catch(Exception e) {
			return null;
		}
	}
}
