package password;

import java.security.Key;
import java.util.ArrayList;

import javax.crypto.Cipher;
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
	
	private String encrypt(String valueToEnc, Key key) throws Exception {
		Cipher c = Cipher.getInstance(algorithm);
		c.init(Cipher.ENCRYPT_MODE, key);
		   
		byte[] encValue = c.doFinal(valueToEnc.getBytes());
		    
		return new BASE64Encoder().encode(encValue);
	}
	
	private String decrypt(String encryptedValue, Key key) throws Exception {
		Cipher c = Cipher.getInstance(algorithm);
		c.init(Cipher.DECRYPT_MODE, key);
		    
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
		byte[] decValue = c.doFinal(decordedValue);
		    
		return new String(decValue);
	}
	
	private Key generateKey(byte[] keyValue) throws Exception {
		return new SecretKeySpec(keyValue, algorithm);
	}
}
