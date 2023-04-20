package password;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class PasswordManager {
	private ArrayList<Password> pwdList;
	private String algorithm;
	
	private static final String[] ALGORITHMS = {"AES", "CBC", "PKCS5Padding"};
	
	public static boolean isInAlgorithms(String str) {
		for(String s : ALGORITHMS) {
			if(s.equals(str)) { 
				return true;
			}
		}
		
		return false;
	}
	
	public PasswordManager(String algo) throws InvalidAlgorithm {
		if(!isInAlgorithms(algo)) {
			throw new InvalidAlgorithm(algo + " isn't a valid encryption algorithm");
		}
		this.algorithm = algo;
		this.pwdList = new ArrayList<Password>();
	}
	
	public String addPsw(Password pwd, String key) {
		try {
			String encriptedPwd = encrypt(pwd.getBody(), generateSecretKey(key));
			pwdList.add(new Password(pwd.getTitle(), encriptedPwd, pwd.getPath()));
			
			return encriptedPwd;
		} 
		catch (Exception e) {
			return null;
		}
	}
	
	private SecretKeySpec generateSecretKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256); // AES-256
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] key = f.generateSecret(spec).getEncoded();
		
		return new SecretKeySpec(key, "AES");
	}
	
	private String encrypt(String valueToEnc, Key key) throws Exception {
		Cipher c = Cipher.getInstance(algorithm);
		c.init(Cipher.ENCRYPT_MODE, key);
		   
		byte[] encValue = c.doFinal(valueToEnc.getBytes());
		  
		String s = Base64.getEncoder().encode(encValue).toString();
		return s;
	}
	
	private String decrypt(String encryptedValue, Key key) throws Exception {
		Cipher c = Cipher.getInstance(algorithm);
		c.init(Cipher.DECRYPT_MODE, key);
		
		
		byte[] decordedValue = Base64.getDecoder().decode(encryptedValue); 
		byte[] decValue = c.doFinal(decordedValue);
		    
		return new String(decValue);
	}
}
