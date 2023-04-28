package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import password.Password;

public class OptionsManager {
	private static final byte MAX_PWD_TITLE_LEN = 20;
	private static final byte MIN_PWD_TITLE_LEN = 1;
	
	private static final byte MAX_PWD_KEY_LEN = 16;
	private static final byte MIN_PWD_KEY_LEN = 4;
	
	private static final byte MAX_PWD_CONTENT_LEN = 50;
	private static final byte MIN_PWD_CONTENT_LEN = 1;
	
	/**
	 * prints all the passwords contained in the PasswordManager
	 */
 	private static void printPasswords() {
 		for(int i = 0; i < Main.passwordManager.getSize(); i++) {
 			Password tmp = Main.passwordManager.getPasswordAt(i);
 			System.out.print("\nIndex: " + i +
 							 "\nTitle: " + tmp.getTitle() + "\n");
 		}
 	}
 	
 	/**
 	 * Print all the decripted passwords contained in the PasswordManager
 	 * 
 	 * @param key the key that will be used to decrypt the passwords.
 	 */
 	private static void decryptAll(String key) {
 		String content = new String();
 		int index = 0;
 		
 		for(Password p : Main.passwordManager.getPswList()) {
 			content = Main.passwordManager.decrypt(p, key);
 			
 			if(content != null) {
 	 			System.out.print("\nIndex: " + index +
						 "\nTitle: " + p.getTitle() +
						 "\nContent: " + content + "\n"
						 );
 			}
 			else {
 	 			System.out.print("\nIndex: " + index +
						 "\nTitle: " + p.getTitle() +
						 "\nContent: [ Error ] bad key.\n"
						 );
 			}
 			index++;
 		}
 	}
 	
 	/**
 	 * This method will return the current date based on the format that it will be passed.
 	 * 
 	 * @param format the format used to get the date , like: yyyy/MM/dd HH:mm:ss
 	 * 
 	 * @return the current date based on the format
 	 */
 	private static String getDateTimeNow(String format) {
 	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);  
 	   LocalDateTime now = LocalDateTime.now();  
 	   
 	   return dtf.format(now); 
 	}
 	
 	/**
 	 * Decrypt Passwords Menu.
 	 * 
 	 * @param sc a scanner to read informations from the System.in
 	 */
 	public static void decryptPassword(Scanner sc) {
 		int option = 0;
 		int index;
 		String tmp;
 		
 		do {
 			try {
 	 			System.out.print(
					     "\n1) Password Index" +
						 "\n2) Decrypt All" +
					     "\n3) View Passwords" +
					     "\n0) Exit" +
						 "\n\nInsert an option ( 0 / 3 ): ");
 	 			option = sc.nextInt();
 	 			sc.nextLine();
		
				switch(option) {					
				case 1:
					System.out.print("\nPassword Index: ");
					index = sc.nextInt();
					sc.nextLine();
					
					System.out.print("\nMaster Key: ");
					tmp = sc.nextLine();
					
					Password pwd = Main.passwordManager.getPasswordAt(index);
					if(pwd == null) {
						System.out.print("\nInvalid password index!\n");
						break;
					}
					
					String decBody = Main.passwordManager.decrypt(pwd, tmp);
					if(decBody == null) {
						System.out.print("\nThe software failed to decrypt the password. ( Probably Bad Key. )\n");
						break;
					}
					
					System.out.print("\nIndex: " + index);
					pwd.toString();
					System.out.print("\nBody: " + decBody);
					
					System.out.print("\nEnter any key to continue...");
					sc.nextLine();
					break;
					
				case 2:
					System.out.print("\n[ NOTE ]: if not all the passwords have the same Master Key, there will be some unencrypted passwords\n");
					
					System.out.print("\nMaster Key: ");
					tmp = sc.nextLine();
					
					decryptAll(tmp);
					break;
				
				case 3:
					printPasswords();
					break;
				
				case 0:
					return;
					
				default:
					System.out.print("\nInsert a valid option!!\n");
					break;
				}
 			}
 			catch(java.util.InputMismatchException e) {
 				System.out.print("\nInsert a valid option!!\n");
 				sc.nextLine();
 			}
 		} while(true);
 	}

 	/**
 	 * New Password Menu.
 	 * 
 	 * @param sc a scanner to read informations from the System.in
 	 */
 	public static void newPassword(Scanner sc) {
 		String title;
 		String content;
 		String masterKey;
 		String tmp;
 		
 		System.out.print("\nTitle ( min: " + MIN_PWD_TITLE_LEN + " / max: " + MAX_PWD_TITLE_LEN + " ): ");
 		title = sc.nextLine();
 		
 		if(title.length() < MIN_PWD_TITLE_LEN || title.length() > MAX_PWD_TITLE_LEN) {
 			System.out.print("\nInvalid title\n");
 			return;
 		}
 		if(Main.passwordManager.findByTitle(title)) {
 			System.out.print("\nThere is already a password with this title!!\n");
 			return;
 		}
 		
 		System.out.print("\nContent ( min: " + MIN_PWD_CONTENT_LEN + " / max: " + MAX_PWD_CONTENT_LEN + " ): ");
 		content = sc.nextLine();
 		
 		if(content.length() < MIN_PWD_CONTENT_LEN || content.length() > MAX_PWD_CONTENT_LEN) {
 			System.out.print("\nInvalid content\n");
 			return;
 		}
 		
 		System.out.print("\nMaster Key ( min: " + MIN_PWD_KEY_LEN + " / max: " + MAX_PWD_KEY_LEN + " ): ");
 		masterKey = sc.nextLine();
 		
 		if(masterKey.length() < MIN_PWD_KEY_LEN || masterKey.length() > MAX_PWD_KEY_LEN) {
 			System.out.print("\nInvalid Master Key\n");
 			return;
 		}
 		
 		System.out.print("\nTitle: " + title + "\nContent: " + content + "\nMaster Key: " + masterKey + "\n\nAre you sure ( yes / no ): ");
 		tmp = sc.nextLine().toLowerCase();
 		
 		if(!tmp.equals("yes")) {
 			return;
 		}
 		
 		if(Main.passwordManager.addPsw(title, content, masterKey)) {
 			System.out.print("\nSuccessfully created the new password\n");
 		}
 		else {
 			System.out.print("\nThe software failed to save the password\n");
 		}
 	}

 	/**
 	 * 
 	 * @param sc a scanner to read informations from the System.in
 	 * 
 	 * @return true if the operation went successfully, else no
 	 */
 	private static Password privateDelPwd(Scanner sc) {
 		String title;
 		String masterKey;
 		
 		System.out.print("\nPassword Title: ");
 		title = sc.nextLine();
 		
 		System.out.print("\nMaster Key: ");
 		masterKey = sc.nextLine();
 		
 		Password pwd = Main.passwordManager.getPwdByTitle(title);
 		if(pwd == null) {
 			System.out.print("\nNo password found with title: " + title + "\n");
 			return null;
 		}
 		
 		String dec = Main.passwordManager.decrypt(pwd, masterKey);
 		if(dec == null) {
 			System.out.print("\nMaster Key for password with title: " + title + " invalid\n");
 			return null;
 		}
 		
 		Main.passwordManager.removePwd(title);
 		return pwd;
 	}
 	
 	/**
 	 * Delete Password Menu.
 	 * 
 	 * @param sc a scanner to read informations from the System.in
 	 */
 	public static void deletePassword(Scanner sc) {
 		if(privateDelPwd(sc) != null) {
 			System.out.print("\nSuccessfully removed the password\n");
 		}
 	}
 
 	/**
 	 * Update Password Menu
 	 *  
 	 * @param sc a scanner to read informations from the System.in
 	 */
 	public static void updatePassword(Scanner sc) {
 		Password p = privateDelPwd(sc);
 		String content;
 		String masterKey;
 		
 		if(p != null) {
 			System.out.print("\nInsert the new content: ");
 			content = sc.nextLine();
 			
 			System.out.print("\nNew Master Key: ");
 			masterKey = sc.nextLine();
 			
 			Main.passwordManager.addPsw(p.getTitle(), content, masterKey);
 			System.out.print("\nSuccessfully update the password\n");
 		}
 	}

 	/**
 	 * Backup Passwords Menu
 	 * 
 	 * @param sc a scanner to read informations from the System.in
 	 */
 	public static void backup(Scanner sc) {
 		if(Main.passwordManager.getSize() == 0) {
 			System.out.print("\nCan't execute a backup on an empty Passwords List\n");
 			return;
 		}
 		
 		String currDate = OptionsManager.getDateTimeNow("dd-MM-yyyy HH-mm-ss");
 		String pathToStore = FileManager.appBackupPath + "\\" + currDate + ".txt";
 		String opt;
 		
 		System.out.print("\nA new backup with title: " + currDate + " will be created, continue? ( yes / no ): ");
 		opt = sc.nextLine().toLowerCase();
 		
 		if(!opt.equals("yes")) {
 			return;
 		}
 		
		if(!FileManager.mkfile(pathToStore) || !FileManager.storePasswords(Main.passwordManager.getPswList(), pathToStore)) {
			System.out.print("\nThe software failed to execute the backup\nPress any key to continue...");
			sc.nextLine();
		}
		else {
			System.out.print("\nSuccessfully created the backup\nPress any key to continue...");
			sc.nextLine();
		}
 	}

 	/**
 	 * Load Backup Menu
 	 * 
 	 * @param sc a scanner to read informations from the System.in
 	 */
 	public static void loadBackup(Scanner sc) {
 		
 	}
}