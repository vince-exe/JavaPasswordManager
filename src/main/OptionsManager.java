package main;

import java.util.Scanner;
import password.Password;

public class OptionsManager {
 	/**
 	 * New Password Menu.
 	 * 
 	 * @param sc a scanner to read informations from the System Input
 	 */
 	public static void newPassword(Scanner sc) {
 		;
 	}
 	
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
 	 * @param sc a scanner to read informations from the System Input
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
}