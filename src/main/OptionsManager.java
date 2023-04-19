package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class OptionsManager {
	private static final String[] ALGORITHMS = {"AES", "CBC", "PKCS5Padding"};
	private static String tmpAlgorithm = PasswordManager.ALGORITHM;
	private static String tmpSavePath = PasswordManager.DEFAULT_SAVE_PATH;
	
	private static boolean isInAlgorithms(String str) {
		for(String s : ALGORITHMS) {
			if(s.equals(str)) { 
				return true;
			}
		}
		
		return false;
	}
	
 	public static void configSettings(Scanner sc) {
		int option = -2;
		String tmp = new String();
		
		do {
			try {
				System.out.print("\n1) Change Alghorithm" + 
						 "\n2) Change Save Path" +
					     "\n0) Exit Without Save" +
						 "\n-1) Save And Exit" +
						 "\n\nInsert a option ( -1 / 2 ): ");
				
				option = sc.nextInt();
				sc.nextLine();
				
				switch(option) {
				case 1:
					System.out.print("\nCurrent Algorithm: " + PasswordManager.ALGORITHM +
							         "\nInsert an algorithm ( AES / CBC / PKCS5Padding ): ");
					tmp = sc.nextLine().toUpperCase();
					
					if(!isInAlgorithms(tmp)) {
						System.out.print("\nI'm sorry but " + tmp + " isn't a valid algorithm for this software\n");
						break;
					}
					tmpAlgorithm = tmp;
					break;
					
				case 2:
					System.out.print("\nCurrent Default Save Path: " + PasswordManager.DEFAULT_SAVE_PATH +
									 "\nInsert a path, format: ( Users/test/test.txt ): ");
					tmp = sc.nextLine();
					
					File file = new File(tmp);
					if(!file.isDirectory()) {
						System.out.print("\nInvalid path!\n");
						break;
					}
					
					tmpSavePath = tmp;
					break;
					
				case 0:
					if(!tmpSavePath.equals(PasswordManager.DEFAULT_SAVE_PATH) || !tmpAlgorithm.equals(PasswordManager.ALGORITHM)) {
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
					if(FileUtils.createAppSettingsFile(tmpAlgorithm, tmpSavePath)) {
						PasswordManager.ALGORITHM = tmpAlgorithm;
						PasswordManager.DEFAULT_SAVE_PATH = tmpSavePath;
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
}