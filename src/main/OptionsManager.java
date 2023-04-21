package main;

import java.io.File;
import java.util.Scanner;

import password.Password;
import utils.Utils;

public class OptionsManager {
	private static String tmpSavePath = FileManager.DEFAULT_SAVE_PATH;
	
	/**
	 * Configuration Settings menu.
	 * 
	 * @param sc a scanner to read informations from the System Input
	 */
 	public static void configSettings(Scanner sc) {
		int option = -2;
		String tmp = new String();
		
		do {
			try {
				System.out.print( 
						 "\n1) Change Save Path" +
					     "\n0) Exit Without Save" +
						 "\n-1) Save And Exit" +
						 "\n\nInsert a option ( -1 / 2 ): ");
				
				option = sc.nextInt();
				sc.nextLine();
				
				switch(option) {
				case 1:
					System.out.print("\nCurrent Default Save Path: " + FileManager.DEFAULT_SAVE_PATH +
									 "\nInsert a path, format: ( C:/Users/User/PersonalArea/Passwords ): ");
					tmp = sc.nextLine();
					
					File file = new File(tmp);
					if(!file.isDirectory()) {
						System.out.print("\nInvalid path!\n");
						break;
					}
					
					tmpSavePath = tmp;
					break;
					
				case 0:
					if(!tmpSavePath.equals(FileManager.DEFAULT_SAVE_PATH)) {
						System.out.print("\nThere are new changes, are you sure that you want to exit without saving? ( yes / no): ");
						tmp = sc.nextLine().toLowerCase();
						
						if(tmp.equals("yes")) {
							return;
						}
					}
					else {
						return;
					}
					break;
					
				case -1:
					if(FileManager.writeFile(FileManager.appSettingsFilePath, tmpSavePath)) {
						FileManager.DEFAULT_SAVE_PATH = tmpSavePath;
						return;
					}
					
					System.out.print("\nThe application failed to write in the settings file!\n");
					break;
					
				default:
					System.out.print("\nInsert a valid option!\n");
					break;
				}
			}
			catch(java.util.InputMismatchException e) {
				System.out.print("\nInsert a valid option!!\n");
				sc.nextLine();
			}
		}while(true);
	}

 	/**
 	 * New Password menu.
 	 * 
 	 * @param sc a scanner to read informations from the System Input
 	 */
 	public static void newPassword(Scanner sc) {
 		String pathToSave;
 		String pswTitle;
 		String key;
 		String content;
 		String option;
 		
 		if(FileManager.DEFAULT_SAVE_PATH.equals("null")) {
 			System.out.print("\nInsert a save path: ");
 			pathToSave = sc.nextLine();
 		}
 		else {
 			System.out.print("\nDo you want to use: " + FileManager.DEFAULT_SAVE_PATH + " as save path? ( yes / other path / exit ): ");
 			pathToSave = sc.nextLine();
 			
 			if(pathToSave.equals("exit")) { 
 				return;
 			}
 			else if(pathToSave.equals("yes")) {
 				pathToSave = FileManager.DEFAULT_SAVE_PATH;
 			}
 		}
 		
 		if(!FileManager.checkPath(pathToSave)) {
 			System.out.print("\nCant save the new password at: " + pathToSave + "\n");
 			return;
 		}
 		
 		System.out.print("\nInsert a title for the password: ");
 		pswTitle = sc.nextLine();
 		
 		System.out.print("\nInsert the content of the password: ");
 		content = sc.nextLine();
 		
 		System.out.print("\nInsert the master key to encrypt the password: ");
 		key = sc.nextLine();
 		
 		System.out.print("\nSave Path: " + pathToSave + "\nTitle " + pswTitle + "\nBody: " + content + "\nMaster Key: " + key + "\n\nAre you sure? ( yes / no ): ");
 		option = sc.nextLine().toLowerCase();
 		
 		if(!option.equals("yes")) {
 			return;
 		}
 		
 		String encriptedPsw = Main.passwordManger.addPsw(new Password(pswTitle, content, pathToSave), key);
 		if(encriptedPsw == null) {
 			System.out.print("\nThe software failed to encrypt the password, please try again.\n");
 			return;
 		}
 		
 		String currDate = Utils.getDate(Utils.EU_FORMAT_DATE_TIME);

 		if(FileManager.writeFile(pathToSave + "\\" + pswTitle + "-" + currDate + ".txt", encriptedPsw)) {
 			System.out.print("\nSuccessfully stored the password\n");
 		}
 		else {
 			System.out.print("\nThe software failed to store the password, please try again");
 		}
 	}

 	public static void decryptPassword(Scanner sc) {
 		int option = 0;
 		
 		do {
 			try {
 	 			System.out.print(
					     "\n1) Password Index" +
						 "\n2) Decrypt All" +
					     "\n0) Exit" +
						 "\n\nInsert an option ( 0 / 3 ): ");
 	 			option = sc.nextInt();
 	 			sc.nextLine();
		
				switch(option) {					
				case 1:
					break;
					
				case 2:
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