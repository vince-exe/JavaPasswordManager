package main;

import java.io.IOException;
import java.security.Key;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordManager {
	public static String ALGORITHM = "AES";
	public static String DEFAULT_SAVE_PATH = "null";
	
	private static final byte NEW_PSW = 1;
	private static final byte VIEW_PSWS = 2;
	private static final byte DEL_PSW = 3;
	private static final byte UPDT_PSW = 4;
	private static final byte DECR_PSW = 5;
	private static final byte CONF_SETTINGS = 6;
	private static final byte EXIT = 0;
    
	private String encrypt(String valueToEnc, Key key) throws Exception {
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.ENCRYPT_MODE, key);
		   
		byte[] encValue = c.doFinal(valueToEnc.getBytes());
		    
		return new BASE64Encoder().encode(encValue);
	}
	
	private String decrypt(String encryptedValue, Key key) throws Exception {
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.DECRYPT_MODE, key);
		    
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
		byte[] decValue = c.doFinal(decordedValue);
		    
		return new String(decValue);
	}
	 
	private Key generateKey(byte[] keyValue) throws Exception {
		return new SecretKeySpec(keyValue, ALGORITHM);
	}
	 
	public static void main(String[] args) {
		int option = -1;
		Scanner scanner = new Scanner(System.in);
		
		FileUtils.init();
		if(FileUtils.checkAppSettingsFile()) {
			String[] args_ = new String[2];
			args_ = FileUtils.loadFileSettings();
			if(args_ != null) {
				PasswordManager.ALGORITHM = args_[0];
				PasswordManager.DEFAULT_SAVE_PATH = args_[1];
			}
			else {
				System.out.print("\n[ FATAL ERROR ] n008, check the README.md file to manually solve it");
				System.exit(-1);
			}
		}
		else {
			int err = FileUtils.handleSettingsPaths(ALGORITHM, DEFAULT_SAVE_PATH);
			if( err != 1) {
				System.out.print("\n[ FATAl ERROR ] n" + err + ", check the README.md file to manually solve it");
				System.exit(-1);
			}
		}

		do {	
			System.out.print("\n1)New Password" +
							 "\n2)View Passwords ( Path + Name )" + 
							 "\n3)Delete Password" +
							 "\n4)Update Password" + 
							 "\n5)Decrypt Password" + 
							 "\n6)Config Settings" + 
							 "\n0)Exit" +
							 "\n\nInsert a option ( 0 / 6 ): ");
			
			try {
				option = scanner.nextInt();
				scanner.nextLine();
				
				switch (option) {
				case NEW_PSW:
					break;
					
				case VIEW_PSWS:
					break;
				
				case DEL_PSW:
					break;
				
				case UPDT_PSW:
					break;
					
				case DECR_PSW:
					break;
				
				case CONF_SETTINGS:
					OptionsManager.configSettings(scanner);
					break;
					
				case EXIT:
					break;
					
				default:
					System.out.print("\nInsert a valid option...");
					scanner.nextLine();
					break;
				}
			}
			catch(java.util.InputMismatchException e) {
				System.out.print("\nInsert a valid option...");
				scanner.nextLine();
			}
		}while(option != EXIT);
		
		scanner.close();
		System.out.print("\nBye :)");
	}
}