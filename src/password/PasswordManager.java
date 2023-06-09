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
	
	public void clear() {
		this.pwdList.clear();
	}
	
	public void reload(ArrayList<Password> nPL) {
		for(Password p : nPL) {
			this.pwdList.add(p);
		}
	}
	
	public ArrayList<Password> getPswList() {
		return this.pwdList;
	}
	
	public PasswordManager()  {
		this.pwdList = new ArrayList<Password>();
	}
	
	public PasswordManager(ArrayList<Password> p) {
		this.pwdList = p;
	}
	
	public boolean findByTitle(String title) {
		for(Password p : this.pwdList) {
			if(p.getTitle().equals(title)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Password getPwdByTitle(String title) {
		for(Password p : this.pwdList) {
			if(p.getTitle().equals(title)) {
				return p;
			}
		}
		
		return null;
	}
	
	public boolean addPsw(String title, String body, String password) {
		try {
			IvParameterSpec ivParameterSpec = generateIv();
			
			SecretKey k = getKeyFromPassword(password, Integer.toString(password.length()));
			String encBody = encrypt_(ALGORITHM, body, k, ivParameterSpec);
			pwdList.add(new Password(title, encBody, ivParameterSpec));
			
			return true;
		} 
		catch (Exception e) {
			return false;
		}
	}
	
	public String decrypt(Password pwd, String password) {
		try {
			SecretKey key = getKeyFromPassword(password, Integer.toString(password.length()));
			
			return decrypt_(ALGORITHM, pwd.getBody(), key, pwd.getIv());
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public boolean removePwd(String title) {
		int index = 0;
		boolean found = false;
		
		for(Password p : this.pwdList) {
			if(p.getTitle().equals(title)) {
				found = true;
				break;
			}
			index++;
		}
		
		if(found) {
			this.pwdList.remove(index);
		}
		
		return found;
	}
	
	public Password getPasswordAt(int index) {
		try {
			return this.pwdList.get(index);
		}
		catch(IndexOutOfBoundsException  e) {
			return null;
		}	
	}
	
	public int getSize() {
		return this.pwdList.size();
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
}
