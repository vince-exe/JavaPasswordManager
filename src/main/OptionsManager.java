package main;

import java.util.Scanner;
import password.Password;

public class OptionsManager {
	private static final byte MAX_PWD_TITLE_LEN = 20;
	private static final byte MIN_PWD_TITLE_LEN = 1;
	
	private static final byte MAX_PWD_KEY_LEN = 16;
	private static final byte MIN_PWD_KEY_LEN = 4;
	
	private static final byte MAX_PWD_CONTENT_LEN = 50;
	private static final byte MIN_PWD_CONTENT_LEN = 1;
	
 	private static void printPasswords() {
 		for(int i = 0; i < Main.passwordManager.getSize(); i++) {
 			Password tmp = Main.passwordManager.getPasswordAt(i);
 			System.out.print("\nIndex: " + i +
 							 "\nTitle: " + tmp.getTitle() + "\n");
 		}
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
					
				case 2:
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
}