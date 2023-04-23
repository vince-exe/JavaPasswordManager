package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.crypto.spec.IvParameterSpec;

import password.Password;
import password.PasswordSerialized;

public class FileManager {
	public static String appUserPasswords; 
	public static String appDirPath;
	
	public static void init() {
		appUserPasswords = System.getenv("APPDATA") + "\\.PasswordManager\\user_passwords.txt";
		appDirPath = System.getenv("APPDATA") + "\\.PasswordManager";
	}
	
	public static boolean checkPath(String path) {
		return new File(path).exists();
	}
	
	public static boolean mkdir(String path) {
		File file = new File(path);
		
		return file.mkdir();
	}
	
	public static boolean mkfile(String path) {
		try {
			File file = new File(path);
			file.createNewFile();
			
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public static boolean storePasswords(ArrayList<Password> pwdL, String path) {
		try {
			ArrayList<PasswordSerialized> pwdS = new ArrayList<PasswordSerialized>();
			 
			for(Password pw : pwdL) {
				pwdS.add(new PasswordSerialized(pw.getTitle(), pw.getBody(), pw.getIv().getIV()));
			}
			
	        FileOutputStream fop = new FileOutputStream(path);
	        ObjectOutputStream oos = new ObjectOutputStream(fop);
	        
	        oos.writeObject(pwdS);
	        oos.close();
	        fop.close();
	        
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Password> loadPasswords(String path) {
		try {
	        FileInputStream fis = new FileInputStream(path);
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        
	        ArrayList<PasswordSerialized> woi = new ArrayList<PasswordSerialized>();
	        woi = (ArrayList<PasswordSerialized>)ois.readObject();
	   
	        ois.close();
	        fis.close();
	        
	        ArrayList<Password> pswL = new ArrayList<Password>();
	        for(PasswordSerialized pwS : woi) {
	        	pswL.add(new Password(pwS.getTitle(), pwS.getBody(), new IvParameterSpec(pwS.getIv())));
	        }
	        
			return pswL;
		}	
		catch(Exception e) {
			return new ArrayList<Password>();
		}
	}
}	